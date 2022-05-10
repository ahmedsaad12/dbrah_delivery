package com.apps.dbrah_delivery.general_ui;

import android.net.Uri;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.apps.dbrah_delivery.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                if (view instanceof CircleImageView) {
                    CircleImageView imageView = (CircleImageView) view;
                    if (imageUrl != null) {
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                } else if (view instanceof RoundedImageView) {
                    RoundedImageView imageView = (RoundedImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);

                    }
                } else if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;

                    if (imageUrl != null) {

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }
                }

            }
        });


    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {


        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(imageUrl)
                        .centerCrop()
                        .into(imageView);
            }
        }

    }

    @BindingAdapter("qr_image")
    public static void qr_image(View view, String imageUrl) {

        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {
                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                RequestOptions options = new RequestOptions();
                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .load(imageUrl)
                        .apply(options)
                        .into(imageView);
            }
        }


    }

    @BindingAdapter("departmentImage")
    public static void department_image(View view, String imageUrl) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }

        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(Uri.parse(imageUrl)).into(imageView);

            } else {
            }
        }

    }


    @BindingAdapter("createAt")
    public static void dateCreateAt(TextView textView, String s) {
        if (s != null) {
            try {
                String[] dates = s.split("T");
                textView.setText(dates[0]);
            } catch (Exception e) {

            }

        }

    }

    @BindingAdapter({"offerDate"})
    public static void displayOfferDate(TextView textView, String offerDate) {
        if (offerDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }

    @BindingAdapter({"offertime"})
    public static void displayOffertime(TextView textView, String offerDate) {
        if (offerDate != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }
    @BindingAdapter({"offertypetime"})
    public static void displayOfferTypetime(TextView textView, String offerDate) {
        if (offerDate != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("aa", Locale.ENGLISH);
            String m_date = dateFormat.format(new Date(Long.parseLong(offerDate)));
            textView.setText(m_date);
        }
    }
}










