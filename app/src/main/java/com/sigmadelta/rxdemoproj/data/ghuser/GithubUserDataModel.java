package com.sigmadelta.rxdemoproj.data.ghuser;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jayway.jsonpath.JsonPath;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUserApi;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserDataModel;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class GithubUserDataModel implements IGithubUserDataModel {

    @NonNull
    @Override
    public Observable<GithubUser> getUserData(String userName) {
        if (isUserNameCached(userName)) {
            return getGithubUserFromCache(userName);
        } else {
            // TODO: Implement error handling if request times out
            return getGithubUserFromRequest(userName);
        }    }


    @NonNull
    private boolean isUserNameCached(String githubUserName) {
        return false;
    }

    @NonNull
    private Observable<GithubUser> getGithubUserFromCache(String githubUserName) {
        // TODO: Check whether user has already been cached
        return null;
    }

    @NonNull
    private Observable<GithubUser> getGithubUserFromRequest(String githubUserName) {
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            final String url ="https://api.github.com/users/";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + githubUserName,
                    response -> {
                        e.onNext(getGithubUserFromJson(response));
                        e.onComplete();
                    },
                    error -> {

                        if (error == null || error.networkResponse == null) {
                            Timber.e("getGithubUserFromRequest(): Error returned is null or has no network response. Check internet connection?");
                            return;
                        }

                        // If user doesn't exist
                        Timber.e("Stringrequest statuscode = " + error.networkResponse.statusCode);
                        if (error.networkResponse.statusCode == 404) {
                            e.onNext(getGithubUserFromJson(getInvalidUserJson()));
                        } else {
                            // TODO: Handle error, currently crashes application
                            Timber.e("Error occurred: " + error);
                            //e.onError(new Throwable("Error retrieving Github User!"));
                        }
                        e.onComplete();
                    }
            );
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }

    private GithubUser getGithubUserFromJson(String jsonRequest) {
        final GithubUser user = new GithubUser();

        // TODO: Make this more robust
        user.setName(JsonPath.read(jsonRequest, "$." + GithubUserApi.USERNAME.getApiName()));
        user.setFollowerCount(JsonPath.read(jsonRequest, "$." + GithubUserApi.FOLLOWERS.getApiName()));
        user.setFollowingCount(JsonPath.read(jsonRequest, "$." + GithubUserApi.FOLLOWING.getApiName()));
        user.setRepositoryCount(JsonPath.read(jsonRequest, "$." + GithubUserApi.REPOSITORIES.getApiName()));
        user.setAvatar(JsonPath.read(jsonRequest, "$." + GithubUserApi.AVATAR.getApiName()));

        return user;
    }

    private String getInvalidUserJson() {
        return  "{\"" + GithubUserApi.USERNAME.getApiName() + "\":\"" + MainApplication.getContext().getResources().getString(R.string.invalid_username) +"\"," +
                 "\"" + GithubUserApi.FOLLOWERS.getApiName() + "\":-1," +
                 "\"" + GithubUserApi.FOLLOWING.getApiName() + "\":-1," +
                 "\"" + GithubUserApi.REPOSITORIES.getApiName() + "\":-1," +
                 "\"" + GithubUserApi.AVATAR.getApiName() + "\":\"NOT_AVAILABLE\"}";
    }
}
