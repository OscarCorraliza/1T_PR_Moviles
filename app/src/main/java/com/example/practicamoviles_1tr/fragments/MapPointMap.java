package com.example.practicamoviles_1tr.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practicamoviles_1tr.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import static com.example.practicamoviles_1tr.common.Constantes.CURRENT_LOCATION_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.CURRENT_LOCATION_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.DESCRIPTION_KEY;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.TITLE;


public class MapPointMap extends Fragment {

    private MapView mMapView;
    private MapController mMapController;
    GeoPoint myGeoPosition;
    GeoPoint mpGeoposition;


    private ArrayList<OverlayItem> mOverlayItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_point_map, container, false);
        //conseguir el contexto del fragment
        Context fragmentContext = container.getContext();

        Configuration.getInstance().load(fragmentContext, PreferenceManager.getDefaultSharedPreferences(fragmentContext));

        Intent getDataIntent = getActivity().getIntent();
        myGeoPosition = new GeoPoint(getArguments().getDouble(CURRENT_LOCATION_LATITUDE), getArguments().getDouble(CURRENT_LOCATION_LONGITUDE));
        setMap(view);

        mpGeoposition = new GeoPoint(getArguments().getDouble(MAPPOINT_LATITUDE), getArguments().getDouble(MAPPOINT_LONGITUDE));

        boolean add = mOverlayItems.add(new OverlayItem(getDataIntent.getStringExtra(TITLE), getDataIntent.getStringExtra(DESCRIPTION_KEY), myGeoPosition));


        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(mOverlayItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return false;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        },getActivity().getApplicationContext());

        mOverlay.setFocusItemsOnTap(true);
        mMapView.getOverlays().add(mOverlay);



        return view;


    }

    public void setMap(View view){
        mMapView = (MapView) view.findViewById(R.id.mapViewMapPoint);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(18);

        //mMapController.setCenter(myGeoPosition);
        mMapController.setCenter(mpGeoposition);

    }
}