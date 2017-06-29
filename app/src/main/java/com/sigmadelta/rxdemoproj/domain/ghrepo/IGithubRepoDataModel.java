package com.sigmadelta.rxdemoproj.domain.ghrepo;


import java.util.List;

import io.reactivex.Observable;

public interface IGithubRepoDataModel {
    Observable<List<GithubRepo>> getRepoList(String userName);
}
