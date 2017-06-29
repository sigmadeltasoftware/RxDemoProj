package com.sigmadelta.rxdemoproj.data.ghrepo;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public class GithubRepoRepository implements IGithubRepoRepository {

    @NonNull
    public Observable<GithubRepo> getGithubRepo(String userName) {
        //TODO: Look into caching option
        return getGithubReposFromRequest(userName);
    }

    @NonNull
    private Observable<GithubRepo> getGithubReposFromRequest(String userName) {
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            final String url ="https://api.github.com/" + userName + "/repos";
        });
    }
}
