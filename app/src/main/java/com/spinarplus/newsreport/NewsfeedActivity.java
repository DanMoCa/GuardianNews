package com.spinarplus.newsreport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class NewsfeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    private static final String LOG_TAG = NewsfeedActivity.class.getSimpleName();
    private static final int ARTICLE_LOADER_ID = 1;
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    private static final String API_KEY = "6163ac78-1af3-4d64-8745-2537a8bec3f8";

    private ArticleAdapter mAdapter;
    private ArrayList<Article> mArticles;
    private List<String> mLastSearches;
    private boolean mIsConnected;

    private DrawerLayout mDrawer;
    private MaterialSearchBar mSearchBar;
    private RecyclerViewCustom mArticleListView;
    private ProgressBar mLoadingSpinner;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        ConnectivityManager connectivityManager = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        mIsConnected  = networkInfo != null && networkInfo.isConnected();

        final LoaderManager loaderManager = getLoaderManager();

        mArticleListView = (RecyclerViewCustom) findViewById(R.id.list);
        mSearchBar = (MaterialSearchBar) findViewById(R.id.search_bar);
        mLoadingSpinner = (ProgressBar) findViewById(R.id.progress_bar_spinner);
        mArticles = new ArrayList<Article>();
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mArticleListView.setLayoutManager(llm);
        mArticleListView.setEmptyView(mEmptyTextView);

        if(mIsConnected){
            loaderManager.initLoader(ARTICLE_LOADER_ID,null,this);
        }else{
            mLoadingSpinner.setVisibility(View.GONE);
            mEmptyTextView.setText("No Internet Connection.");
        }

        mAdapter = new ArticleAdapter(new ArrayList<Article>());
        mArticleListView.setAdapter(mAdapter);

        mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                String s = enabled ? "enabled" : "disabled";
//                Toast.makeText(NewsfeedActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Bundle args = new Bundle();
                mLoadingSpinner.setVisibility(View.VISIBLE);
                args.putString("query",text.toString());
                mAdapter.clear();
                loaderManager.restartLoader(ARTICLE_LOADER_ID,args,NewsfeedActivity.this);
//                Toast.makeText(NewsfeedActivity.this,text.toString(),Toast.LENGTH_SHORT).show();
//                startSearch(text.toString(), true, null, true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode){

                }
            }
        });
//        mLastSearches = loadSearchSuggestionFromDisk();

        mArticleListView.addOnItemTouchListener(
            new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    // TODO Handle item click
                    Article currentArticle = mAdapter.getItem(position);

                    String url = currentArticle.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            })
        );

    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG,"Loader created");
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if( args != null && !args.isEmpty()){
            Log.v(LOG_TAG,"Loader reloaded");
            if(args.containsKey("query")){
                uriBuilder.appendQueryParameter("q",args.getString("query"));
            }
        }

        uriBuilder.appendQueryParameter("api-key",API_KEY);

        Log.v(LOG_TAG,"URL: "+uriBuilder.toString());

        return new ArticleLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> articles) {
        Log.v(LOG_TAG,"Loader finished");
        mAdapter.clear();

        mLoadingSpinner.setVisibility(View.GONE);

        if(articles != null && !articles.isEmpty()){
            mAdapter.addAll(articles);

            mEmptyTextView.setText("");
        }else{
            mEmptyTextView.setText("No articles found.");
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        Log.v(LOG_TAG,"Loader has been reset");
        mAdapter.clear();
    }


}
