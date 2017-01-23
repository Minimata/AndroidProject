package minimata.geosys;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import minimata.geosys.dummy.Alarms;
import minimata.geosys.dummy.DummyContent;
import minimata.geosys.dummy.Settings;
import minimata.geosys.dummy.Types;
import minimata.geosys.model.Area;

import static android.R.drawable.ic_delete;
import static android.R.drawable.ic_input_add;

public class MainActivity extends AppCompatActivity implements
        GoogleMapFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener,
        TypeFragment.OnListFragmentInteractionListener,
        AlarmFragment.OnListFragmentInteractionListener {

    private final static String SAVE_FILENAME = "/areas";
    public static final String EXTRA_AREA = ":extra_area";
    private BottomSheetBehavior behavior;
    private GoogleMapFragment gmapInstance;
    private Thread checkAlarms;
    private View bottomSheet;
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(INITIAL_PERMS, 1);
        setContentView(R.layout.activity_main);

        gmapInstance = ((GoogleMapFragment) getFragmentManager().findFragmentById(R.id.fragment_googlemap));
        if (savedInstanceState != null) {
            return;
        }

        //New alarm button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Alarm selection bottom sheet
        bottomSheet = findViewById(R.id.bottom_sheet);
        //Bottom sheet behaviour
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        /**
         * This here makes the bottom sheet come up to half of screen height.
         * It's not a good practice, but the xml and bottom sheet doesn't allow get along well
         * if we don't wnat the bottom sheet to come up all the way to the top of the screen.
         */
        setBottomSheetHeight();
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //reset settings
                        fab.setImageResource(ic_input_add);
                        //resetting gmap editionmode
                        gmapInstance.activateEditionMode(false);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        gmapInstance.activateEditionMode(false);
                        fab.setImageResource(ic_delete);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.setImageResource(ic_delete);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        //Action button behaviour
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                replaceFragment(new TypeFragment(), args);
                switch (behavior.getState()) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //delete settings
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        //show new settings
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //delete settings
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        break;

                    default:
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                }
            }
        });


        /**
         * loading in memory the previously saved alarms
         */
        ArrayList<Area> areas = readAreasFromFile();
        if (areas != null) {
            gmapInstance.setAreas(areas);
        }

        /**
         * Setting up fragments
         */
        Bundle args = new Bundle();
        args.putSerializable("areas", areas);
        addFragment(new AlarmFragment(), args, R.id.fragment_alarms);
        addFragment(new TypeFragment(), new Bundle(), R.id.fragment_type_settings);

        /**
         * Setting up a thread to check every second if we enter an alarm radius
         */
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000); // Waits for 1 second (1000 milliseconds)
                        for (Area area : gmapInstance.getAreas()) {
                            LatLng position = area.getPosition();
                            float radius = area.getRadius();
                                if (gmapInstance.isUserInArea(position, radius)) {
                                    setAlarm(area.getId());
                                }
                            }
                        }
                    }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        checkAlarms = new Thread(myRunnable);
        checkAlarms.start();
    }

    private void setBottomSheetHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        bottomSheet.getLayoutParams().height = height / 2;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setBottomSheetHeight();
    }

    @Override
    protected void onDestroy() {
        saveAreasToFile(gmapInstance.getAreas());
        checkAlarms.interrupt();
    }

    public void setAlarm(Integer name) {
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(""+name), 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + 1000, pendingIntent);
    }

    public void newAlarm(int radius, LatLng position) {
        gmapInstance.addArea(radius, position);
        saveAreasToFile(gmapInstance.getAreas());
    }

    private void addFragment(Fragment fragment, Bundle args, int id) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment.setArguments(args);
        transaction.add(id, fragment);
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment, Bundle args) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment.setArguments(args);
        transaction.replace(R.id.fragment_type_settings, fragment); // f2_container is your FrameLayout container
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public GoogleMapFragment getGMapFragment() {
        return gmapInstance;
    }

    /**
     * This will be called when a list fragment is pressed, as en event.
     * @param item the item clicked, polmorphed into a dummy.
     */
    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        /**
         * If we click an already set alarm.
         */
        if (item.getClass() == Alarms.AlarmItem.class) {
            //open settingsFragment to edit an already existing alarm
            Bundle args = new Bundle();
            gmapInstance.activateEditionMode(true);
            replaceFragment(new SettingFragment(), args);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        /**
         * If we click a type of setting to configure.
         * For now there's only alarms but there could be another one in the future.
         */
        if (item.getClass() == Types.TypeItem.class) {
            //open settings fragment to create a new alarm
            Bundle args = new Bundle();
            gmapInstance.activateEditionMode(true);
            replaceFragment(new SettingFragment(), args);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        /**
         * Pressing the OKButton on the settingsfragment
         */
        if (item.getClass() == Settings.OKButton.class) {
            //creates an event depending of the type of setting (position, radius, tune, save button, etc)
            newAlarm((Integer) item.data.keySet().toArray()[0], (LatLng) item.data.values().toArray()[0]);

            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            Bundle args = new Bundle();
            replaceFragment(new TypeFragment(), args);
        }
    }

    public void saveAreasToFile(ArrayList<Area> areas) {
        String filename = getFilesDir() + SAVE_FILENAME;
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(areas);
            oos.flush();
            oos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Area> readAreasFromFile() {
        String filename = getFilesDir() + SAVE_FILENAME;
        ArrayList<Area> outputAreas = null;

        try{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            outputAreas = (ArrayList<Area>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // If file has not been yet created
        if(outputAreas == null) outputAreas = new ArrayList<>();

        return outputAreas;
    }

    public void editArea(int areaId) {
        gmapInstance.activateEditionMode(true);
        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.EXTRA_AREA, areaId);
        replaceFragment(new SettingFragment(), bundle);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public void updateCurrentAreaRadius(int radius) {
        gmapInstance.updateRadius(radius);
    }

    public void confirmArea(int id, int radius) {
        gmapInstance.getAreas().get(id).setRadius(radius);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        replaceFragment(new TypeFragment(), new Bundle());
    }
}
