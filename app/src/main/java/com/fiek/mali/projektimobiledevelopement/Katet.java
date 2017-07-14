package com.fiek.mali.projektimobiledevelopement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Katet extends AppCompatActivity {

    Button btnKati2;
    Button btnKati3;
    Button btnKati4;
    Button btnKati5;
    Button btnKati6;
    Button btnKati7;
    String username = null;

    ProgressDialog progressDialog;
    FirebaseUser user = null;
    //Firebase AUTH
    private static final String TAG = "FacebookLogin";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    Button btnSignOut;

    private CallbackManager mCallbackManager;

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
//        if(user==null)
//        {
//            Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(myintent);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katet);

        btnKati2 = (Button) findViewById(R.id.btnKati2);
        btnKati3 = (Button) findViewById(R.id.btnKati3);
        btnKati4 = (Button) findViewById(R.id.btnKati4);
        btnKati5 = (Button) findViewById(R.id.btnKati5);
        btnKati6 = (Button) findViewById(R.id.btnKati6);
        btnKati7 = (Button) findViewById(R.id.btnKati7);

        progressDialog = new ProgressDialog(Katet.this);
        progressDialog.setMessage("Its loading....");

        // Firebase user
        Intent myintent = getIntent();
        username = myintent.getStringExtra("username");
        if (username!=null) {
            Log.v("Katet.java", username);
        }
        else
        {
            username = null;
        }
        //FB
        mAuth = FirebaseAuth.getInstance();
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

        btnSignOut = (Button) findViewById(R.id.button_facebook_signout);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        //Intentat per kalimin neper planimetrite e kateve me rastin e klikimit mbi button
        btnKati2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin2 = new Intent(getApplicationContext(), Kati2.class);
                objKaloNeKatin2.putExtra("username",username);
                startActivity(objKaloNeKatin2);
            }
        });

        btnKati3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin3 = new Intent(getApplicationContext(), Kati3.class);
                objKaloNeKatin3.putExtra("username",username);
                startActivity(objKaloNeKatin3);
            }
        });
        btnKati4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin4 = new Intent(getApplicationContext(), Kati4.class);
                objKaloNeKatin4.putExtra("username",username);
                startActivity(objKaloNeKatin4);
            }
        });
        btnKati5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin5 = new Intent(getApplicationContext(), Kati5.class);
                objKaloNeKatin5.putExtra("username",username);
                startActivity(objKaloNeKatin5);
            }
        });
        btnKati6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin6 = new Intent(getApplicationContext(), Kati6.class);
                objKaloNeKatin6.putExtra("username",username);
                startActivity(objKaloNeKatin6);
            }
        });
        btnKati7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objKaloNeKatin7 = new Intent(getApplicationContext(), Kati7.class);
                objKaloNeKatin7.putExtra("username",username);
                startActivity(objKaloNeKatin7);
            }
        });
    }

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
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(MainActivity.this,user.getEmail(),Toast.LENGTH_LONG).show();
                            updateUI(user);
//                            Intent objKaloNeKatet = new Intent(getApplicationContext(), Katet.class);
//                            objKaloNeKatet.putExtra("username",user.getDisplayName());
//                            startActivity(objKaloNeKatet);
//                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(Katet.this, "Authentication failed.",
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
        username=null;
        Intent myintent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myintent);
        finish();
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
