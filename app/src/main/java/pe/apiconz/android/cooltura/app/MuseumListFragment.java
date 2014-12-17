package pe.apiconz.android.cooltura.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MuseumListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> museumList = Arrays.asList("Museo de Arte Italiano","Museo de Arte de Lima", "Museo de Ciencias Naturales", "Museo de la Nación","Museo Arquelógico Rafael");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_item_museum,R.id.txtMuseumName,museumList);

        ListView museumListView = (ListView)rootView.findViewById(R.id.listview_museums);
        museumListView.setAdapter(adapter);

        return rootView;
    }
}
