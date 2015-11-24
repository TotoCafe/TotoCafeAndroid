package com.sohos.totocafeandroid;

/**
 * Created by dilkom71 on 24.11.2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*www.101apps.co.za
displays the selected contact's image (well, a placeholder!) and name*/
public class DisplayContactFragment extends Fragment {

    public DisplayContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_contact, container, false);

        TextView textView = (TextView) view.findViewById(R.id.textViewContactsName);

        int position = getArguments().getInt("position", -1);

        if (position != -1) {
            //get the contact's name and display it
            String contactName = ContactsListFragment.contactsList[position];
            textView.setText(contactName);
        } else {
            //there's a problem so display the problem image and text
          //  ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            //imageView.setImageDrawable(getResources().getDrawable(R.drawable.problem));
            textView.setText("There is a problem");
        }

        return view;
    }
}
