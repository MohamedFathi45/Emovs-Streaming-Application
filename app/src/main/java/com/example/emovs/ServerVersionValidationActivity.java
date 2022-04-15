package com.example.emovs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.emovs.Loader_Tasks.UserRigisterLoader;
import com.example.emovs.Loader_Tasks.VersionLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import java.util.ArrayList;

public class ServerVersionValidationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object>{

    ArrayList<String> ret;
    private int operationCount = 0;
    private int numOfOperations = 1;
    private static final String  APP_VERSION = "1";
    public static final String SERVER_DOWNLOAD_APP_VERSION_API = com.example.emovs.MainActivity.SERVER_ROOT_LINK_URL+"/info/version";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_version_validation);
        com.example.emovs.MainActivity.account = GoogleSignIn.getLastSignedInAccount(this);
        if(com.example.emovs.MainActivity.account != null){
            numOfOperations = 2;
            registerUserOnServer(com.example.emovs.MainActivity.account.getEmail().toString());
        }
        getCurrentApplicationVersion();
    }



    private void getCurrentApplicationVersion() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString(com.example.emovs.MainActivity.SERVER_LINK , SERVER_DOWNLOAD_APP_VERSION_API);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(com.example.emovs.MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION, queryBundle, this);

    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        Loader loader = null;
        if(id == com.example.emovs.MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            Log.d("DEBUGTOCON" , args.getString(com.example.emovs.MainActivity.SERVER_LINK));
            loader = new VersionLoader(this  , args.getString(com.example.emovs.MainActivity.SERVER_LINK));
        }
        else if(id == com.example.emovs.MainActivity.LOADER_FOR_RIGISTER_USER){
            loader =new UserRigisterLoader(this ,args.getString(com.example.emovs.MainActivity.SERVER_LINK) , args.getString(com.example.emovs.MainActivity.NAME) , args.getString(com.example.emovs.MainActivity.NAMETOW) , args.getString(com.example.emovs.MainActivity.URL));
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {
        int id = loader.getId();
        if(id == com.example.emovs.MainActivity.LOADER_FOR_LOAD_APPLICATION_VERSION){
            if(data != null) {
                ret = (ArrayList<String>) data;
                incrementOperationCount();
            }
        }
        else if(id == com.example.emovs.MainActivity.LOADER_FOR_RIGISTER_USER){
                incrementOperationCount();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    private void registerUserOnServer(String email){

        Bundle queryBundle = new Bundle();
        //  Use putString with SEARCH_QUERY_URL_EXTRA as the key and the String value of the URL as the value
        queryBundle.putString(com.example.emovs.MainActivity.SERVER_LINK, SignInActivity.SERVER_WRITE_USER_API);
        queryBundle.putString(com.example.emovs.MainActivity.NAME , email);          // is every google account has unique id
        queryBundle.putString(com.example.emovs.MainActivity.NAMETOW , com.example.emovs.MainActivity.account.getDisplayName());

        if(com.example.emovs.MainActivity.account.getPhotoUrl() != null){
            queryBundle.putString(com.example.emovs.MainActivity.URL , String.valueOf(com.example.emovs.MainActivity.account.getPhotoUrl()));
        }
        else{
            queryBundle.putString(com.example.emovs.MainActivity.URL , "non");
        }

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(com.example.emovs.MainActivity.LOADER_FOR_RIGISTER_USER, queryBundle, this);
    }


    void incrementOperationCount() {
        synchronized (this) {
            operationCount = operationCount + 1;
            if (operationCount == numOfOperations) {
                if (ret.get(0).equals("0")) {             // server is down for while    allert then exit
                    Toast.makeText(this, "server is down please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                } else {                                   // online
                    if (ret.get(0).equals(APP_VERSION)) {           // open main Activity
                        Intent intent = new Intent(this, com.example.emovs.MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {                       // allert with the new version to download and exit
                        com.example.emovs.DownloadLatestVersionDialog dialog = new com.example.emovs.DownloadLatestVersionDialog(ret.get(1));
                        dialog.show(getSupportFragmentManager(), "dialog");
                    }
                }
            }
        }
    }
}
