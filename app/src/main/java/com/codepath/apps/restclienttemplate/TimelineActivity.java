package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

  public static final String TAG = "TimelineActivity";
  TwitterClient client;
  RecyclerView rvTweets;
  List<Tweet> tweets;
  TweetsAdapter adapter;
  SwipeRefreshLayout swipeContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    client = TwitterApp.getRestClient(this);
    swipeContainer = findViewById(R.id.swipeContainer);
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        Log.i(TAG, "fetching new data!");
        populateHomeTimeline();
      }
    });
    rvTweets = findViewById(R.id.rvTweets);
    tweets = new ArrayList<>();
    adapter = new TweetsAdapter(this, tweets);
    rvTweets.setLayoutManager(new LinearLayoutManager(this));
    rvTweets.setAdapter(adapter);
    populateHomeTimeline();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.compose) {

    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void populateHomeTimeline() {
    client.getHomeTimeline(new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Headers headers, JSON json) {
        Log.i(TAG, "onSuccess!" + json.toString());
        JSONArray jsonArray = json.jsonArray;
        try {
          adapter.clear();
          adapter.addAll(Tweet.fromJsonArray(jsonArray));
          swipeContainer.setRefreshing(false);
        } catch (JSONException e) {
          Log.e(TAG, "Json exception", e);
        }
      }

      @Override
      public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
        Log.i(TAG, "onFailure!" + response, throwable);
      }
    });
  }
}