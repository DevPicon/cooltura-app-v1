package pe.apiconz.android.cooltura.app.presentation.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.presentation.fragments.PlaceListFragment;
import pe.apiconz.android.cooltura.app.utils.Utility;

/**
 * Created by Indra on 05/01/2015.
 */
public class PlaceAdapter extends CursorAdapter {

    @Bind(R.id.txtPlaceName)
    protected TextView txtPlaceNameView;

    @Bind(R.id.txtPlaceCity)
    protected TextView txtPlaceCityView;

    @Bind(R.id.iconPlaceView)
    protected ImageView imgType;

    public PlaceAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_place, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ButterKnife.bind(this, view);

        String placeName = cursor.getString(PlaceListFragment.COL_PLACE_NAME);
        //TextView txtPlaceNameView = (TextView) view.findViewById(R.id.txtPlaceName);
        txtPlaceNameView.setText(placeName);

        String placeCity = cursor.getString(PlaceListFragment.COL_CITY_NAME);
        //TextView txtPlaceCityView = (TextView) view.findViewById(R.id.txtPlaceCity);
        txtPlaceCityView.setText(placeCity);

        String placeTypeName = cursor.getString(PlaceListFragment.COL_TYPE_NAME);
        //ImageView imgType = (ImageView) view.findViewById(R.id.iconPlaceView);
        imgType.setImageResource(Utility.getIconResourceForPlaceType(placeTypeName));

    }
}
