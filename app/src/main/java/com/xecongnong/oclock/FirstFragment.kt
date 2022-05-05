package com.xecongnong.oclock

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startForegroundService
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.xecongnong.oclock.databinding.FragmentFirstBinding
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var list:MutableList<Alarm>
    // This property is only valid between onCreateView and
    // onDestroyView.
    var i=0
    private val binding get() = _binding!!
    lateinit var adapter: AlarmAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener { view ->
            createDialog(requireContext())
        }
        list= mutableListOf()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setAlarm(h:Int,m:Int) {
       val alarm=Alarm(i,h,m,true,false)
        alarm.schedule(requireContext())
        list.add(alarm)
        adapter= AlarmAdapter(requireContext(),list)
        binding.listView.adapter=adapter
        i++
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun createDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        var timePicker = dialog.findViewById<TimePicker>(R.id.time_picker)
        var btn=dialog.findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            setAlarm(timePicker.hour,timePicker.minute)
            dialog.dismiss()
        }
        dialog.show()
    }
}