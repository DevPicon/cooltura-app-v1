package pe.apiconz.android.cooltura.app.presentation.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pe.apiconz.android.cooltura.app.R;

public class PlaceViewHolder extends RecyclerView.ViewHolder {
    private TextView placeName;
    private TextView placeCity;
    private ImageView placeImage;

    public PlaceViewHolder(View itemView) {
        super(itemView);

        placeName = (TextView) itemView.findViewById(R.id.txtPlaceName);
        placeCity = (TextView) itemView.findViewById(R.id.txtPlaceCity);
        placeImage = (ImageView) itemView.findViewById(R.id.iconPlaceView);

    }

    public TextView getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(TextView placeCity) {
        this.placeCity = placeCity;
    }

    public ImageView getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(ImageView placeImage) {
        this.placeImage = placeImage;
    }

    public TextView getPlaceName() {
        return placeName;
    }

    public void setPlaceName(TextView placeName) {
        this.placeName = placeName;
    }
}