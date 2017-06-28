package com.sigmadelta.rxdemoproj.domain.ghuser;


import io.reactivex.Observable;

public interface IGithubUserRepository {
    Observable<GithubUser> getGithubUser(String githubUserName);
}
