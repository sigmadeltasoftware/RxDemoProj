package com.sigmadelta.rxdemoproj.presentation.ghrepo;


import com.sigmadelta.rxdemoproj.data.ghrepo.GithubRepoDataModel;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoDataModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

public class GithubRepoViewModel {

    private IGithubRepoDataModel _ghRepoDataModel;
    private BehaviorSubject<String> _ghRepoSubject = BehaviorSubject.create();

    public GithubRepoViewModel() {
        _ghRepoDataModel = new GithubRepoDataModel();
    }

    @NonNull
    public Observable<List<GithubRepo>> bindGetRepoList() {
        return _ghRepoSubject.flatMap(_ghRepoDataModel::getRepoList);
    }

    @NonNull
    public Completable getRepoList(String userName) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver s) {
                _ghRepoSubject.onNext(userName);
            }
        };
    }
}
