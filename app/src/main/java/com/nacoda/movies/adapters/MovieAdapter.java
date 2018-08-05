package com.nacoda.movies.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.nacoda.movies.libs.Helper;
import com.nacoda.movies.R;
import com.nacoda.movies.model.Movie;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nacoda.movies.libs.Constants.IMAGE_URL;

/**
 * Created by Mayburger on 1/11/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    private Context context;
    private final OnItemClickListener listener;

    public MovieAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies.get(position);

        Glide.with(context).load(IMAGE_URL + movie.getPoster_path()).apply(new RequestOptions().centerCrop()).transition(DrawableTransitionOptions.withCrossFade()).into(holder.posterItem);
        holder.titleItem.setText(movie.getTitle());
        holder.ratingItem.setRating(movie.getVote_average() / 2);
        holder.genreItem.setText(Helper.getGenres(movie.getGenre_ids(), context.getString(R.string.genres_language)));
        holder.click(movies.get(position), listener, holder.parentItem, position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface OnItemClickListener {
        void onClick(Movie movie, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster_item)
        ImageView posterItem;
        @BindView(R.id.title_item)
        TextView titleItem;
        @BindView(R.id.rating_item)
        RatingBar ratingItem;
        @BindView(R.id.genre_item)
        TextView genreItem;
        @BindView(R.id.parent_item)
        RelativeLayout parentItem;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void click(final Movie movie, final OnItemClickListener listener, View view, final int position) {
            view.setOnClickListener(view1 -> {
                listener.onClick(movie, position);
            });
        }
    }
}

