package com.sigmadelta.rxdemoproj.presentation.ghuser;


import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;

import timber.log.Timber;

public class GithubUserViewProxy implements IGithubUserViewProxy {

    private Activity _act;
    private FloatingActionButton _fabSearch;
    private TextView _txtRepos, _txtFollowers, _txtFollowing;

    public GithubUserViewProxy(Activity act) {
        _act = act;
        _fabSearch = (FloatingActionButton) _act.findViewById(R.id.fabSearchUser);
        _fabSearch.setAlpha(0.3f);
        _fabSearch.setEnabled(false);
        _txtRepos = (TextView) _act.findViewById(R.id.txtRepositories);
        _txtFollowers = (TextView) _act.findViewById(R.id.txtFollowers);
        _txtFollowing = (TextView) _act.findViewById(R.id.txtFollowing);
    }

    @Override
    public void showUsernameExists(boolean userExists) {
        Timber.d("Does user exist: " + userExists);
        _fabSearch.setAlpha(userExists ? 1.0f : 0.3f);
        _fabSearch.setEnabled(userExists);
    }

    @Override
    public void showUsernameData(GithubUser ghUser) {
        _txtRepos.setText(String.valueOf(ghUser.getRepositoryCount()));
        _txtFollowers.setText(String.valueOf(ghUser.getFollowerCount()));
        _txtFollowing.setText(String.valueOf(ghUser.getFollowingCount()));
    }

    @Override
    public void onUsernameChangeError(Throwable throwable) {
        showUsernameExists(false);
        Toast.makeText(_act,
                R.string.error_ghuser_existance_check,
                Toast.LENGTH_SHORT)
                .show();
    }
}
