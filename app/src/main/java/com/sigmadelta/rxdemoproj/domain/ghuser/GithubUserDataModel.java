package com.sigmadelta.rxdemoproj.domain.ghuser;


import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GithubUserDataModel implements IGithubUserDataModel {

    @NonNull
    @Override
    public Observable<GithubUser> getUserData() {
        return null;
    }
}
