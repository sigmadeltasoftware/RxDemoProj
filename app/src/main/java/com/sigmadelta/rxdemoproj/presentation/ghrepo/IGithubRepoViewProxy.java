package com.sigmadelta.rxdemoproj.presentation.ghrepo;


import com.sigmadelta.rxdemoproj.presentation.ghrepo.adapter.GithubRepoAdapter;

public interface IGithubRepoViewProxy {
    void setRepositoryDataVisibility(int visibility);
    void showRepositoryData(GithubRepoAdapter adapter);
    void onRepositoryFetchError(Throwable throwable);
}
