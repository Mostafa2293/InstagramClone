package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //UI components
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnSignUp;
    private Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(R.string.sign_up_activity_title);

        // init UI components
        edtEmail = findViewById(R.id.edtEnterEmail);
        edtUsername = findViewById(R.id.edtEnterUserName);
        edtPassword = findViewById(R.id.edtEnterPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                    onClick(btnSignUp);

                }
                return false;
            }
        });

        // OnClickListeners
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);


        if (ParseUser.getCurrentUser() != null) {
           // ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSignUp:

                if (edtEmail.getText().toString().equals("") || edtPassword.getText().toString().equals("") || edtUsername.getText().toString().equals("")) {

                    FancyToast.makeText(SignUp.this, "Email, Username, Password Is Required", Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtEmail.getText().toString());
                    appUser.setUsername(edtUsername.getText().toString());
                    appUser.setPassword(edtPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up " + edtUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " IS Signed Up", Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this, "There Is An ERROR : " + e.getMessage(), Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }

                            progressDialog.dismiss();

                        }
                    });

                }
                break;
            case R.id.btnLogIn:

                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    //this method for hiding the keyboard when the user touch the rootLayout(background)
    public void rootLayoutTapped(View view){

        try {
            InputMethodManager inputMethodManager  = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method if for the transition to the social media activity
    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}
