package com.sigmadelta.rxdemoproj.presentation.ghuser;


import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;

public interface IGithubUserViewProxy {
    void showUsernameExists(boolean usernameExists);
    void showUsernameData(GithubUser ghUser);
    void onUsernameChangeError(Throwable throwable);
}
