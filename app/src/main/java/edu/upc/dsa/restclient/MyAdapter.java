package edu.upc.dsa.restclient;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Track track);
    }

    private Context context;
    private List<Track> trackList;
    private OnItemClickListener listener;

    public MyAdapter(Context context, List<Track> trackList, OnItemClickListener listener) {
        this.context = context;
        this.trackList = trackList;
        this.listener = listener;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        if (trackList.get(i).getId() != null)
            myViewHolder.id.setText(trackList.get(i).getId());
        else
            myViewHolder.id.setText("Not defined");
        if (trackList.get(i).getTitle() != null)
            myViewHolder.title.setText(trackList.get(i).getTitle());
        else
            myViewHolder.title.setText("Not defined");
        if (trackList.get(i).getSinger() != null)
            myViewHolder.singer.setText(trackList.get(i).getSinger());
        else
            myViewHolder.singer.setText("Not defined");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(trackList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (trackList != null)
            return trackList.size();
        else
            return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView title;
        private TextView singer;

        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.recyclerViewId);
            title = itemView.findViewById(R.id.recyclerViewTitle);
            singer = itemView.findViewById(R.id.recyclerViewSinger);
        }
    }
}