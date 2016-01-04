package pe.apiconz.android.cooltura.app.presentation.fragments;

import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.model.entities.PlaceEntity;
import pe.apiconz.android.cooltura.app.presentation.adapters.WifiFreePlaceAdapter;

/**
 * Created by Armando on 1/2/2016.
 */
public class WifiPlaceListFragment extends BaseFragment {

    @Bind(R.id.my_recycler_view)
    protected RecyclerView recyclerView;
    private WifiFreePlaceAdapter adapter;

    @Override
    protected int getFragmentLayoutResourceId() {
        return R.layout.recycler_view;
    }

    @Override
    protected void onCreateView(View view) {
        recyclerView = (RecyclerView) view;
    }

    @Override
    protected void onCreateView() {
        List<PlaceEntity> list = getData();
        adapter = new WifiFreePlaceAdapter(list);
        recyclerView.setAdapter(adapter);

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private List<PlaceEntity> getData() {
        List<PlaceEntity> wifiPlaceEntityList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            PlaceEntity wifiPlace = new PlaceEntity();
            wifiPlace.setName("Lugar " + i);
            wifiPlace.setAddress("Una dirección ficticia");
            wifiPlace.setInfo("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales, eros sed mollis tristique, ipsum mauris ornare tellus, ut pulvinar elit nisl id felis. Nam commodo ultricies odio, vitae gravida nisl commodo eu. Maecenas imperdiet tincidunt est, sodales molestie felis tristique et. Duis at nulla vitae est consequat fringilla. Suspendisse id lorem eu nunc rhoncus convallis vitae sollicitudin lacus. Morbi imperdiet lectus et nunc lacinia mattis. In sodales tempor magna, at egestas eros maximus id.");
            wifiPlaceEntityList.add(wifiPlace);
        }

        return wifiPlaceEntityList;
    }
}
