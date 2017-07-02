package com.sigmadelta.rxdemoproj.data.ghrepo;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jayway.jsonpath.JsonPath;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepoApi;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoDataModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class GithubRepoDataModel implements IGithubRepoDataModel {

    @NonNull
    private Observable<List<GithubRepo>> getGithubReposFromRequest(String userName) {
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            final String url ="https://api.github.com/search/repositories?q=user:" + userName + "+fork:true&sort=stars&order=desc";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        // Implement fromCallable to defer operations to background thread
                        // considering response from Volley arrives on main thread

                        Single.fromCallable(() -> getGithubReposFromJson(response))
                                .subscribeOn(Schedulers.io())
                                .subscribe(e::onNext, e::onError);
                    },
                    error -> {
                        e.onComplete();
                    }
            );
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }

    @NonNull
    @Override
    public Observable<List<GithubRepo>> getRepoList(String userName) {
        return getGithubReposFromRequest(userName);
    }

    private List<GithubRepo> getGithubReposFromJson(String jsonRequest) {
        // TODO: Make more robust
        final List<GithubRepo> repoList = new ArrayList<>(5);
//        Timber.e("JsonRequest: " + jsonRequest);

        // Limit maximal returns to 10
        int totalRepos = JsonPath.read(jsonRequest, "$.total_count");
        if (totalRepos > 10) {
            totalRepos = 10;
        }

        for (int i = 0; i < totalRepos; ++i) {
            GithubRepo repo = new GithubRepo();
            repo.setName(JsonPath.read(jsonRequest, getJsonPathFilter(i, GithubRepoApi.NAME.getApiName())));
            repo.setStars(JsonPath.read(jsonRequest, getJsonPathFilter(i, GithubRepoApi.STARS.getApiName())));
            repo.setOwner(JsonPath.read(jsonRequest, getJsonPathFilter(i, GithubRepoApi.OWNER.getApiName())));
            repo.setFork(JsonPath.read(jsonRequest, getJsonPathFilter(i, GithubRepoApi.FORKED.getApiName())));
            repo.setIssues(JsonPath.read(jsonRequest, getJsonPathFilter(i, GithubRepoApi.ISSUE_COUNT.getApiName())));
            repoList.add(repo);
        }
        return repoList;
    }

    private String getJsonPathFilter(int position, String apiName) {
        return "$.items.[" + position + "]." + apiName;
    }

    private String getInvalidRepoJson() {
        return  "{\"" + GithubRepoApi.NAME.getApiName() + "\":\"" + MainApplication.getContext().getResources().getString(R.string.invalid_username) +"\"," +
                "\"" + GithubRepoApi.STARS.getApiName() + "\":-1," +
                "\"" + GithubRepoApi.OWNER.getApiName() + "\":\"" + MainApplication.getContext().getResources().getString(R.string.invalid_username) +"\"," +
                "\"" + GithubRepoApi.FORKED.getApiName() + "\":false," +
                "\"" + GithubRepoApi.ISSUE_COUNT.getApiName() + "\":-1}";
    }
}
