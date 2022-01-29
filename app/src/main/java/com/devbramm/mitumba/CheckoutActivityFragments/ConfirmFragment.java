package com.devbramm.mitumba.CheckoutActivityFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.devbramm.mitumba.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment {

    ImageView gifContainer;

    public ConfirmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        gifContainer = view.findViewById(R.id.gifImageView);

        Glide.with(view.getContext()).load(R.drawable.nutmeg).into(gifContainer);

        // Inflate the layout for this fragment*/
        return view;
    }
}
