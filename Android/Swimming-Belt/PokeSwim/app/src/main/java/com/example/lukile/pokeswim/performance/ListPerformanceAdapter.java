package com.example.lukile.pokeswim.performance;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukile.pokeswim.R;
import com.example.lukile.pokeswim.model.Performance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListPerformanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Performance> data = new ArrayList<>();

    private Activity activity;

    public ListPerformanceAdapter(ListPerformanceActivity listPerformanceActivity) {
        this.activity = listPerformanceActivity;
        RecyclerView mRecyclerView = ((ListPerformanceActivity) activity).findViewById(R.id.rcv_list_perf);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.item_list_performance, viewGroup, false);
        return new PerfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if(holder instanceof PerfViewHolder) {
            final PerfViewHolder viewHolder = (PerfViewHolder) holder;
            final Date date = data.get(i).getStartTime();
            final Date dateEnd = data.get(i).getEndTime();
            final int distance = data.get(i).getDistance();
            final float speed = data.get(i).getSpeed();

            String datePerfFormatted = formatDate(date);
            String duration = formatHour(date, dateEnd);

            String dateTV = viewHolder.textViewDatePerformance.getText().toString().replace("{date}", datePerfFormatted);
            String durationTV = viewHolder.textViewDurationPerformance.getText().toString().replace("{duration}", duration);
            String speedTV = viewHolder.textViewSpeed.getText().toString().replace("{speed}", Float.toString(speed));
            String distanceTV = viewHolder.textViewDistance.getText().toString().replace("{distance}", Integer.toString(distance));

            viewHolder.textViewDatePerformance.setText(dateTV);
            viewHolder.textViewDurationPerformance.setText(durationTV);
            viewHolder.textViewSpeed.setText(speedTV);
            viewHolder.textViewDistance.setText(distanceTV);
        }
    }
    static class PerfViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDatePerformance;
        TextView textViewDurationPerformance;
        TextView textViewSpeed;
        TextView textViewDistance;

        PerfViewHolder(View itemView) {
            super(itemView);
            textViewDatePerformance = itemView.findViewById(R.id.date_performance_tv);
            textViewDurationPerformance = itemView.findViewById(R.id.duration_performance_tv);
            textViewSpeed = itemView.findViewById(R.id.speed_performance_tv);
            textViewDistance = itemView.findViewById(R.id.distance_performance_tv);
        }
    }

    public void resetData(List<Performance> items) {
        this.data = items;
        notifyDataSetChanged();
    }

    private String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return dateFormat.format(date);
    }

    private String formatHour(Date beginDate, Date endDate) {
        DateFormat hourFormat = new SimpleDateFormat("hh:mm:ss");

        String hourBeginPerfFormatted = hourFormat.format(beginDate);
        String hourEndPerfFormatted = hourFormat.format(endDate);

        Date begin = null;
        Date end = null;

        try {
            begin = hourFormat.parse(hourBeginPerfFormatted);
            end = hourFormat.parse(hourEndPerfFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = end.getTime() - begin.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff /(60 * 1000) % 60;
        long diffHours = diff /(60 * 60 * 1000) % 24;

        return diffHours + ":" + diffMinutes + ":" + diffSeconds;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
