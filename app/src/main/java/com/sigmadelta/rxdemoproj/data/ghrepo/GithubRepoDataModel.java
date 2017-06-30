package com.sigmadelta.rxdemoproj.data.ghrepo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jayway.jsonpath.JsonPath;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepoApi;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoDataModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;


public class GithubRepoDataModel implements IGithubRepoDataModel {

    @NonNull
    private Observable<List<GithubRepo>> getGithubReposFromRequest(String userName) {
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            final String url ="https://api.github.com/users/" + userName + "/repos";
            Timber.e("getGithubReposFromRequest()");

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        e.onNext(getGithubReposFromJson(response));
                        e.onComplete();
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
        final List<GithubRepo> repoList = new ArrayList<>(5);
//        Timber.e("JsonRequest: " + jsonRequest);

        // TODO: Make more robust
        for (int i = 0; i < 3; ++i) {
            GithubRepo repo = new GithubRepo();
            repo.setName(JsonPath.read(jsonRequest, "$.[" + i + "]." + GithubRepoApi.NAME.getApiName()));
            repo.setStars(JsonPath.read(jsonRequest, "$.[" + i + "]." + GithubRepoApi.STARS.getApiName()));
            repo.setOwner(JsonPath.read(jsonRequest, "$.[" + i + "]." + GithubRepoApi.OWNER.getApiName()));
            repo.setFork(JsonPath.read(jsonRequest, "$.[" + i + "]." + GithubRepoApi.FORKED.getApiName()));
            repo.setIssues(JsonPath.read(jsonRequest, "$.[" + i + "]." + GithubRepoApi.ISSUE_COUNT.getApiName()));
            repoList.add(repo);
        }
        Timber.e("Finished repo list: " + repoList);
        return repoList;
    }
}
