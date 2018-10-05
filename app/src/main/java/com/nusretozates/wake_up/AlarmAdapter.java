package com.nusretozates.wake_up;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapter extends    RecyclerView.Adapter<AlarmAdapter.MyViewHolder>
{

    ArrayList<Alarm> mProductList;
    LayoutInflater inflater;

    public AlarmAdapter(Context context, ArrayList<Alarm> products) {
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_back, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Alarm selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView alarmsaati, alarmtarihi,alarmID;
        ImageView productImage;


        public MyViewHolder(View itemView) {
            super(itemView);
            alarmsaati = (TextView) itemView.findViewById(R.id.saatim);
            alarmtarihi = (TextView) itemView.findViewById(R.id.tarih);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            alarmID = (TextView) itemView.findViewById(R.id.alarmID);
            productImage.setOnClickListener(this);

        }

        public void setData(Alarm selectedProduct, int position) {

            this.alarmsaati.setText(selectedProduct.getAlarmsaat());
            this.alarmtarihi.setText(selectedProduct.getAlarmgun());
            this.productImage.setImageResource(selectedProduct.getImageID());
        }


        @Override
        public void onClick(View v) {

            Alarm.alarms.remove(new Alarm(alarmsaati.getText().toString(),alarmtarihi.getText().toString(),Integer.valueOf(alarmID.getText().toString())));
        }
    }


}
