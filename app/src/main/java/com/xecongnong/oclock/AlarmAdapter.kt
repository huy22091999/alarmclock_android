package com.xecongnong.oclock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Switch
import android.widget.TextView
import java.lang.String

class AlarmAdapter(val context:Context,val list: List<Alarm>) :BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val v=LayoutInflater.from(context).inflate(R.layout.item_alarm,p2,false)
        val check=v.findViewById<Switch>(R.id.item_alarm_started)
        check.isChecked=list[p0].isStarted
        check.setOnCheckedChangeListener { _, b ->
            if(b)
            {
                list[p0].schedule(context)
            }
            else{
                list[p0].cancelAlarm(context)
            }
        }
        v.findViewById<TextView>(R.id.item_alarm_time).text=
            String.format("%02d:%02d", list[p0].hour, list[p0].minute)
        return v
    }
}