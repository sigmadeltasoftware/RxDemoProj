package com.sigmadelta.rxdemoproj.presentation;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sigmadelta.core.util.NetworkValidator;
import com.sigmadelta.core.util.PermissionManager;
import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.presentation.ghrepo.GithubRepoViewModel;
import com.sigmadelta.rxdemoproj.presentation.ghrepo.GithubRepoViewProxy;
import com.sigmadelta.rxdemoproj.presentation.ghrepo.IGithubRepoViewProxy;
import com.sigmadelta.rxdemoproj.presentation.ghuser.GithubUserViewModel;
import com.sigmadelta.rxdemoproj.presentation.ghuser.GithubUserViewProxy;
import com.sigmadelta.rxdemoproj.presentation.ghuser.IGithubUserViewProxy;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private final CompositeDisposable _compositeDisposable = new CompositeDisposable();
    private GithubUserViewModel _ghUserViewModel;
    private GithubRepoViewModel _ghRepoViewModel;
    private IGithubUserViewProxy _ghUserViewProxy;
    private IGithubRepoViewProxy _ghRepoViewProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Before continuing, validate if network data connection is valid
        Observable.defer(NetworkValidator::isDataConnectionValid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (!aBoolean) {
                        setContentView(R.layout.no_network);
                    }
                });

        setContentView(R.layout.activity_main);
        PermissionManager permMan = new PermissionManager(this);
        permMan.maybeRequestPermission(null, PermissionManager.Permissions.INTERNET);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _ghUserViewModel = new GithubUserViewModel();
        _ghRepoViewModel = new GithubRepoViewModel();
        _ghUserViewProxy = new GithubUserViewProxy(this);
        _ghRepoViewProxy = new GithubRepoViewProxy(this);

        EditText editUserName = (EditText) findViewById(R.id.editUserName);
        _compositeDisposable.add(RxTextView.textChanges(editUserName)
                .filter(charSequence -> charSequence.length() > 0)
                .subscribeOn(Schedulers.io())
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .subscribe(string -> {
                    _ghUserViewModel.doesUserExist(string)
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                })
        );

        FloatingActionButton fabSearch = (FloatingActionButton) findViewById(R.id.fabSearchUser);
        _compositeDisposable.add(RxView.clicks(fabSearch)
                .subscribe( view -> {
                    _ghRepoViewProxy.showSpinningWheel(true);
                    _ghRepoViewModel.getRepoList(editUserName.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: Move to binding
        _compositeDisposable.add(subscribeToUsernameChanges());
        _compositeDisposable.add(subscribeToProjectSearch());
    }

    @Override
    protected void onPause() {
        super.onPause();
        _compositeDisposable.dispose();
    }

    private Disposable subscribeToUsernameChanges() {
        return _ghUserViewModel.bindGetUserData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ghUser -> {
                    _ghUserViewProxy.showUsernameData(ghUser);
                    _ghUserViewProxy.showUsernameExists(ghUser.getName().equals(
                            getResources().getString(R.string.invalid_username)) ?
                            Boolean.FALSE :
                            Boolean.TRUE
                    );
                    _ghRepoViewProxy.setRepositoryDataVisibility(View.INVISIBLE);
                }, _ghUserViewProxy::onUsernameChangeError);
    }

    private Disposable subscribeToProjectSearch() {
        return _ghRepoViewModel.bindGetRepoList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ghRepo -> {
                    Timber.e("subscribeToProjectSearch() || itemcount: " + ghRepo.getItemCount());
                    _ghRepoViewProxy.showRepositoryData(ghRepo);
                }, error -> {
                    Timber.e("Error occurred: " + error);
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionManager.INTERNET_ID: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Timber.d("Permission INTERNET granted!");
                } else {
                    Toast.makeText(this, "The app was not allowed to access the internet. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    finish();
                }
            } break;
        }
    }
}