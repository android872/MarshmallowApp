package com.example.networks.marshmallowapp.restService;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.networks.marshmallowapp.R;
import com.example.networks.marshmallowapp.restService.data.User;
import com.example.networks.marshmallowapp.restService.webservices.WebServiceTask;
import com.example.networks.marshmallowapp.restService.webservices.WebServiceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestMainActivity extends AppCompatActivity {
    private UserInfoTask mUserInfoTask = null;
    private UserEditTask mUserEditTask = null;
    private UserResetTask mUserResetTask = null;
    private UserDeleteTask mUserDeleteTask = null;

    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mnameText;
    private EditText mPhoneNumberText;
    private EditText mNoteText;

    private interface ConfirmationListener{
        void onConfirmation(boolean isConirmed);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_main);
        initViews();
        showProgress(true);
        mUserInfoTask = new UserInfoTask();
        mUserInfoTask.execute();
    }
private void initViews(){
    mEmailText = (EditText) findViewById(R.id.email);
    mPasswordText = (EditText) findViewById(R.id.password);
    mnameText = (EditText) findViewById(R.id.name);
    mPhoneNumberText = (EditText) findViewById(R.id.phoneNumber);
    mNoteText = (EditText) findViewById(R.id.note);
}

private void showProgress(final boolean isShow){
findViewById(R.id.progress).setVisibility(isShow ? View.VISIBLE : View.GONE);
    findViewById(R.id.info_form).setVisibility(isShow ? View.GONE : View.VISIBLE);
}

private void populateText(){
    User user = RestServiceApplication.getInstance().getUser();
    mEmailText.setText(user.getEmail());
    mPasswordText.setText(user.getPassword());
    mnameText.setText(user.getName() == null ? "" : user.getName());
    mPhoneNumberText.setText(user.getPhoneNumer() == null ? "" : user.getPhoneNumer());
    mNoteText.setText(user.getNote() == null ? "" : user.getNote());


}

public void clickUpdateButton(View view){
    if (mPasswordText.getText().toString().trim().length() >= 5){
        mUserEditTask = new UserEditTask();
        mUserEditTask.execute();

    }else{
        Toast.makeText(this,R.string.error_password_length,Toast.LENGTH_LONG).show();
    }
}

public  void clickDeleteButton(View view){
showConfigurationDialog(new ConfirmationListener(){
    @Override
    public void onConfirmation(boolean isConirmed) {
        if(isConirmed){
            showProgress(true);
            mUserDeleteTask = new UserDeleteTask();
            mUserDeleteTask.execute();
        }
    }
});
}

public void clickResetButton(View view){
    showConfigurationDialog(new ConfirmationListener() {
        @Override
        public void onConfirmation(boolean isConirmed) {
            if (isConirmed){
                showProgress(true);
                mUserResetTask = new UserResetTask();
                mUserResetTask.execute();
            }
        }
    });
}
public void clickSignOutButton(View view){
showLoginScreen();
}

