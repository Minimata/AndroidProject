package minimata.geosys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import minimata.geosys.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements
        MapsFragment.OnFragmentInteractionListener,
        SettingFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.Maps_fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }

            // Pushing MapsFragment
            MapsFragment mapsFragment = new MapsFragment();
//            mapsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Maps_fragment_container, mapsFragment).commit();

            SettingFragment settingFragment = new SettingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.Setting_fragment_container, settingFragment).commit();


        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem dummyItem) {

    }
}
