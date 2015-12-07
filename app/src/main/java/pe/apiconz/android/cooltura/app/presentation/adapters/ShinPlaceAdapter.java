package pe.apiconz.android.cooltura.app.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.model.entities.PlaceEntity;
import pe.apiconz.android.cooltura.app.presentation.fragments.PlaceViewHolder;
import pe.apiconz.android.cooltura.app.utils.Utility;

/**
 * Created by Armando on 11/29/2015.
 */
public class ShinPlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ShinPlaceAdapter.class.getCanonicalName();

    private List<PlaceEntity> places;

    public ShinPlaceAdapter(List<PlaceEntity> places) {
        this.places = places;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        PlaceViewHolder placeViewHolder = (PlaceViewHolder) holder;
        placeViewHolder.getPlaceCity().setText(places.get(position).getCity());
        placeViewHolder.getPlaceName().setText(places.get(position).getName());
        placeViewHolder.getPlaceImage().setImageResource(Utility.getIconResourceForPlaceType(places.get(position).getType()));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (places != null) {
            return places.size();
        }

        return 0;
    }
}
