package com.service.myapplication.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.service.myapplication.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSION_ID = 44;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FusedLocationProviderClient fusedLocationProviderClient;
    Uri uriCoordinates;
    TextView txtDataLocation;
    TextView txtCoordinates;
    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        if (getActivity() != null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
        txtDataLocation = view.findViewById(R.id.txt_dataLocation);
        txtCoordinates = view.findViewById(R.id.txt_coordinate);
        initLocationText(view);
        return view;
    }

    private void initLocationText(View view) {
        TextView txtUserLocation = view.findViewById(R.id.txt_userLocation);
        ImageButton btnLocationUserMap = view.findViewById(R.id.btn_locationUserMap);

        AtomicInteger counter = new AtomicInteger();

        counter.getAndIncrement();
        HorizontalScrollView scroll_locationOptions = view.findViewById(R.id.scrollVw_locationOptions);

        txtUserLocation.setOnClickListener(view1 -> {
            if(counter.get()%2 != 0) {
                scroll_locationOptions.setVisibility(View.VISIBLE);
                scroll_locationOptions.setHorizontalScrollBarEnabled(false);
                scroll_locationOptions.setScrollBarSize(0);
                getLastLocation();
            } else {
                scroll_locationOptions.setVisibility(View.GONE);
            }
            counter.getAndIncrement();
        });
        btnLocationUserMap.setOnClickListener(
                v -> launchMap()
        );
    }

    @SuppressLint({"MissingPermission", "SetTextI18n"})
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        getDataLocation(latitude, longitude);
                        uriCoordinates = Uri.parse("geo:"+latitude+","+longitude);
                        txtCoordinates.setText("latitud:"+latitude + "\n longitude"+longitude);
                    }
                });
            } else {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        boolean areGranted = false;
        if (getActivity() != null) {
            areGranted = ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED;
        }
        return areGranted;
    }

    private boolean isLocationEnabled() {
        boolean isEnabled = false;
        if (getActivity() != null){
         LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
         isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        return isEnabled;
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        if (getActivity() != null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private final LocationCallback locationCallback = new LocationCallback() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location location = locationResult.getLastLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            getDataLocation(latitude, longitude);
            uriCoordinates = Uri.parse("geo:"+latitude+","+longitude);
            txtCoordinates.setText("latitud:"+latitude + "\n longitude"+longitude);
        }
    };


    private void requestPermissions() {
        if (getActivity() != null) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, PERMISSION_ID
            );
        }
    }

    private void getDataLocation(double latitude, double longitude) {
        Geocoder geocoder;
        if (getActivity() != null) {
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                if (addressList != null && !(addressList.isEmpty())) {
                    String country = addressList.get(0).getCountryName();
                    String state = addressList.get(0).getAdminArea();
                    String city = addressList.get(0).getLocality();
                    StringBuilder userAddress = new StringBuilder();
                    userAddress.append("*País: ").append(country);
                    userAddress.append("\n *Departamento: ").append(state);
                    userAddress.append("\n *Ciudad: ").append(city);
                    txtDataLocation.setText(userAddress);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void launchMap() {
        if (uriCoordinates != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uriCoordinates);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        } else {
          launchMap();
        }
    }
}