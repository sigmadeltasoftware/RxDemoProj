package com.sigmadelta.rxdemoproj.data.ghrepo;


import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoDataModel;
import com.sigmadelta.rxdemoproj.domain.ghrepo.IGithubRepoRepository;

import io.reactivex.Observable;

public class GithubRepoDataModel implements IGithubRepoDataModel {

    private IGithubRepoRepository _ghProjectRepo;

    public GithubRepoDataModel() {
        _ghProjectRepo = new GithubRepoRepository();
    }

    @Override
    public Observable<GithubRepo> getRepoData() {
        return null;
    }
}
