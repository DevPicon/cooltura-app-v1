package pe.apiconz.android.cooltura.app.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.apiconz.android.cooltura.app.R;

/**
 * Created by Armando on 1/2/2016.
 */
public class WifiFreePlaceViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txt_wifi_place_name)
    protected TextView txtWifiPlaceName;
    @Bind(R.id.txt_wifi_place_address)
    protected TextView txtWifiPlaceAddress;
    @Bind(R.id.txt_wifi_place_notes)
    protected TextView txtWifiPlaceNotes;

    public WifiFreePlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTxtWifiPlaceAddress() {
        return txtWifiPlaceAddress;
    }

    public void setTxtWifiPlaceAddress(TextView txtWifiPlaceAddress) {
        this.txtWifiPlaceAddress = txtWifiPlaceAddress;
    }

    public TextView getTxtWifiPlaceName() {
        return txtWifiPlaceName;
    }

    public void setTxtWifiPlaceName(TextView txtWifiPlaceName) {
        this.txtWifiPlaceName = txtWifiPlaceName;
    }

    public TextView getTxtWifiPlaceNotes() {
        return txtWifiPlaceNotes;
    }

    public void setTxtWifiPlaceNotes(TextView txtWifiPlaceNotes) {
        this.txtWifiPlaceNotes = txtWifiPlaceNotes;
    }
}
