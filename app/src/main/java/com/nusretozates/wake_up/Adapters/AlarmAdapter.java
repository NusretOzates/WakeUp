package com.nusretozates.wake_up.Adapters;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nusretozates.wake_up.Activities.AlarmRecieverActivity;
import com.nusretozates.wake_up.Activities.MainActivity;
import com.nusretozates.wake_up.R;
import com.nusretozates.wake_up.Utils.Alarm;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.MyViewHolder>
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
            alarmsaati =  itemView.findViewById(R.id.saatim);
            alarmtarihi =  itemView.findViewById(R.id.tarih);
            productImage =  itemView.findViewById(R.id.productImage);
            alarmID =  itemView.findViewById(R.id.alarmID);
            productImage.setOnClickListener(this);

        }

        public void setData(Alarm selectedProduct, int position) {

            this.alarmsaati.setText(selectedProduct.getAlarmsaat());
            this.alarmtarihi.setText(selectedProduct.getAlarmgun());
            this.productImage.setImageResource(selectedProduct.getImageID());
            this.alarmID.setText(selectedProduct.getAlarmID());
        }


        @Override
        public void onClick(View v) {

            Alarm.alarms.remove(new Alarm(alarmsaati.getText().toString(),alarmtarihi.getText().toString(),alarmID.getText().toString()));
            Log.d("Alarm Remove","Alarm REMOVED");
            Intent intent = new Intent(v.getContext(), AlarmRecieverActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(v.getContext(),
                    Integer.valueOf(alarmID.getText().toString()), intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager am =
                    (AlarmManager)v.getContext().getSystemService(Activity.ALARM_SERVICE);
           am.cancel(pendingIntent);
            Intent a = new Intent(v.getContext(),MainActivity.class);
            v.getContext().startActivity(a);

        }
    }


}
