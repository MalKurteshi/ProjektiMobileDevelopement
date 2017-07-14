package com.fiek.mali.projektimobiledevelopement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.OptionalPendingResult;
//import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    Button btnVazhdoPaLogim;
    Button btnSignOut;
//    Button btnKycuMeFB;
    ProgressDialog progressDialog;
    FirebaseUser user = null;
    //Firebase AUTH
    private static final String TAG = "FacebookLogin";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private CallbackManager mCallbackManager;

    //Google
//    SignInButton signInButtonGoogle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        // Progress dialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Its loading....");
        // Butonat
        btnVazhdoPaLogim = (Button) findViewById(R.id.btnVazhdoPaLogim);
        btnVazhdoPaLogim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKate = new Intent(getApplicationContext(), Katet.class);
                startActivity(objKaloNeKate);
            }
        });

        btnSignOut = (Button) findViewById(R.id.button_facebook_signout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

//        Google
//        signInButtonGoogle= (SignInButton) findViewById(R.id.sign_in_button);
//        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent objSignIn = new Intent(getApplicationContext(), SignIn.class);
//                startActivity(objSignIn);
//            }
//        });

        //
//                FirebaseUser currentUser = mAuth.getCurrentUser();
//                Log.v(TAG, String.valueOf(currentUser));
//                if (currentUser!=null) {
//                    Intent objKaloNeKatet = new Intent(getApplicationContext(), Katet.class);
//                    objKaloNeKatet.putExtra("username", currentUser.getDisplayName());
//                    startActivity(objKaloNeKatet);
//                    finish();
//                }
//                else {
//                    Intent objKaloNeKatet = new Intent(getApplicationContext(), Katet.class);
//                    startActivity(objKaloNeKatet);
//                }


        // [END initialize_auth]

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.btnKycuMeFB);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

        });
        // [END initialize_fblogin]

    }

//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


//         per testim

        if (currentUser!=null) {
            Intent objKaloNeKatet = new Intent(getApplicationContext(), Katet.class);
            objKaloNeKatet.putExtra("username", currentUser.getDisplayName());
            startActivity(objKaloNeKatet);
            finish();
        }
//        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        progressDialog.show();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,user.getEmail(),Toast.LENGTH_LONG).show();
                            updateUI(user);
                            Intent objKaloNeKatet = new Intent(getApplicationContext(), Katet.class);
                            objKaloNeKatet.putExtra("username",user.getDisplayName());
                            startActivity(objKaloNeKatet);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        progressDialog.hide();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]
    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI(null);

    }

    private void updateUI(FirebaseUser user) {
        progressDialog.hide();
        if (user != null) {
//            mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.btnKycuMeFB).setVisibility(View.GONE);
            findViewById(R.id.button_facebook_signout).setVisibility(View.VISIBLE);
        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

            findViewById(R.id.btnKycuMeFB).setVisibility(View.VISIBLE);
            findViewById(R.id.button_facebook_signout).setVisibility(View.GONE);
        }
    }
}

