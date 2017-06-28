package com.sigmadelta.rxdemoproj.domain.ghuser;

public enum GithubUserApi {
    USERNAME(0, "login"),
    FOLLOWERS(1, "followers"),
    FOLLOWING(2, "following"),
    REPOSITORIES(3, "public_repos"),
    AVATAR(4, "avatar_url");

    private final int id;
    private final String apiName;

    GithubUserApi(int id, String imgResource) {
        this.id = id;
        this.apiName = imgResource;
    }

    public final int getId() { return id; }

    public final String getApiName() {
        return apiName;
    }

    public static final int count = GithubUserApi.values().length;
}
