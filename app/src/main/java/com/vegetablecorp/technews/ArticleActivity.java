package com.vegetablecorp.technews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    // Create a constant tag for so we know log messages came from this class
    private static final String LOG_TAG = ArticleActivity.class.getName();
    // int value for the article loader ID
    private static final int ARTICLE_LOADER_ID = 1;
    // Article Adapter
    private ArticleAdapter mAdapter;
    // search URL used for the API request (baseApiUrl
    private String searchURL;
    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        searchURL = String.format(getString(R.string.GUARDIAN_API_SEARCH));
        Log.i(LOG_TAG, "Search URL = " + searchURL);

        ListView articleListView = (ListView) findViewById(R.id.list);

        // Add the empty state TextView to a variable
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        // Set the empty view on the list
        articleListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());
        articleListView.setAdapter(mAdapter);

        // Create new ConnectivityManager to check state of the network
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch the data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Create new LoaderManager to manage our loaders
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);

        } else {
            // Else hide loading indicator
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Display error message in the empty state TextView
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current article that was clicked on
                Article currentArticle = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri articleUri = Uri.parse(String.valueOf(currentArticle.getmWebUrl()));

                // Create a new intent to view the article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        return new ArticleLoader(this, searchURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // Set empty state text to display "No articles found."
        mEmptyStateTextView.setText(R.string.no_article);

        // Clear the adapter of previous article data
        mAdapter.clear();

        // If there is a valid list of {@link Article}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
