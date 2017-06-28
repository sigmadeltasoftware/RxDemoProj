package com.sigmadelta.rxdemoproj.domain.ghuser;


public class GithubUser {
    private String _name;
    private String _avatar;
    private int _repositoryCount;
    private int _followerCount;
    private int _followingCount;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getAvatar() {
        return _avatar;
    }

    public void setAvatar(String avatar) {
        this._avatar = avatar;
    }

    public int getRepositoryCount() {
        return _repositoryCount;
    }

    public void setRepositoryCount(int repositoryCount) {
        this._repositoryCount = repositoryCount;
    }

    public int getFollowerCount() {
        return _followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this._followerCount = followerCount;
    }

    public int getFollowingCount() {
        return _followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this._followingCount = followingCount;
    }
}
