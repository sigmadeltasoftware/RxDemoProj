package com.sigmadelta.rxdemoproj.presentation.ghrepo;


import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.presentation.ghrepo.adapter.GithubRepoAdapter;

import timber.log.Timber;

public class GithubRepoViewProxy implements IGithubRepoViewProxy {

    private Activity _act;
    private RecyclerView _rcvRepo;

    public GithubRepoViewProxy(Activity act) {
        _act = act;
        _rcvRepo = (RecyclerView) _act.findViewById(R.id.list_repos);
        RecyclerView.LayoutManager rcvLayoutManager = new LinearLayoutManager(_act);
        _rcvRepo.setLayoutManager(rcvLayoutManager);
        _rcvRepo.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setRepositoryDataVisibility(int visibility) {
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            _rcvRepo.setVisibility(visibility);
        } else {
            Timber.e("setRepositoryDataVisibility: Invalid visibility setting!");
            _rcvRepo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showRepositoryData(GithubRepoAdapter repoAdapter) {
        _rcvRepo.setAdapter(repoAdapter);
        _rcvRepo.setVisibility(View.VISIBLE);
        repoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRepositoryFetchError(Throwable throwable) {

    }
}
