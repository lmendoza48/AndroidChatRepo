package edu.galileo.android.androidchat.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by Alejandro on 19/7/2017.
 * clase para usar libreria para cargar las imagenes
 */
public class GlideImageLoader implements ImageLoading {
    private RequestManager requestManager;

    public GlideImageLoader(Context context) {
        this.requestManager = Glide.with(context);
    }

    @Override
    public void load(ImageView imageView, String url) {
        requestManager.load(url).into(imageView);
    }
}
