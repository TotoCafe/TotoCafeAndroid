package com.sohos.totocafeandroid;

/**
 * Created by dilkom71 on 24.11.2015.
 */
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*www.101apps.co.za
displays a list of names in this fragment*/
public class ContactsListFragment extends ListFragment {

    static String[] contactsList = {"Harry", "Peter", "Sally", "Bruno", "Jack", "Peter", "Mary", "Elizabeth"};

    OnContactSelectedListener contactSelectedCallback;

    /* this interface must be implemented by host activity */
    public interface OnContactSelectedListener {
        public void onContactSelected(int position);
    }

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, contactsList);
        setListAdapter(myAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // makes sure parent MainActivity implements
        // the callback interface. If not, it throws an exception.
        try {
            contactSelectedCallback = (OnContactSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " The MainActivity activity must " +
                    "implement OnContactSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        contactSelectedCallback.onContactSelected(position);
    }
}
