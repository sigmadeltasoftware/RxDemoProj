package com.sigmadelta.rxdemoproj.domain.ghuser;


public class GithubUser {
    private String _name;
    private int _repositories;
    private int _followers;
    private int _following;

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getRepositories() {
        return _repositories;
    }

    public void setRepositories(int _repositories) {
        this._repositories = _repositories;
    }

    public int getFollowers() {
        return _followers;
    }

    public void setFollowers(int _followers) {
        this._followers = _followers;
    }

    public int getFollowing() {
        return _following;
    }

    public void setFollowing(int _following) {
        this._following = _following;
    }
}
