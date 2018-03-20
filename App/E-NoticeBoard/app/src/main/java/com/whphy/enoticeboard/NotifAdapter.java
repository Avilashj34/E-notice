package com.whphy.enoticeboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whphy.enoticeboard.activities.FullNotif;
import com.whphy.enoticeboard.models.NotifModel;

import java.util.ArrayList;

/**
 * Created by Jaykishan on 27/7/2017.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.MyViewHolder> {

    Context context;
    ArrayList<NotifModel> classArrayList;

    public NotifAdapter(Context context, ArrayList<NotifModel> classArrayList) {
        this.context = context;
        this.classArrayList = classArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(classArrayList.get(position).getTitle());
        holder.notif.setText(classArrayList.get(position).getData());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FullNotif.class);
                intent.putExtra("TITLE",classArrayList.get(position).getTitle());
                intent.putExtra("NOTIF",classArrayList.get(position).getData());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,notif;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title_notif);
            notif = (TextView)itemView.findViewById(R.id.data_notif);
            cardView = (CardView)itemView.findViewById(R.id.notifcard);
        }
    }
}
