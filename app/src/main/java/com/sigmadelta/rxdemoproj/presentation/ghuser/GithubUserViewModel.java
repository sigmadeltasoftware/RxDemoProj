package com.sigmadelta.rxdemoproj.presentation.ghuser;


import com.sigmadelta.rxdemoproj.data.ghuser.GithubUserDataModel;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserDataModel;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Creator: Bojan Belic
 * Date:    6/23/17
 * Company: Sigma Delta Software Solutions
 */

public class GithubUserViewModel {

    private IGithubUserDataModel _ghUserDataModel;

    private BehaviorSubject<String> _ghUserSubject = BehaviorSubject.create();

    public GithubUserViewModel() {
        _ghUserDataModel = new GithubUserDataModel();
    }

    public Observable<GithubUser> bindGetUserData() {
        return _ghUserSubject.flatMap(_ghUserDataModel::getUserData);
    }

    public Completable doesUserExist(String userName) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                _ghUserSubject.onNext(userName);
            }
        };
    }
}
