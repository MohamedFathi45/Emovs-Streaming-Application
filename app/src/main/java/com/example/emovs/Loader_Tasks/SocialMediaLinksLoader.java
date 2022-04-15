package com.example.emovs.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.emovs.MainActivity;
import com.example.emovs.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public class SocialMediaLinksLoader extends AsyncTaskLoader<Map<String,String>> {

    Map<String,String> loadedLinks = null;
    String link;
    Context context;


    public SocialMediaLinksLoader(Context context, String link){
        super(context);
        this.context = context;
        this.link = link;
    }

    @Nullable
    @Override
    public Map<String,String> loadInBackground() {
        Map<String,String> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_SOCIAL_MEDIA_LINKS));
            params.add(link);
            ret = (Map<String,String>) NetworkOP.executeNetworkOperation(params);
            loadedLinks = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable Map<String,String> data) {
        this.loadedLinks = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedLinks == null)
            forceLoad();
        else
            deliverResult(loadedLinks);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedLinks = null;
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
