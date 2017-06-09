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
    Button btnKycuMeFB;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    ProgressDialog progressDialog;

    //Google
//    SignInButton signInButtonGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

//        Google
//        signInButtonGoogle= (SignInButton) findViewById(R.id.sign_in_button);
//        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent objSignIn = new Intent(getApplicationContext(), SignIn.class);
//                startActivity(objSignIn);
//            }
//        });

        // Inicializimi i variableve
         mAuth = FirebaseAuth.getInstance();

        // Butoni2
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.btnKycuMeFB);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("FBauth", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("FBauth", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FBauth", "facebook:onError", error);
                // ...
            }
        });

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

    }



//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent objKaloNeKate = new Intent(getApplicationContext(), Katet.class);
        startActivity(objKaloNeKate);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }




    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FBtoken", "handleFacebookAccessToken:" + token);
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
                            Log.d("FBsignInSucces", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Qka bon ti me user
                            Toast.makeText(MainActivity.this, "Email: "+ user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(user);
                            // dil ne aktivitetin tjeter p.sh listen e kateve


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FBsignInFailure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // Qka bon ti
//                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        progressDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });

    }
}
