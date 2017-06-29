package com.sigmadelta.rxdemoproj.domain.ghproject;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface IGithubProjectDataModel {
    @NonNull
    Observable<GithubProject> getProjectData();
}
