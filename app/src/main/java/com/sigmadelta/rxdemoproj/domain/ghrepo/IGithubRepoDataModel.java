package com.sigmadelta.rxdemoproj.domain.ghrepo;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface IGithubRepoDataModel {
    @NonNull
    Observable<GithubRepo> getRepoData();
}
