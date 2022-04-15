package com.example.emovs.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.emovs.GeneralShow;
import com.example.emovs.MainActivity;
import com.example.emovs.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainScreenPosterLoader extends AsyncTaskLoader<GeneralShow> {
    GeneralShow loadedShow = null;
    String link;
    Context context;


    public MainScreenPosterLoader(Context context, String link){
        super(context);
        this.context = context;
        this.link = link;
    }

    @Nullable
    @Override
    public GeneralShow loadInBackground() {
        GeneralShow latestShow = null;
        try {
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_MAIN_SCREEN_POSTER));
            params.add(link);
            latestShow = (GeneralShow) NetworkOP.executeNetworkOperation(params);
            loadedShow = latestShow;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return latestShow;
    }
    @Override
    public void deliverResult(@Nullable GeneralShow data) {
        this.loadedShow = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedShow == null)
            forceLoad();
        else
            deliverResult(loadedShow);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedShow = null;
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
