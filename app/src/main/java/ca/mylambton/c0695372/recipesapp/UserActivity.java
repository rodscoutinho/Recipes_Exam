package ca.mylambton.c0695372.recipesapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.mylambton.c0695372.recipesapp.models.User;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class UserActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "ca.mylambton.c0695372.b2cproject.mUser";
    public static final String ARG_DATE = "date";

    private EditText mNameField;
    private EditText mPhoneField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSaveButton;

    private User mUser;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, UserActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Realm.init(this);

        mNameField = (EditText) findViewById(R.id.user_name_field);
        mPhoneField = (EditText) findViewById(R.id.user_phone_field);
        mEmailField = (EditText) findViewById(R.id.user_email_field);
        mPasswordField = (EditText) findViewById(R.id.user_password_field);

        String userEmail = getIntent().getStringExtra(UserActivity.EXTRA_USER);
        if (userEmail != null) {
            Realm realm = Realm.getDefaultInstance();

            mUser = realm.where(User.class).equalTo("email", userEmail).findFirst();
        }

        if (mUser != null) {
            setTitle("Edit User");

            mNameField.setText(mUser.getName());
            mPhoneField.setText(mUser.getPhone());
            mEmailField.setText(mUser.getEmail());
            mEmailField.setEnabled(false);
            mPasswordField.setText(mUser.getPassword());

        } else {
            setTitle("Sign up");
        }

        mSaveButton = (Button) findViewById(R.id.save_user_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPasswordField.getText().toString().length() <= 4) {
                    Toast.makeText(UserActivity.this, "The informed password is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mNameField.getText().toString().length() == 0) {
                    Toast.makeText(UserActivity.this, "The informed name is too short.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mEmailField.getText().toString().length() <= 5 || !mEmailField.getText().toString().contains("@")) {
                    Toast.makeText(UserActivity.this, "Please provide your e-mail address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mPhoneField.getText().toString().length() < 6) {
                    Toast.makeText(UserActivity.this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Realm realm = Realm.getDefaultInstance();

                if (mUser != null) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mUser.setName(mNameField.getText().toString());
                            mUser.setPhone(mPhoneField.getText().toString());
                            mUser.setPassword(mPasswordField.getText().toString());
                        }
                    });

//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra("user", mUser);
//                    setResult(Activity.RESULT_OK, returnIntent);

                } else {

                    final User user = new User();
                    user.setName(mNameField.getText().toString());
                    user.setPhone(mPhoneField.getText().toString());
                    user.setPassword(mPasswordField.getText().toString());
                    user.setEmail(mEmailField.getText().toString());
                    try {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealm(user);
                            }
                        });

//                        Intent returnIntent = new Intent();
//                        returnIntent.putExtra("user", user);
//                        setResult(Activity.RESULT_OK, returnIntent);
                    } catch (RealmPrimaryKeyConstraintException e) {
                        Toast.makeText(UserActivity.this, "This e-mail is already associated with another user.", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();

            }
        });

    }

}
