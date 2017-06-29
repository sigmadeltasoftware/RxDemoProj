package com.sigmadelta.rxdemoproj.data.ghproject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.domain.ghproject.GithubProject;
import com.sigmadelta.rxdemoproj.domain.ghproject.IGithubProjectRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;


public class GithubProjectRepository implements IGithubProjectRepository {

    @NonNull
    public Observable<GithubProject> getGithubProject(String userName) {
        //TODO: Look into caching option
        return getGithubProjectsFromRequest(userName);
    }

    @NonNull
    private Observable<GithubProject> getGithubProjectsFromRequest(String userName) {
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            final String url ="https://api.github.com/" + userName + "/repos";
        });
    }
}
