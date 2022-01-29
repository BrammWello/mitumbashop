package com.devbramm.mitumba.paymentOptionsFragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbramm.mitumba.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AirtelMoneyFragment extends Fragment {


    public AirtelMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_airtel_money, container, false);
    }

}
