package com.service.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.service.myapplication.R;
import com.service.myapplication.adapters.ServiceAdapter;
import com.service.myapplication.models.ServiceModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Services#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Services extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Services() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Services.
     */
    // TODO: Rename and change types and number of parameters
    public static Services newInstance(String param1, String param2) {
        Services fragment = new Services();
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
        View view = inflater.inflate(R.layout.fragment_servicios, container, false);

        initRecycleView(super.getContext(), view);

        // Inflate the layout for this fragment
        return view;
    }

    private void initRecycleView(Context ctx, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleView_services);
        ArrayList<ServiceModel> services = new ArrayList<>();
        services.add(new ServiceModel("01", "Servicio1", "2020-02-23"));
        services.add(new ServiceModel("02", "Servicio2", "2020-02-24"));
        services.add(new ServiceModel("03", "Servicio3", "2020-02-25"));
        services.add(new ServiceModel("04", "Servicio4", "2020-03-10"));

        ServiceAdapter serviceAdapter = new ServiceAdapter(ctx, services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(serviceAdapter);
    }
}