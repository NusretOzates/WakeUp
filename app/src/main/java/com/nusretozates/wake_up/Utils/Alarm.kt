package com.nusretozates.wake_up.Utils

import com.nusretozates.wake_up.R
import java.util.*

class Alarm(var alarmsaat: String?, var alarmgun: String?, var alarmID: String?) {
    var imageID = R.drawable.minisaat

    fun addAlarm(alarm: Alarm) {

        alarms.add(alarm)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val alarm = o as Alarm?
        return alarmsaat == alarm!!.alarmsaat &&
                alarmgun == alarm.alarmgun &&
                alarmID == alarm.alarmID
    }

    override fun hashCode(): Int {

        return Objects.hash(alarmsaat, alarmgun, alarmID)
    }

    companion object {
        var alarms = ArrayList<Alarm>()
    }
}
