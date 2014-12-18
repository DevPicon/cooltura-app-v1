package pe.apiconz.android.cooltura.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MuseumListFragment extends Fragment {
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> museumList = Arrays.asList("Museo de Arte Italiano","Museo de Arte de Lima", "Museo de Ciencias Naturales", "Museo de la Nación","Museo Arquelógico Rafael");

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_item_museum,R.id.txtMuseumName,museumList);

        ListView museumListView = (ListView)rootView.findViewById(R.id.listview_museums);
        museumListView.setAdapter(adapter);
        museumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String museum = adapter.getItem(position);
                Intent intent = new Intent(getActivity(),MuseumDetailActivity.class).putExtra(Intent.EXTRA_TEXT, museum);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
