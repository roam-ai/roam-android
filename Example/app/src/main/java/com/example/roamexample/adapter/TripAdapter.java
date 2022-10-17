package com.example.roamexample.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.example.roamexample.R;
import com.example.roamexample.ui.TripActivity;
import com.google.gson.Gson;
import com.roam.sdk.Roam;
import com.roam.sdk.models.ActiveTrips;
import com.roam.sdk.models.RoamError;
import com.roam.sdk.trips_v2.callback.RoamSyncTripCallback;
import com.roam.sdk.trips_v2.callback.RoamTripCallback;
import com.roam.sdk.trips_v2.callback.a;
import com.roam.sdk.trips_v2.models.Error;
import com.roam.sdk.trips_v2.models.RoamDeleteTripResponse;
import com.roam.sdk.trips_v2.models.RoamSyncTripResponse;
import com.roam.sdk.trips_v2.models.RoamTripResponse;
import com.roam.sdk.trips_v2.models.Trip;
import com.roam.sdk.trips_v2.models.Trips;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ItemHolder> {

    private TripActivity activity;
    private List<Trips> lists = new ArrayList<>();

    public TripAdapter(TripActivity activity) {
        this.activity = activity;
    }

    public void addList(List<Trips> lists) {
        this.lists.clear();
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        final Trips activeTrips = lists.get(holder.getAdapterPosition());
        Log.e("TAG", "onBindViewHolder: " + new Gson().toJson(activeTrips));
        holder.mTxtTripId.setText(activeTrips.getTripId());
        holder.mTxtDate.setText(activeTrips.getUpdatedAt());
        if (!TextUtils.isEmpty(activeTrips.getSyncStatus())) {
            holder.mTxtSyncStatus.setText("Trip status: " + activeTrips.getSyncStatus());
        }
        holder.mTxtSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.syncTrip(activeTrips.getTripId(), new RoamSyncTripCallback() {
                    @Override
                    public void onSuccess(RoamSyncTripResponse roamSyncTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        removeItem(holder.getAdapterPosition());
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip Sync: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.mTxtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showView(holder.mProgressBar);
                hideView(holder.mTxtStart);
                hideView(holder.mTxtResume);
                hideView(holder.mTxtPause);
                hideView(holder.mTxtStop);
                Roam.deleteTrip(activeTrips.getTripId(), new a() {
                    @Override
                    public void a(RoamDeleteTripResponse roamDeleteTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        removeItem(holder.getAdapterPosition());
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip deleted: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        holder.mTxtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showView(holder.mProgressBar);
                hideView(holder.mTxtStart);
                hideView(holder.mTxtResume);
                hideView(holder.mTxtPause);
                hideView(holder.mTxtStop);
                Roam.startTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse roamTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip started: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.mTxtResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.resumeTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse roamTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip resume: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.mTxtPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.pauseTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse roamTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip pause: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.mTxtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roam.endTrip(activeTrips.getTripId(), false, new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse roamTripResponse) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        activity.refreshList();
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        Toast.makeText(activity, "Trip stop: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    private void removeItem(int position) {
        lists.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView mTxtTripId;
        private TextView mTxtDate;
        private TextView mTxtStart;
        private TextView mTxtResume;
        private TextView mTxtPause;
        private TextView mTxtStop;
        private TextView mTxtSync;
        private TextView mTxtSyncStatus;
        private TextView mTxtDelete;
        private ProgressBar mProgressBar;

        ItemHolder(View itemView) {
            super(itemView);
            mTxtTripId = itemView.findViewById(R.id.txt_trip_id);
            mTxtDate = itemView.findViewById(R.id.txt_date);
            mTxtStart = itemView.findViewById(R.id.txt_start);
            mTxtResume = itemView.findViewById(R.id.txt_resume);
            mTxtPause = itemView.findViewById(R.id.txt_pause);
            mTxtStop = itemView.findViewById(R.id.txt_stop);
            mTxtSync = itemView.findViewById(R.id.txt_sync);
            mTxtSyncStatus = itemView.findViewById(R.id.txt_sync_status);
            mTxtDelete = itemView.findViewById(R.id.txt_delete);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}