package com.sigmadelta.rxdemoproj.data.ghuser;


import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserDataModel;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class GithubUserDataModel implements IGithubUserDataModel {

    private final IGithubUserRepository _ghUserRepository;

    public GithubUserDataModel() {
        _ghUserRepository = new GithubUserRepository();
    }

    @NonNull
    @Override
    public Observable<GithubUser> getUserData(String userName) {
        return _ghUserRepository.getGithubUser(userName);
    }
}