private void showLoginScreen(){
    Intent intent = new Intent(RestMainActivity.this,LoginRegisterActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
}

private void showConfigurationDialog(final ConfirmationListener confirmationListener){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Confirmation");
    builder.setMessage("Are you sure ? this operation can not be undone");
    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            confirmationListener.onConfirmation(true);
            dialog.dismiss();
        }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            confirmationListener.onConfirmation(false);
            dialog.dismiss();
        }
    });

    AlertDialog alertDialog = builder.create();
    alertDialog.setCancelable(true);
    alertDialog.show();
}


    private abstract class ActivityWeServiceTask extends WebServiceTask{
        public ActivityWeServiceTask(WebServiceTask webServiceTask){
            super(RestMainActivity.this);
        }

        @Override
        public void showProgress() {
            RestMainActivity.this.showProgress(true);
        }

        @Override
        public void hideProgress() {
            RestMainActivity.this.showProgress(false);

        }

        @Override
        public void performSuccessfulOperation() {
            populateText();
        }
    }

    private class UserInfoTask extends ActivityWeServiceTask{
        public UserInfoTask(){
            super(mUserInfoTask);
        }

        @Override
        public boolean performRequest() {
            ContentValues contentValues = new ContentValues();
            User user = RestServiceApplication.getInstance().getUser();
            contentValues.put(Constants.ID,user.getId());
            contentValues.put(Constants.ACCESS_TOKEN,RestServiceApplication.getInstance().getAccessToken());
            JSONObject obj = WebServiceUtils.requestJSONObject(Constants.INFO_URL,
                    WebServiceUtils.METHOD.GET,contentValues,null);
            if(!hasError(obj)){
                JSONArray jsonArray = obj.optJSONArray(Constants.INFO);
                JSONObject jsonObject = jsonArray.optJSONObject(0);
                user.setName(jsonObject.optString(Constants.NAME));
                if(user.getName().equalsIgnoreCase("null")){
                    user.setName(null);
                }
                user.setPhoneNumer(jsonObject.optString(Constants.PHONE_NUMBER));
                if (user.getPhoneNumer().equalsIgnoreCase("null")){
                    user.setPhoneNumer(null);
                }

                user.setNote(jsonObject.optString(Constants.NOTE));
                if (user.getNote().equalsIgnoreCase("null")){
                    user.setNote(null);
                }
                user.setId(jsonObject.optLong(Constants.ID_INFO));
                return true;
            }
            return false;
        }
    }
    private class UserEditTask extends ActivityWeServiceTask{
        public UserEditTask(){
            super(mUserEditTask);
        }

        @Override
        public boolean performRequest() {
            ContentValues contentValues = new ContentValues();
            User user = RestServiceApplication.getInstance().getUser();
            contentValues.put(Constants.ID,user.getId());
            contentValues.put(Constants.NAME,mnameText.getText().toString());
            contentValues.put(Constants.PASSWORD,mPasswordText.getText().toString());
            contentValues.put(Constants.PHONE_NUMBER,mPasswordText.getText().toString());
            contentValues.put(Constants.NOTE,mNoteText.getText().toString());
           // contentValues.put(Constants.EMAIL,mEmailText.getText().toString());

            ContentValues urlValues = new ContentValues();
            urlValues.put(Constants.ACCESS_TOKEN,RestServiceApplication.getInstance().getAccessToken());

            JSONObject obj = WebServiceUtils.requestJSONObject(Constants.UPDATE_URL,
                    WebServiceUtils.METHOD.POST,urlValues,contentValues);

            if (!hasError(obj)){
                JSONArray jsonArray = obj.optJSONArray(Constants.INFO);
                JSONObject jsonObject = jsonArray.optJSONObject(0);
                user.setName(jsonObject.optString(Constants.NAME));
                user.setPhoneNumer(jsonObject.optString(Constants.PHONE_NUMBER));
                user.setNote(jsonObject.optString(Constants.NOTE));
                user.setPassword(jsonObject.optString(Constants.PASSWORD));
                //user.setEmail(jsonObject.optString(Constants.EMAIL));
                return true;
            }


            return false;
        }
    }

    private class UserResetTask extends ActivityWeServiceTask{
        public UserResetTask(){
            super(mUserResetTask);
        }

        @Override
        public boolean performRequest() {
            ContentValues contentValues = new ContentValues();
            User user = RestServiceApplication.getInstance().getUser();
            contentValues.put(Constants.ID,user.getId());
            contentValues.put(Constants.ACCESS_TOKEN,
                    RestServiceApplication.getInstance().getAccessToken());
            JSONObject obj = WebServiceUtils.requestJSONObject(Constants.RESET_URL,
                    WebServiceUtils.METHOD.POST,contentValues,null);
            if (!hasError(obj)){
                user.setName("");;
                user.setPhoneNumer("");
                user.setNote("");
                return true;
            }
            return false;
        }
    }

    private class UserDeleteTask extends ActivityWeServiceTask{
        public UserDeleteTask(){
            super(mUserDeleteTask);
        }

        @Override
        public void performSuccessfulOperation() {
            showLoginScreen();
        }

        @Override
        public boolean performRequest() {
            ContentValues contentValues = new ContentValues();
            User user = RestServiceApplication.getInstance().getUser();
            contentValues.put(Constants.ID,user.getId());
            contentValues.put(Constants.ACCESS_TOKEN,
                    RestServiceApplication.getInstance().getAccessToken());
            JSONObject obj = WebServiceUtils.requestJSONObject(Constants.DELETE_URL,
                    WebServiceUtils.METHOD.DELETE,contentValues,null);
            if (!hasError(obj)){
                RestServiceApplication.getInstance().setUser(null);
                return true;
            }
            return false;
        }
    }


}
