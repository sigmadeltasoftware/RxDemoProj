package com.sigmadelta.rxdemoproj.domain.ghuser;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface IGithubUserDataModel {
    @NonNull
    Observable<GithubUser> getUserData();
}
