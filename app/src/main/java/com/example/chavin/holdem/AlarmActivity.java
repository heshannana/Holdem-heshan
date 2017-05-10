package com.example.chavin.holdem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    //to make the alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pending_intent;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        this.context = this;

        //initializing the alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //inititalizing the time picker
        alarm_timepicker = (TimePicker)findViewById(R.id.timePicker);

        //initialising the status
        status = (TextView)findViewById(R.id.status);

        //create an instance of a calander
        final Calendar calendar = Calendar.getInstance();

        //initialize start button
        Button alarm_on = (Button)findViewById(R.id.buttonNewAlarm);

        final Intent my_intent = new Intent(this.context, Alarm_receiver.class);


        //create an onclick listner
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //setting hours and minutes
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getCurrentMinute());

                //get the int values of hour and minute
                int hour = alarm_timepicker.getCurrentHour();
                int minute= alarm_timepicker.getCurrentMinute();

                //convert the int values to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                //convert 24 hours to 12 hours
                if (hour>12){
                    hour_string= String.valueOf(hour - 12);
                }

                if (minute <10){
                    //12:3 --> 12:03
                    minute_string = "0" + String.valueOf(minute);
                }


                //method that changes the status Textbox
                set_alarm_text("Alarm set to:" + hour_string + ":" + minute_string);

                //create a pending intent to delay the intent until the specified calender time
                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);


                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);
            }
        });

        //initialize end button
        Button alarm_off = (Button)findViewById(R.id.buttonStopAlarm);
        //create an onclick listner
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //method that changes the status textbox
                set_alarm_text("Alarm off");

                //cancel the alarm
                alarm_manager.cancel(pending_intent);
            }
        });

    }

    private void set_alarm_text(String output) {
        status.setText(output);
    }



    @Override
    public void onClick(View view) {
    }

}
