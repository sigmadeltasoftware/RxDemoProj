package com.sigmadelta.rxdemoproj.domain.ghuser;


import io.reactivex.Observable;

public interface IGithubUserRepository {
    Observable<Boolean> doesGithubUserExist(String githubUserName);
    Observable<GithubUser> getGithubUser(String githubUserName);
}
