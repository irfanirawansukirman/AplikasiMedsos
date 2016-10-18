package com.iriras.medsosapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.iriras.medsosapp.databinding.ActivityMainBinding;

import id.gits.mvvmcore.controller.GitsController;

/**
 * Created by irfan on 18/10/16.
 */

public class MainController extends GitsController<MainVM, ActivityMainBinding> {
    public static final int RC_SIGN_IN = 7859;
    public final String TAG = MainController.class.getSimpleName();

    @Override
    public void onCreateController(Bundle savedInstanceState) {
        super.onCreateController(savedInstanceState);
    }

    @Override
    public MainVM createViewModel(ActivityMainBinding binding) {
        return new MainVM(mActivity, this, binding);
    }

    @Override
    public void bindViewModel(ActivityMainBinding binding, MainVM viewModel) {
        binding.setVm(viewModel);
    }

    public void setGoogleConfig() {
        mViewModel.setGoogleConfig();
    }

    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mViewModel.mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleGoogleResult(
            GoogleSignInResult result
    ) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mViewModel.mTextName.set(acct.getDisplayName());
            mViewModel.mTextEmail.set(acct.getEmail());
            if (acct.getPhotoUrl() != null || !acct.getPhotoUrl().equals("")) {
                mViewModel.setGooglePhoto(acct);
            }
            mViewModel.updateGoogleBtn(true);
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
            mViewModel.updateGoogleBtn(false);
        }
    }
}
