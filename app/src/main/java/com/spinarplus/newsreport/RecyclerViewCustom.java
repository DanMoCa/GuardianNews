package com.spinarplus.newsreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Desarrollo on 8/24/2017.
 */

public class RecyclerViewCustom extends RecyclerView {
    private View emptyView;

    public RecyclerViewCustom(Context context) {
        super(context);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            Adapter<?> adapter = getAdapter();
            if(adapter != null && emptyView != null){
                if(adapter.getItemCount() == 0){
                    emptyView.setVisibility(View.VISIBLE);
                    RecyclerViewCustom.this.setVisibility(View.GONE);
                }else{
                    emptyView.setVisibility(View.GONE);
                    RecyclerViewCustom.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public RecyclerViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewCustom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
