package com.sigmadelta.rxdemoproj.presentation;


import java.net.InetAddress;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import timber.log.Timber;

public class NetworkValidator {
    public static Observable<Boolean> isDataConnectionValid() {
        try {
            final InetAddress address = InetAddress.getByName("github.com");
            return Observable.just(!address.toString().equals(""));
        } catch (UnknownHostException e) {
            Timber.e("Unable to validate connection with GitHub! \n" + e.toString());
        }
        return Observable.just(false);
    }
}
