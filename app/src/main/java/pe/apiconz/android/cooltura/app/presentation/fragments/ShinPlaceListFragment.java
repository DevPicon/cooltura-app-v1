package pe.apiconz.android.cooltura.app.presentation.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pe.apiconz.android.cooltura.app.R;
import pe.apiconz.android.cooltura.app.model.entities.PlaceEntity;
import pe.apiconz.android.cooltura.app.presentation.adapters.ShinPlaceAdapter;


public class ShinPlaceListFragment extends BaseFragment {

    protected RecyclerView recyclerView;
    private String SHIN_TAG = ShinPlaceListFragment.class.getCanonicalName();
    private ShinPlaceAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<PlaceEntity> places;

    @Override
    protected int getFragmentLayoutResourceId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onCreateView() {

    }

    @Override
    protected void onCreateView(View view) {
        super.onCreateView(view);

        Log.d(SHIN_TAG, "Paso por aqui");
        places = new ArrayList<>();
        initList();


        Log.d(SHIN_TAG, "Paso por aqui");

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new ShinPlaceAdapter(places);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        int scrollPosition = 0;
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        }

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);

    }

    private void initList() {
        // TODO: Borrar 11/29/2015
        PlaceEntity place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
        place = new PlaceEntity();
        place.setCity("Lima");
        place.setName("Hola");
        place.setType("museum");
        places.add(place);
    }

}
