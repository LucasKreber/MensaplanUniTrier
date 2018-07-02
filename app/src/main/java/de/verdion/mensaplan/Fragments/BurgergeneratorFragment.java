package de.verdion.mensaplan.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import de.verdion.mensaplan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BurgergeneratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BurgergeneratorFragment extends Fragment {


    private WebView webView;


    public BurgergeneratorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BurgergeneratorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BurgergeneratorFragment newInstance() {
        BurgergeneratorFragment fragment = new BurgergeneratorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = "https://burgenerator.de/";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.enableUrlBarHiding();
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_burgergenerator, container, false);
        webView = (WebView) view.findViewById(R.id.webview);
        //String url = "https://burgenerator.de/";
        //webView.setWebChromeClient(new WebChromeClient());
        //webView.setWebViewClient(new WebViewClient());
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(url);
        return view;
    }

}
