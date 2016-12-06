package minimata.geosys;

import android.Manifest;
import android.content.Context;
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
import java.util.*;
import java.io.*;

import java.util.Map;

import minimata.geosys.dummy.Alarms;
import minimata.geosys.dummy.DummyContent;
import minimata.geosys.dummy.Settings;
import minimata.geosys.dummy.Types;

import static android.R.drawable.ic_delete;
import static android.R.drawable.ic_input_add;

public class MainActivity extends AppCompatActivity implements
        GoogleMapFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener,
        TypeFragment.OnListFragmentInteractionListener,
        AlarmFragment.OnListFragmentInteractionListener {

    private String filename = "saves";
    private BottomSheetBehavior behavior;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(INITIAL_PERMS, 1);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        //New alarm button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //Alarm selection bottom sheet
        View bottomSheet = findViewById(R.id.bottom_sheet);
        //Bottom sheet behaviour
        behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        bottomSheet.getLayoutParams().height = height / 2;
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //reset settings
                        fab.setImageResource(ic_input_add);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
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


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_type_settings, new TypeFragment());
        transaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_type_settings, fragment); // f2_container is your FrameLayout container
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        if(item.getClass() == Alarms.AlarmItem.class) {
            //open settingsFragment to edit an already existing alarm
            replaceFragment(new SettingFragment());
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Log.d("d", "ALARM ITEM CLICKED");
        }
        if(item.getClass() == Types.TypeItem.class) {
            //open settings fragment to create a new alarm
            replaceFragment(new SettingFragment());
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Log.d("d", "TYPE ITEM CLICKED");
        }
        if(item.getClass() == Settings.SettingItem.class) {
            //creates an event depending of the type of setting (position, radius, melody, save button, etc)
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            replaceFragment(new TypeFragment());
            Log.d("d", "SETTING ITEM CLICKED");
        }
//        View view = findViewById(R.id.activity_main);
//        Snackbar.make(view, dummyItem.content, Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    public void SaveToFile(Map map) {
        try
        {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map ReadFromFile() {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map)objectInputStream.readObject();
        }
        catch(ClassNotFoundException | IOException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
