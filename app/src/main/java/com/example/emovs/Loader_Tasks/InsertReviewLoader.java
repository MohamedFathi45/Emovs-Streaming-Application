package com.example.emovs.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.emovs.MainActivity;
import com.example.emovs.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class InsertReviewLoader extends AsyncTaskLoader<String> {

    String loadedRes = null;
    String link;
    Context context;
    String user_id;
    String show_id;
    String comment;

    public InsertReviewLoader(Context context, String link,String user_id , String show_id,String comment){
        super(context);
        this.context = context;
        this.link = link;
        this.user_id = user_id;
        this.show_id = show_id;
        this.comment = comment;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_INSERT_REVIEW));
            params.add(link);
            params.add(user_id);
            params.add(show_id);
            params.add(comment);
            ret = (String)NetworkOP.executeNetworkOperation(params);
            loadedRes = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable String data) {
        this.loadedRes = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedRes == null)
            forceLoad();
        else
            deliverResult(loadedRes);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedRes = null;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
