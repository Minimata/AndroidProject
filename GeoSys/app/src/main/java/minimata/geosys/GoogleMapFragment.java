package minimata.geosys;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoogleMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoogleMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = GoogleMapFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private LatLng userPosition;
    private LatLng selectedPosition;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private MarkerOptions userMarkerOptions = new MarkerOptions();
    private MarkerOptions selectedMarkerOptions = new MarkerOptions();
    private UiSettings mapUiSettings;
    private CircleOptions circleOptions = new CircleOptions();
    private boolean editionMode = false;
    private HashMap<String,HashMap<LatLng,Double>> savedPositions = new HashMap<String, HashMap<LatLng, Double>>();

//    private OnFragmentInteractionListener mListener;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleMapFragment newInstance(String param1, String param2) {
        GoogleMapFragment fragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // You can only findFragmentById when the layout has been inflated. Which is done in
        // the onCreateView. Refer to the Fragment lifecycle. RTFM
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void updateLocation(Location location) {
        userPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.clear();
        mMap.addMarker(userMarkerOptions.position(userPosition));
        if (editionMode) {
            mMap.addMarker(selectedMarkerOptions.position(selectedPosition));
            mMap.addCircle(circleOptions);
        }
    }

    public void updateRadius(int radius) {
        Log.d("d", Integer.toString(radius));
    }

    private void initListeners() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (editionMode) {
                    mMap.clear();
                    drawAllMarkers();
                    mMap.addMarker(userMarkerOptions.position(userPosition));
                    selectedPosition = latLng;
                    selectedMarkerOptions.title("You selected this.");
                    mMap.addMarker(selectedMarkerOptions.position(selectedPosition));
                    mMap.addCircle(circleOptions.center(latLng));
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //setting Map UI settings
        mMap = googleMap;
        mapUiSettings = mMap.getUiSettings();
        mapUiSettings.setCompassEnabled(true);
        mapUiSettings.setZoomControlsEnabled(true);
        //setting Map interaction event listeners
        initListeners();
        //setting initial marker userPosition on HE-Arc
        userPosition = new LatLng(46.997455, 6.938350);
        initMapObjects();
        //moving camera over marker's userPosition, zooming to street level
        mMap.addMarker(userMarkerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userPosition));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 17.0f));
        HashMap<LatLng,Double> temp = new HashMap<LatLng,Double>();
        temp.put(new LatLng(46.9975,6.9388),30.0);
        savedPositions.put("Yolo1",temp);
        temp = new HashMap<LatLng,Double>();
        temp.put(new LatLng(46.9979,6.9388),30.0);
        savedPositions.put("Yolo2",temp);
        temp = new HashMap<LatLng,Double>();
        temp.put(new LatLng(46.9975,6.9395),30.0);
        savedPositions.put("Yolo3",temp);
        drawAllMarkers();
    }

    private void drawAllMarkers(){
        for(Map.Entry<String, HashMap<LatLng,Double>> entry : savedPositions.entrySet()) {
            LatLng position = new LatLng(0,0);
            Double radius = 30.0;
            for(Map.Entry<LatLng,Double> entry2 : entry.getValue().entrySet()) {
                position = entry2.getKey();
                radius = entry2.getValue();
            }
            createNewCircleMarker(position,radius,entry.getKey());
        }
    }

    private void createNewCircleMarker(LatLng position, double radius, String name){
        selectedMarkerOptions.position(position);
        selectedMarkerOptions.title(name);
        mMap.addMarker(selectedMarkerOptions);
        circleOptions.radius(radius);
        circleOptions.center(position);
        mMap.addCircle(circleOptions);
    }
    private void initMapObjects() {
        userMarkerOptions.position(userPosition);
        userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        userMarkerOptions.title("You are here.");

        selectedMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        selectedMarkerOptions.title("You selected this...");

        circleOptions.radius(30.0);
        circleOptions.strokeWidth(3);
        circleOptions.fillColor(Color.argb(30, 255, 0, 0));
        circleOptions.strokeColor(Color.RED);
    }
    /*------------------------MATH UTILITY--------------------------------------------------------*/
    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius=6371;//radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec =  Integer.valueOf(newFormat.format(km));
        double meter=valueResult%1000;
        int  meterInDec= Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
    }
    /*------------------------UTILITY FOR EXTERNAL OBJECTS----------------------------------------*/
    public LatLng getSelectedPosition(){
        return selectedPosition;
    }
    public int getNumberOfAlarms() { return savedPositions.size(); }
    public Map<String,HashMap<LatLng,Double>> getPositions() { return savedPositions; }
    public void setSavedPositions(int id, HashMap<LatLng,Double> positionsToSet){
        HashMap<LatLng,Double> area = new HashMap<LatLng, Double>();
        for(Map.Entry<LatLng,Double> entry2 : positionsToSet.entrySet()){
            LatLng position = entry2.getKey();
            double radius = entry2.getValue();
            area.put(position,radius);
        }
        savedPositions.put("Alarm " + Integer.toString(id), area);
    }

    public boolean IsEditionMode() {
        return this.editionMode;
    }
    public boolean IsViewMode() {
        return !this.editionMode;
    }

    public void activateEditionMode(boolean bool) {
        this.editionMode = bool;
    }

    public void setCircleRadius(double radius) {
        this.circleOptions.radius(radius);
    }

    public void setSelectedMarkerName(String name) {
        this.selectedMarkerOptions.title(name);
    }

    public void clear() {
        mMap.clear();
    }

    public boolean isUserInArea(LatLng eventlocation, double eventradius){
        return (calculationByDistance(eventlocation,userPosition)<=eventradius);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    //----------------------------------------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mListener = (OnFragmentInteractionListener) context;
        initializeLocationEngine();
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//            initializeLocationEngine();
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    private void initializeLocationEngine() {
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Runnable geolocation = new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        } catch (SecurityException e) {
                            Log.d(TAG, "changed location from geoloc thread DUDE CHECK IT OUT HERE!!");
                            Log.d(TAG, e.getMessage());
                        }
                    }
                });

            }
        };
        new Thread(geolocation).start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
