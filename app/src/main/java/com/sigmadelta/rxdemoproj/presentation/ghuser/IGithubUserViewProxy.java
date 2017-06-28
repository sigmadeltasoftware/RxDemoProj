package com.sigmadelta.rxdemoproj.presentation.ghuser;


public interface IGithubUserViewProxy {
    void showUsernameExists(boolean usernameExists);
    void onUsernameChangeError(Throwable throwable);
}
