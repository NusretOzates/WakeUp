package com.nusretozates.wake_up.Adapters

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.nusretozates.wake_up.Activities.AlarmRecieverActivity
import com.nusretozates.wake_up.Activities.MainActivity
import com.nusretozates.wake_up.R
import com.nusretozates.wake_up.Utils.Alarm
import java.util.*

class AlarmAdapter(context: Context, internal var mProductList: ArrayList<Alarm>) : RecyclerView.Adapter<AlarmAdapter.MyViewHolder>() {
    internal var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.card_view_back, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedProduct = mProductList[position]
        holder.setData(selectedProduct, position)

    }

    override fun getItemCount(): Int {
        return mProductList.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var alarmsaati: TextView
        var alarmtarihi: TextView
        var alarmID: TextView
        var productImage: ImageView


        init {
            alarmsaati = itemView.findViewById(R.id.saatim)
            alarmtarihi = itemView.findViewById(R.id.tarih)
            productImage = itemView.findViewById(R.id.productImage)
            alarmID = itemView.findViewById(R.id.alarmID)
            productImage.setOnClickListener(this)

        }

        fun setData(selectedProduct: Alarm, position: Int) {

            this.alarmsaati.text = selectedProduct.alarmsaat
            this.alarmtarihi.text = selectedProduct.alarmgun
            this.productImage.setImageResource(selectedProduct.imageID)
            this.alarmID.text = selectedProduct.alarmID
        }


        override fun onClick(v: View) {

            Alarm.alarms.remove(Alarm(alarmsaati.text.toString(), alarmtarihi.text.toString(), alarmID.text.toString()))
            Log.d("Alarm Remove", "Alarm REMOVED")
            val intent = Intent(v.context, AlarmRecieverActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(v.context,
                    Integer.valueOf(alarmID.text.toString()), intent, PendingIntent.FLAG_CANCEL_CURRENT)
            val am = v.context.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            am.cancel(pendingIntent)
            val a = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(a)

        }
    }


}
