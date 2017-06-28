package com.sigmadelta.rxdemoproj.presentation.ghuser;


import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghuser.GithubUser;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class GithubUserViewProxy implements IGithubUserViewProxy {

    private Activity _act;
    private FloatingActionButton _fabSearch;
    private TextView _txtRepos, _txtFollowers, _txtFollowing;
    private ImageView _imgUser;

    public GithubUserViewProxy(Activity act) {
        _act = act;
        _fabSearch = (FloatingActionButton) _act.findViewById(R.id.fabSearchUser);
        _fabSearch.setAlpha(0.3f);
        _fabSearch.setEnabled(false);
        _txtRepos = (TextView) _act.findViewById(R.id.txtRepositories);
        _txtFollowers = (TextView) _act.findViewById(R.id.txtFollowers);
        _txtFollowing = (TextView) _act.findViewById(R.id.txtFollowing);
        _imgUser = (ImageView) _act.findViewById(R.id.imgUserAvatar);
    }

    @Override
    public void showUsernameExists(boolean userExists) {
        Timber.d("Does user exist: " + userExists);
        _fabSearch.setAlpha(userExists ? 1.0f : 0.3f);
        _fabSearch.setEnabled(userExists);

        // TODO: Look into better icon
        if (!userExists) {
            _imgUser.setImageDrawable(_act.getResources().getDrawable(R.mipmap.ic_launcher));
        }
    }

    @Override
    public void showUsernameData(GithubUser ghUser) {
        _txtRepos.setText(String.valueOf(ghUser.getRepositoryCount()));
        _txtFollowers.setText(String.valueOf(ghUser.getFollowerCount()));
        _txtFollowing.setText(String.valueOf(ghUser.getFollowingCount()));
        Picasso.with(_act).load(ghUser.getAvatar()).into(_imgUser);
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
