package ch.hearc.gcapp.garbages.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import ch.hearc.gcapp.R;
import ch.hearc.gcapp.garbages.Garbage;

/**
 * Created by diogo on 10/24/16.
 */

public class MainActivity extends AppCompatActivity {


    public static int orientation = Configuration.ORIENTATION_PORTRAIT;

    FragmentManager fragmentManager;
    FrameLayout fragmentContainer;
    GarbageListActivity garbageListActivity;
    GarbageDetailsActivity garbageDetailsActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fragmentContainer = (FrameLayout)findViewById(R.id.fragment_container);

        if(fragmentContainer != null){
            fragmentManager = getSupportFragmentManager();

            garbageListActivity = new GarbageListActivity();

            garbageListActivity.setListener(new GarbageListActivity.OnGarbageListActivityInteraction() {
                @Override
                public void onGarbageClicked(Garbage garbage) {
                    Bundle bundle = new Bundle();
                    bundle.putString("garbageName",garbage.getName());
                    garbageDetailsActivity = new GarbageDetailsActivity();
                    garbageDetailsActivity.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    if(orientation == Configuration.ORIENTATION_LANDSCAPE && garbageDetailsActivity != null){
                        fragmentTransaction.replace(R.id.detail_fragment,garbageDetailsActivity);
                    }else{
                        fragmentTransaction.replace(R.id.fragment_container,garbageDetailsActivity);
                    }
                    fragmentTransaction.commit();
                }
            });

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container,garbageListActivity);
            fragmentTransaction.commit();
        }


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;
        Log.d("MainActivity","odsjgosfdhijfd");
        if(orientation == Configuration.ORIENTATION_PORTRAIT && garbageDetailsActivity != null){
            Log.d("MainActivity","HEEYY");
            getSupportFragmentManager().beginTransaction().remove(garbageDetailsActivity).commit();
        }
    }
}
