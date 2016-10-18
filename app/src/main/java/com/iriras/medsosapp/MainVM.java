package com.iriras.medsosapp;

import android.databinding.ObservableField;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.iriras.medsosapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import id.gits.mvvmcore.viewmodel.GitsVM;

/**
 * Created by irfan on 18/10/16.
 */

public class MainVM extends GitsVM<MainController, ActivityMainBinding> implements GoogleApiClient.OnConnectionFailedListener {
    public ObservableField<String> mTextName = new ObservableField<>();
    public ObservableField<String> mTextEmail = new ObservableField<>();
    public ObservableField<Boolean> isViewBtn = new ObservableField<>(false);

    public GoogleApiClient mGoogleApiClient;
    public GoogleSignInOptions mGoogleSignInOptions;

    public MainVM(AppCompatActivity activity, MainController controller, ActivityMainBinding binding) {
        super(activity, controller, binding);
    }

    public void setGooglePhoto(GoogleSignInAccount mGoogleSignInAccount) {
        if (mGoogleSignInAccount.getPhotoUrl() == null) {
            Picasso.with(mBinding.imgProfile.getContext()).load(R.color.colorPrimary).into(mBinding.imgProfile);
        } else {
            Picasso.with(mBinding.imgProfile.getContext()).load(mGoogleSignInAccount.getPhotoUrl()).into(mBinding.imgProfile);
        }
    }

    //google sign in config
    //for request data exp : Name, Email etc
    public void setGoogleConfig() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Google API Client object
        //for access google sign-in
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                .enableAutoManage(mActivity /* FragmentActivity */, this/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();

        mBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoogleSignIn();
            }
        });
    }

    public void updateGoogleBtn(boolean isUpdate) {
        isViewBtn.set(isUpdate);
    }

    public void signOut(View v) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateGoogleBtn(false);
                        mTextEmail.set("");
                        mTextName.set("");
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onGoogleSignIn() {
        mBinding.signInButton.setSize(SignInButton.SIZE_STANDARD);
        mBinding.signInButton.setScopes(mGoogleSignInOptions.getScopeArray());

        //Google sign-in
        mController.googleSignIn();
    }
}
