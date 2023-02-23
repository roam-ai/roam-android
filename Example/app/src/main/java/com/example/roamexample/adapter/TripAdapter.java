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
import com.roam.sdk.trips_v2.callback.RoamDeleteTripCallback;
import com.roam.sdk.trips_v2.callback.RoamSyncTripCallback;
import com.roam.sdk.trips_v2.callback.RoamTripCallback;
import com.roam.sdk.trips_v2.models.Error;
import com.roam.sdk.trips_v2.models.RoamDeleteTripResponse;
import com.roam.sdk.trips_v2.models.RoamSyncTripResponse;
import com.roam.sdk.trips_v2.models.RoamTripResponse;
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
        final Trips activeTrips = lists.get(position);
        holder.mTxtTripId.setText(activeTrips.getTripId());
        holder.mTxtDate.setText(activeTrips.getUpdatedAt());
        if (!TextUtils.isEmpty(activeTrips.getSyncStatus())) {
            holder.mTxtSyncStatus.setText("Trip status: " + activeTrips.getSyncStatus());
        }
        if (activeTrips.getTripState().equalsIgnoreCase("ended")) {
            holder.mTxtStart.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtResume.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtPause.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtStop.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtForceStop.setTextColor(activity.getResources().getColor(R.color.colorBorder));
        } else if (activeTrips.getTripState().equalsIgnoreCase("started")
                || activeTrips.getTripState().equalsIgnoreCase("paused")
                || activeTrips.getTripState().equalsIgnoreCase("resumed")) {
            if (activeTrips.getTripState().equalsIgnoreCase("paused")) {
                holder.mTxtStart.setTextColor(activity.getResources().getColor(R.color.colorBorder));
                holder.mTxtResume.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.mTxtPause.setTextColor(activity.getResources().getColor(R.color.colorBorder));
                holder.mTxtStop.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.mTxtForceStop.setTextColor(activity.getResources().getColor(R.color.colorBlack));
            } else {
                holder.mTxtStart.setTextColor(activity.getResources().getColor(R.color.colorBorder));
                holder.mTxtResume.setTextColor(activity.getResources().getColor(R.color.colorBorder));
                holder.mTxtPause.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.mTxtStop.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.mTxtForceStop.setTextColor(activity.getResources().getColor(R.color.colorBlack));
            }
        } else {
            holder.mTxtStart.setTextColor(activity.getResources().getColor(R.color.colorBlack));
            holder.mTxtResume.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtPause.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtStop.setTextColor(activity.getResources().getColor(R.color.colorBorder));
            holder.mTxtForceStop.setTextColor(activity.getResources().getColor(R.color.colorBorder));
        }
        holder.mTxtSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "RequestData: " + "tripId- " + activeTrips.getTripId());
                Roam.syncTrip(activeTrips.getTripId(), new RoamSyncTripCallback() {
                    @Override
                    public void onSuccess(RoamSyncTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        removeItem(position);
                        activity.refreshList();
                        Log.e("TAG", "onSuccess: "+new Gson().toJson(response) );
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip Sync: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: "+new Gson().toJson(error) );
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
                hideView(holder.mTxtForceStop);

                Log.e("TAG", "RequestData: " + "tripId- " + activeTrips.getTripId());
                Roam.deleteTrip(activeTrips.getTripId(), new RoamDeleteTripCallback() {
                    @Override
                    public void onSuccess(RoamDeleteTripResponse response) {

                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        removeItem(position);
                        activity.refreshList();


                        Log.e("TAG", "onSuccess: "+new Gson().toJson(response) );

                    }

                    @Override
                    public void onError(Error error) {


                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip deleted: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();

                        Log.e("TAG", "onError: "+new Gson().toJson(error) );
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
                hideView(holder.mTxtForceStop);

                Log.e("TAG", "RequestData: " + activeTrips.getTripId());

                Roam.startTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        activity.refreshList();

                        Log.e("TAG", "onSuccess: " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip started: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + new Gson().toJson(error));
                    }
                });

            }
        });
        holder.mTxtResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "RequestData: " + activeTrips.getTripId() );

                Roam.resumeTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        activity.refreshList();
                        Log.e("TAG", "onSuccess: " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip resume: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + new Gson().toJson(error));
                    }
                });

            }
        });
        holder.mTxtPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "RequestData1: " + activeTrips.getTripId());
                Roam.pauseTrip(activeTrips.getTripId(), new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        activity.refreshList();
                        Log.e("TAG", "onSuccess: " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip pause: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + new Gson().toJson(error));
                    }
                });

            }
        });
        holder.mTxtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "RequestData1: " +activeTrips.getTripId());
                Roam.endTrip(activeTrips.getTripId(), false, new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        activity.refreshList();
                        Log.e("TAG", "onSuccess: " + new Gson().toJson(response));
                    }


                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip stop: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + new Gson().toJson(error));
                    }
                });

            }
        });
        holder.mTxtForceStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "RequestData1: " +activeTrips.getTripId());
                Roam.endTrip(activeTrips.getTripId(), true, new RoamTripCallback() {
                    @Override
                    public void onSuccess(RoamTripResponse response) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        activity.refreshList();
                        Log.e("TAG", "onSuccess: " + new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Error error) {
                        hideView(holder.mProgressBar);
                        showView(holder.mTxtStart);
                        showView(holder.mTxtResume);
                        showView(holder.mTxtPause);
                        showView(holder.mTxtStop);
                        showView(holder.mTxtForceStop);
                        Toast.makeText(activity, "Trip stop: " + activeTrips.getTripId() + " " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: " + new Gson().toJson(error));
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
        private TextView mTxtForceStop;
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
            mTxtForceStop = itemView.findViewById(R.id.txt_force_stop);
            mTxtSync = itemView.findViewById(R.id.txt_sync);
            mTxtSyncStatus = itemView.findViewById(R.id.txt_sync_status);
            mTxtDelete = itemView.findViewById(R.id.txt_delete);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}