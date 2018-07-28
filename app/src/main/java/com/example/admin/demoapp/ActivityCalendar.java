package com.example.admin.demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ActivityCalendar extends AppCompatActivity {
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final String DEBUG_TAG = "MyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"hera@example.com", "com.example",
                "hera@example.com"};
// Submit the query and get a Cursor object back.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            // Do something with the values...
            Log.d("TEST", "TEST: " + calID + "===" + displayName + "=====" + accountName + "======" + ownerName);

        }

//        long calID = 2;
//        ContentValues values = new ContentValues();
//// The new display name for the calendar
//        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
//        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calID);
//        int rows = getContentResolver().update(updateUri, values, null, null);
//        Log.i(DEBUG_TAG, "Rows updated: " + rows);


        ////
//        long calID = 3;
//        long startMillis = 0;
//        long endMillis = 0;
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(2012, 9, 14, 7, 30);
//        startMillis = beginTime.getTimeInMillis();
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(2012, 9, 14, 8, 45);
//        endMillis = endTime.getTimeInMillis();
//
//        ContentResolver cr2 = getContentResolver();
//        ContentValues values = new ContentValues();
//        values.put(CalendarContract.Events.DTSTART, startMillis);
//        values.put(CalendarContract.Events.DTEND, endMillis);
//        values.put(CalendarContract.Events.TITLE, "Jazzercise");
//        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
//        values.put(CalendarContract.Events.CALENDAR_ID, calID);
//        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
//        Uri uri2 = cr2.insert(CalendarContract.Events.CONTENT_URI, values);
//
//// get the event ID that is the last element in the Uri
//        long eventID = Long.parseLong(uri.getLastPathSegment());

        //SyncEvent(3, "Demo", "", "", "dasdasdasd");
        addEvent();
    }

    public void addEvent() {
        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 7, 27, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, 7, 27, 8, 45);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "hoi.nguyenn@popinz.vn");
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        //values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
//                int apiLevel = android.os.Build.VERSION.SDK_INT;
//        if(apiLevel<21)
//            values.put("visibility", 0);
        values.put("visibility", 0);


//        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(ctx) + "reminders");
//        values = new ContentValues();
//        values.put("event_id", Long.parseLong(event.getLastPathSegment()));
//        values.put("method", 1);
//        values.put("minutes", mTotalMinute);
//        cr.insert(REMINDERS_URI, values);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);




//        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
//        builder.appendPath("time");
//        ContentUris.appendId(builder, beginTime.getTimeInMillis());
//        Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
//        startActivity(intent);
        ContentProviderOperation.newInsert(CalendarContract.Events.CONTENT_URI).withValues(values).build();
//        Uri uri;
//        if (android.os.Build.VERSION.SDK_INT <= 7) {
//
//            uri = Uri.parse("content://calendar/events");
//        } else
//        {
//
//            uri = Uri.parse("content://com.android.calendar/events");
//        }
//        Uri urievent = cr.insert(uri, values);


// get the event ID that is the last element in the Uri
    long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.d("", "addEvent: "+eventID);
        Toast.makeText(this, "Created Calendar Event " + eventID,
                Toast.LENGTH_SHORT).show();
}

    private String getCalendarUriBase(Activity activity) {

        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = activity.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = activity.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }
    public void SyncEvent(long id, String EventName, String Stime, String Etime, String Description) {

        Calendar cal = Calendar.getInstance();
      //  cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
        Date dt = null;
        Date dt1 = null;
        try {
           // dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(Stime);
           // dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(Etime);

            Calendar beginTime = Calendar.getInstance();
           //todo set time start
            //cal.setTime(dt);

             beginTime.set(2018, 7, 27, 16, 30);
//            beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                    cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE));

            Calendar endTime = Calendar.getInstance();
           // cal.setTime(dt1);

             endTime.set(2018, 7, 27, 18, 30);
            // endTime.set(year, month, day, hourOfDay, minute);
//            endTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                    cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
//                    cal.get(Calendar.MINUTE));

            ContentResolver cr = getContentResolver();
            ContentValues values = new ContentValues();

            values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
            values.put(CalendarContract.Events.TITLE, EventName);
            values.put(CalendarContract.Events.DESCRIPTION, Description);
            values.put(CalendarContract.Events.CALENDAR_ID, id);
            // values.put(Events._ID, meeting_id);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            long eventID = Long.parseLong(uri.getLastPathSegment());

            Log.d("TEST", "SyncEvent: "+eventID);


            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, beginTime.getTimeInMillis());
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
            startActivity(intent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
