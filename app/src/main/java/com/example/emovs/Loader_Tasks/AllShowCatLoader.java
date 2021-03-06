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

public class AllShowCatLoader extends AsyncTaskLoader<ArrayList<GeneralShow>> {

    ArrayList<GeneralShow> loadedShows = null;
    String link;
    Context context;
    String cat;


    public AllShowCatLoader(Context context, String link ,String cat){
        super(context);
        this.context = context;
        this.link = link;
        this.cat = cat;
    }

    @Nullable
    @Override
    public ArrayList<GeneralShow> loadInBackground() {
        ArrayList<GeneralShow> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_ALL_SHOW_CAT));
            params.add(link);
            params.add(cat);
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