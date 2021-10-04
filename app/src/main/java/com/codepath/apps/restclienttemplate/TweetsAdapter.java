package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

  Context context;
  List<Tweet> tweets;

  public TweetsAdapter(Context context, List<Tweet> tweets) {
    this.context = context;
    this.tweets = tweets;
  }

  @NonNull
  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int i) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
    Tweet tweet = tweets.get(position);
    holder.bind(tweet);
  }

  @Override
  public int getItemCount() {
    return tweets.size();
  }

  public void clear() {
    tweets.clear();
    notifyDataSetChanged();
  }

  public void addAll(List<Tweet> tweetList) {
    tweets.addAll(tweetList);
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvTimestamp;

    public ViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);
      ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
      tvBody = itemView.findViewById(R.id.tvBody);
      tvScreenName = itemView.findViewById(R.id.tvScreenName);
      tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
    }

    public void bind(Tweet tweet) {
      tvBody.setText(tweet.body);
      tvScreenName.setText(tweet.user.screenName);
      Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
    }
  }
}
