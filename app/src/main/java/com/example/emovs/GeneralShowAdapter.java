package com.example.emovs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GeneralShowAdapter extends RecyclerView.Adapter<GeneralShowAdapter.AdapterViewHolder> {


    ArrayList<com.example.emovs.GeneralShow> mData;
    String recylerViewDefiningString;
    private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(com.example.emovs.GeneralShow show , String rvDefiningString);
    }

    public GeneralShowAdapter(ListItemClickListener listener , ArrayList<com.example.emovs.GeneralShow> mData, String recylerViewDefiningString){
        this.mData = mData;
        this.recylerViewDefiningString = recylerViewDefiningString;
        mOnClickListener = listener;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_general_show,parent,false);
        final AdapterViewHolder holder = new AdapterViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(mData.get(pos) , recylerViewDefiningString);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getName());
        //holder.imgPoster.setImageBitmap(mData.get(position).getPoster_image());
        Picasso.get().load( MainActivity.SERVER_IMAGES_URL+"/"+mData.get(position).getPosterImagePath()).fit().into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvTitle;
        private ImageView imgPoster;
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.id_rv_movie_name);
            imgPoster = itemView.findViewById(R.id.id_rv_movie_image);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            com.example.emovs.GeneralShow ret =  mData.get(clickedPosition);

        }
    }

}
