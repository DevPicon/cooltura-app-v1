package pe.apiconz.android.cooltura.app.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.model.entities.PlaceEntity;

/**
 * Created by Armando on 1/2/2016.
 */
public class WifiFreePlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PlaceEntity> placeEntityList;

    public WifiFreePlaceAdapter(List<PlaceEntity> placeEntityList) {
        this.placeEntityList = placeEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi_place, parent, false);
        return new WifiFreePlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WifiFreePlaceViewHolder wifiFreePlaceViewHolder = (WifiFreePlaceViewHolder) holder;
        PlaceEntity wifiPlaceEntity = placeEntityList.get(position);
        wifiFreePlaceViewHolder.getTxtWifiPlaceName().setText(wifiPlaceEntity.getName());
        wifiFreePlaceViewHolder.getTxtWifiPlaceAddress().setText(wifiPlaceEntity.getAddress());
        wifiFreePlaceViewHolder.getTxtWifiPlaceNotes().setText(wifiPlaceEntity.getInfo());
    }

    @Override
    public int getItemCount() {
        if (placeEntityList != null) {
            return placeEntityList.size();
        }
        return 0;
    }

}
