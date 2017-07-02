package com.sigmadelta.rxdemoproj.presentation.ghrepo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.presentation.ghrepo.adapter.GithubRepoAdapter;

import timber.log.Timber;

public class GithubRepoViewProxy implements IGithubRepoViewProxy {

    private Activity _act;
    private RecyclerView _rcvRepo;
    private ProgressDialog _spinningDialog;

    public GithubRepoViewProxy(Activity act) {
        _act = act;
        _rcvRepo = (RecyclerView) _act.findViewById(R.id.list_repos);
        RecyclerView.LayoutManager rcvLayoutManager = new LinearLayoutManager(_act);
        _rcvRepo.setLayoutManager(rcvLayoutManager);
        _rcvRepo.setItemAnimator(new DefaultItemAnimator());

        _spinningDialog = new ProgressDialog(_act);
        _spinningDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _spinningDialog.setMessage("Loading repository data...");
        _spinningDialog.setIndeterminate(true);
        _spinningDialog.setCanceledOnTouchOutside(false);
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
    public void showSpinningWheel(boolean shouldShow) {
        if (shouldShow) {
            _spinningDialog.show();
        } else {
            _spinningDialog.cancel();
        }
    }

    @Override
    public void showRepositoryData(GithubRepoAdapter repoAdapter) {
        _rcvRepo.setAdapter(repoAdapter);
        _rcvRepo.setVisibility(View.VISIBLE);
        showSpinningWheel(false);
    }

    @Override
    public void onRepositoryFetchError(Throwable throwable) {
        // TODO:
        showSpinningWheel(false);
    }
}
