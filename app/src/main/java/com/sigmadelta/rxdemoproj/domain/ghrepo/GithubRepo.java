package com.sigmadelta.rxdemoproj.domain.ghrepo;


public class GithubRepo {
    private String _name;
    private int _stars;
    private String _owner;
    private boolean _fork;
    private int _issues;

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getStars() {
        return _stars;
    }

    public void setStars(int _stars) {
        this._stars = _stars;
    }

    public String getOwner() {
        return _owner;
    }

    public void setOwner(String _owner) {
        this._owner = _owner;
    }

    public boolean getFork() {
        return _fork;
    }

    public void setFork(boolean _fork) {
        this._fork = _fork;
    }

    public int getIssues() {
        return _issues;
    }

    public void setIssues(int _issues) {
        this._issues = _issues;
    }
}
