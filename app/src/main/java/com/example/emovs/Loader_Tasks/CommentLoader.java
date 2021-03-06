package com.example.emovs.Loader_Tasks;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.emovs.Comment;
import com.example.emovs.MainActivity;
import com.example.emovs.NetworkOP;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CommentLoader extends AsyncTaskLoader<ArrayList<Comment>> {

    ArrayList<Comment> loadedComments;
    Context context;
    String link;
    String general_show_id;
    public CommentLoader(Context context, String link , String movie_id){
        super(context);
        this.context = context;
        this.link = link;
        this.general_show_id = movie_id;
    }

    @Nullable
    @Override
    public ArrayList<Comment> loadInBackground() {
        ArrayList<Comment> ret = null;
        try{
            ArrayList<String> params = new ArrayList<>();
            params.add(String.valueOf(MainActivity.LOADER_FOR_LOAD_REVIEWS));
            params.add(link);
            params.add(general_show_id);
            ret = (ArrayList<Comment>) NetworkOP.executeNetworkOperation(params);
            loadedComments = ret;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public void deliverResult(@Nullable ArrayList<Comment> data) {
        this.loadedComments = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if(loadedComments == null)
            forceLoad();
        else
            deliverResult(loadedComments);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        loadedComments = null;
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
