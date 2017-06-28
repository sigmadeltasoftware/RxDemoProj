package com.sigmadelta.rxdemoproj.data.ghuser;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jayway.jsonpath.JsonPath;
import com.sigmadelta.rxdemoproj.MainApplication;
import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.sigmadelta.rxdemoproj.domain.ghuser.IGithubUserRepository;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class GithubUserRepository implements IGithubUserRepository {

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
        return Observable.create(e -> {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(MainApplication.getContext());
            String url ="https://api.github.com/users/";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + githubUserName,
                    response -> {
                        e.onNext(getGithubUserFromJson(response));
                        e.onComplete();
                    },
                    error -> {
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

        // TODO: Make this more robust => Create GithubAPI enum class or something
        Timber.d("Debugging JsonRequest: " + jsonRequest);
        user.setName(JsonPath.read(jsonRequest, "$.login"));
        user.setFollowerCount(JsonPath.read(jsonRequest, "$.followers"));
        user.setFollowingCount(JsonPath.read(jsonRequest, "$.following"));
        user.setRepositoryCount(JsonPath.read(jsonRequest, "$.public_repos"));
        user.setAvatar(JsonPath.read(jsonRequest, "$.avatar_url"));

        return user;
    }

    private String getInvalidUserJson() {
        return  "{\"login\":\"" + MainApplication.getContext().getResources().getString(R.string.invalid_username) +"\"," +
                 "\"followers\":-1," +
                 "\"following\":-1," +
                 "\"public_repos\":-1," +
                 "\"avatar_url\":\"NOT_AVAILABLE\"}";
    }
}
