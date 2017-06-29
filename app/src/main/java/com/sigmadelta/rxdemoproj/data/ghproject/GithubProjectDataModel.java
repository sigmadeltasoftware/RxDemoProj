package com.sigmadelta.rxdemoproj.data.ghproject;


import com.sigmadelta.rxdemoproj.domain.ghproject.GithubProject;
import com.sigmadelta.rxdemoproj.domain.ghproject.IGithubProjectDataModel;
import com.sigmadelta.rxdemoproj.domain.ghproject.IGithubProjectRepository;

import io.reactivex.Observable;

public class GithubProjectDataModel implements IGithubProjectDataModel {

    private IGithubProjectRepository _ghProjectRepo;

    public GithubProjectDataModel() {
        _ghProjectRepo = new GithubProjectRepository();
    }

    @Override
    public Observable<GithubProject> getProjectData() {
        return null;
    }
}
