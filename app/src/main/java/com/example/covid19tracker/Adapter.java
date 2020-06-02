package com.example.covid19tracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<states> allstates;
    boolean value;

    public Adapter(Context context,ArrayList<states> allstates,boolean value) {
        this.value=value;
        this.allstates = allstates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        ViewHolder v= new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tv1.setText(allstates.get(position).getState());
        holder.tv2.setText(allstates.get(position).getConfirm());
        holder.tv3.setText(allstates.get(position).getActive());
        holder.tv4.setText(allstates.get(position).getDeath());
        holder.tv5.setText(allstates.get(position).getCured());
        if(value==true) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), districtActivity.class);
                    intent.putExtra("state", allstates.get(position).getState());
                    intent.putExtra("total", allstates.get(position).getConfirm());
                    intent.putExtra("active", allstates.get(position).getActive());
                    intent.putExtra("dead", allstates.get(position).getDeath());
                    intent.putExtra("cured", allstates.get(position).getCured());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return allstates.size();
    }

   /* @Override
    public Filter getFilter() {
        return Filter;

    };*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4,tv5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.stateTV);
            tv2=itemView.findViewById(R.id.confirmedTV);
            tv3=itemView.findViewById(R.id.activeTV);
            tv4=itemView.findViewById(R.id.deathTV);
            tv5=itemView.findViewById(R.id.curedTV);
        }
    }
}
