package com.iriras.medsosapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import id.gits.mvvmcore.activity.GitsActivity;

public class MainActivity extends GitsActivity<MainController> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController.setGoogleConfig();
    }

    @Override
    protected int getToolbarId() {
        return 0;
    }

    @Override
    protected int getResLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainController createController() {
        return new MainController();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == mController.RC_SIGN_IN && resultCode == RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mController.handleGoogleResult(result);
        }
    }
}
