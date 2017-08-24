package com.spinarplus.newsreport;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollo on 8/23/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private List<Article> mArticleList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView sectionTextView;

        public MyViewHolder(View view){
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title_text_view);
            dateTextView = (TextView) view.findViewById(R.id.date_text_view);
            sectionTextView = (TextView) view.findViewById(R.id.section_text_view);
        }
    }

    public ArticleAdapter(List<Article> articleList){
        mArticleList = articleList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article = mArticleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sectionTextView.setText(article.getSection());
        holder.dateTextView.setText(article.getDate());
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item,parent,false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new MyViewHolder(v);
    }

    public Article getItem(int position){
        return mArticleList.get(position);
    }

    public void clear(){
        mArticleList.clear();
        this.notifyDataSetChanged();
    }

    public void addAll(ArrayList<Article> articles){
        clear();
        mArticleList.addAll(articles);
        this.notifyDataSetChanged();
    }

}
