package com.example.practicamoviles_1tr.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.models.MapPoint;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.practicamoviles_1tr.common.Constantes.DESCRIPTION_KEY;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.TITLE;


public class MapPointMap extends Fragment {

    private MapView mMapView;
    private MapController mMapController;
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
        mpGeoposition = new GeoPoint(getArguments().getDouble(MAPPOINT_LATITUDE), getArguments().getDouble(MAPPOINT_LONGITUDE));

        setMap(view);
        boolean add = mOverlayItems.add(new OverlayItem(getDataIntent.getStringExtra(TITLE), getDataIntent.getStringExtra(DESCRIPTION_KEY), mpGeoposition));
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

    private void setMap(View view){
        mMapView = view.findViewById(R.id.mapViewMapPoint);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(18);

        mMapController.setCenter(mpGeoposition);

    }

    public void goToMap(List<MapPoint> mapPoints, int position, FragmentActivity fragmentActivity){
        FragmentManager manager =  fragmentActivity.getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putDouble(MAPPOINT_LATITUDE, mapPoints.get(position).getLocation().getLatitude());
        bundle.putDouble(MAPPOINT_LONGITUDE, mapPoints.get(position).getLocation().getLongitude());
        Fragment fragment = new MapPointMap();
        fragment.setArguments(bundle);
        manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
    }
}