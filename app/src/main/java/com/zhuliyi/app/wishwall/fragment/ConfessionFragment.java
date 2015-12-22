package com.zhuliyi.app.wishwall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuliyi.app.wishwall.R;

/**
 * 表白墙
 * by zhuliyi
 * on 2015/11/29
 */
public class ConfessionFragment extends Fragment {

private static ConfessionFragment fragment;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConfessionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfessionFragment newInstance() {
        if (fragment==null) {
            fragment = new ConfessionFragment();
        }
        return fragment;
    }

    public ConfessionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confession, container, false);
    }



}
