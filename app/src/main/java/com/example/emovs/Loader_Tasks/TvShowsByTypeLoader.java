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

public class TvShowsByTypeLoader extends AsyncTaskLoader<ArrayList<GeneralShow>> {
    ArrayList<GeneralShow> loadedShows = null;
    String link;
    Context context;
    String type;

    public TvShowsByTypeLoader(Context context, String link ,String type){
        super(context);
        this.context = context;
        this.link = link;
        this.type = type;
    }

    @Nullable
    @Override
    public ArrayList<GeneralShow> loadInBackground() {
        ArrayList<GeneralShow> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_TV_SHOWS_BY_TYPE));
            params.add(link);
            params.add(type);
            ret = (ArrayList<GeneralShow>) NetworkOP.executeNetworkOperation(params);
            loadedShows = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<GeneralShow> data) {
        this.loadedShows = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedShows == null)
            forceLoad();
        else
            deliverResult(loadedShows);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedShows = null;
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
