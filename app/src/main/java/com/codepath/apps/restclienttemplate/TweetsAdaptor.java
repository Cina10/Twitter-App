package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.media.Image;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdaptor extends RecyclerView.Adapter<TweetsAdaptor.Viewholder> {
    Context context;
    List<Tweet> tweets;

    //Pass in Contexts and List of tweets
    public TweetsAdaptor(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row inflate the layout
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new Viewholder(view);
    }

    //bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //get the data
        Tweet tweet = tweets.get(position);
        //bind tweet to the viewholder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    //Clean all elements of recycler for swipe refresh
    public void clear(){
        tweets.clear(); //make sure you're modifying existing reference to tweets rather than making a new one
        notifyDataSetChanged();
    }

    //AddAll for swipe refresh
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();

    }

    //define a viewholder
    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView ivProfile;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvHandle;
        TextView tvTime;
        ImageView ivMedia;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivMedia = itemView.findViewById(R.id.ivMedia);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvHandle.setText(tweet.user.handle);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfile);
            tvTime.setText(Tweet.getRelativeTime(tweet.createAt));
            if(!tweet.media.equals("")) {
                Glide.with(context).load(tweet.media).into(ivMedia);
                ivMedia.setVisibility(View.VISIBLE);
            } else {ivMedia.setVisibility(View.GONE); }
        }
    }
}
