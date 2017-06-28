package com.sigmadelta.rxdemoproj.data.ghuser;


import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class GithubUserRepository implements IGithubUserRepository {

    @NonNull
    @Override
    public Observable<Boolean> doesGithubUserExist(String githubUserName) {
        // TODO: Make network request to Github and check whether the username presented is valid
        Timber.d("doesGithubUserExist: " + githubUserName);
        Timber.e(Looper.myLooper() == Looper.getMainLooper() ? "MAIN THREAD" : "IO THREAD");

        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            String url ="https://api.github.com/users/";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + githubUserName,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Timber.d("Response is: "+ response.substring(0,500));
                        e.onNext(Boolean.TRUE);
                        e.onComplete();
                    },
                    error -> {
                        // TODO: Handle error
                        Timber.e("Error occurred: " + error);
                        e.onNext(Boolean.FALSE);
                        e.onComplete();
                    }
            );
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        });
    }

    // Only call getGithubUser if Github User exists
    @NonNull
    @Override
    public Observable<GithubUser> getGithubUser(String githubUserName) {
        if (isUserNameCached(githubUserName)) {
            return getGithubUserFromCache(githubUserName);
        } else {
            // TODO: Implement error handling if request times out
            return getGithubUserFromRequest(githubUserName);
        }
    }

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
        // TODO: Create Volley request to retrieve user

        return null;
    }
}
