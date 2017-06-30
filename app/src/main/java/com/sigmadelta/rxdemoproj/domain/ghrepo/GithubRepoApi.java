package com.sigmadelta.rxdemoproj.domain.ghrepo;


public enum GithubRepoApi {
    NAME(0, "name"),
    STARS(1, "stargazers_count"),
    OWNER(2, "owner.login"),
    FORKED(3, "fork"),
    ISSUE_COUNT(4, "open_issues_count");

    private final int id;
    private final String apiName;

    GithubRepoApi(int id, String imgResource) {
        this.id = id;
        this.apiName = imgResource;
    }

    public final int getId() { return id; }

    public final String getApiName() {
        return apiName;
    }

    public static final int count = GithubRepoApi.values().length;

    public static GithubRepoApi match(int val) {
        for (GithubRepoApi id: GithubRepoApi.values()) {
            if (id.getId() == val) {
                return id;
            }
        }
        return null;
    }
}
