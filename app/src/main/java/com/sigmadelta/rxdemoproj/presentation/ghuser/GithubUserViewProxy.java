package com.sigmadelta.rxdemoproj.presentation.ghuser;


import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.sigmadelta.rxdemoproj.R;

import timber.log.Timber;

public class GithubUserViewProxy implements IGithubUserViewProxy {

    private Activity _act;
    private FloatingActionButton _fabSearch;

    public GithubUserViewProxy(Activity act) {
        _act = act;
        _fabSearch = (FloatingActionButton) act.findViewById(R.id.fabSearchUser);
    }

    @Override
    public void showUsernameExists(boolean userExists) {
        Timber.d("Does user exist: " + userExists);
        _fabSearch.setAlpha(userExists ? 1.0f : 0.3f);
        _fabSearch.setEnabled(userExists);
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
