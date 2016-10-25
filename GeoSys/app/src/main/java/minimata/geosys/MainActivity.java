package minimata.geosys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MapsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pushing MapsFragment
        MapsFragment fragment = new MapsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.activity_main, fragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
