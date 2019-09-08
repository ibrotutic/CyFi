package com.example.cyfi.current_wifi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyfi.R;

import java.util.List;

public class WifiInfoAdapter extends
        RecyclerView.Adapter<WifiInfoAdapter.ViewHolder> {

    List<WifiInfo> wifiInfo;

    public WifiInfoAdapter(List<WifiInfo> wifiInfo) {
        this.wifiInfo = wifiInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View wifiInfoView = inflater.inflate(R.layout.wifi_info_item, parent, false);

        return new ViewHolder(wifiInfoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WifiInfo contact = wifiInfo.get(position);

        TextView propertyName = holder.propertyName;
        propertyName.setText(contact.getPropertyName());

        TextView propertyDescription = holder.propertyDescription;
        propertyDescription.setText(contact.getPropertyDescription());
    }

    @Override
    public int getItemCount() {
        return wifiInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView propertyName;
        public TextView propertyDescription;
        public View bottomDivider;

        public ViewHolder(View itemView) {
            super(itemView);

            propertyName = itemView.findViewById(R.id.property_name);
            propertyDescription = itemView.findViewById(R.id.property_description);
            bottomDivider = itemView.findViewById(R.id.bottom_divider);
        }
    }
}