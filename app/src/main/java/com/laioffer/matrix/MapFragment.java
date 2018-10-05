package com.laioffer.matrix;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laioffer.matrix.model.Item;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";

    private LocationTracker locationTracker;
    private MapView mMapView;
    private View mView;
    private GoogleMap mMap;
    private FloatingActionButton mFABReport;
    private FloatingActionButton mFABFocus;
    private Dialog mDialog;
    private RecyclerView mRecyclerView;
    private ReportRecyclerViewAdapter mAdapter;

    private ViewSwitcher mViewSwitcher;
    private String eventType = null;

    //Event specs
    private ImageView mImageCamera;
    private Button mBackButton;
    private Button mSendButton;
    private EditText mCommentEditText;
    private ImageView mEventTypeImg;
    private TextView mTypeTextView;

    public MapFragment() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static MapFragment newInstance() {
        MapFragment mapFragment = new MapFragment();
        return mapFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView(inflater, container, savedInstanceState)");
        mView = inflater.inflate(R.layout.fragment_main, container,
                false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.event_map_view);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();// needed to get the map to display immediately
            mMapView.getMapAsync(this);
        }
        mFABReport = (FloatingActionButton) mView.findViewById(R.id.fab);
        mFABReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        mFABFocus = (FloatingActionButton) mView.findViewById(R.id.fab_focus);
        mFABFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.getMapAsync(MapFragment.this);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "onMapReady(googleMap)");
        MapsInitializer.initialize(getContext());

        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.map_style));

        locationTracker = new LocationTracker(getActivity());
        locationTracker.getLocation();

        double lat = locationTracker.getLatitude();
        double lon = locationTracker.getLongitude();
        Log.i(TAG, "lat, lon: " + lat + ", " + lon);
        LatLng latLng = new LatLng(lat, lon);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(16)// Sets the zoom
                .bearing(90)           // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        MarkerOptions marker = new MarkerOptions().position(latLng).
                title("You");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.boy));

        // adding marker
        Marker mker = mMap.addMarker(marker);
    }

    //Animation show dialog
    private void showDialog() {
        final View dialogView = View.inflate(getActivity(), R.layout.dialog, null);
        mViewSwitcher = (ViewSwitcher) dialogView.findViewById(R.id.view_switcher);
        mDialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(dialogView);

        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                animateDialog(dialogView, true, null);
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    animateDialog(dialogView, false, mDialog);
                    return true;
                }
                return false;
            }
        });
        Animation slide_in_left = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_in_left);
        Animation slide_out_right = AnimationUtils.loadAnimation(getActivity(),
                android.R.anim.slide_out_right);

        mViewSwitcher.setInAnimation(slide_in_left);
        mViewSwitcher.setOutAnimation(slide_out_right);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setupRecyclerView(dialogView);
        setUpEventSpecs(dialogView);
        mDialog.show();
    }

    //Add animation to Floating Action Button
    private void animateDialog(View dialogView, boolean open, final Dialog dialog) {
        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (mFABReport.getX() + (mFABReport.getWidth() / 2));
        int cy = (int) (mFABReport.getY()) + mFABReport.getHeight() + 56;

        if (open) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
//            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(500);
            revealAnimator.start();

        } else {
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(500);
            anim.start();
        }
    }

    //Set up type items
    private void setupRecyclerView(View dialogView) {
        mRecyclerView = dialogView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        List<Item> listItems = new ArrayList<>();
        listItems.add(new Item(Config.POLICE, R.drawable.policeman));
        listItems.add(new Item(Config.TRAFFIC, R.drawable.traffic));
        listItems.add(new Item(Config.NO_ENTRY, R.drawable.no_entry));
        listItems.add(new Item(Config.NO_PARKING, R.drawable.no_parking));
        listItems.add(new Item(Config.SECURITY_CAMERA, R.drawable.security_camera));
        listItems.add(new Item(Config.HEADLIGHT, R.drawable.lights));
        listItems.add(new Item(Config.SPEEDING, R.drawable.speeding));
        listItems.add(new Item(Config.CONSTRUCTION, R.drawable.construction));
        listItems.add(new Item(Config.SLIPPERY, R.drawable.slippery));
        mAdapter = new ReportRecyclerViewAdapter(getActivity(), listItems);
        mAdapter.setClickListener(new ReportRecyclerViewAdapter.OnClickListener() {
            @Override
            public void setItem(String itemLabel) {
                eventType = itemLabel;
                if (mViewSwitcher != null) {
                    mViewSwitcher.showNext();
                    mTypeTextView.setText(eventType);
                    mEventTypeImg.setImageBitmap(
                            BitmapFactory.decodeResource(getContext().getResources(),
                                    Config.trafficMap.get(eventType)));
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpEventSpecs(final View dialogView) {
        mImageCamera = (ImageView) dialogView.findViewById(R.id.event_camera_img);
        mBackButton = (Button) dialogView.findViewById(R.id.event_back_button);
        mSendButton = (Button) dialogView.findViewById(R.id.event_send_button);
        mCommentEditText = (EditText) dialogView.findViewById(R.id.event_comment);
        mEventTypeImg = (ImageView)dialogView.findViewById(R.id.event_type_img);
        mTypeTextView = (TextView)dialogView.findViewById(R.id.event_type);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewSwitcher.showPrevious();
            }
        });
    }
}
