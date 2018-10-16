package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.easeui.R;

import java.util.List;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/15
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public class CustomerChatMoreAdapter extends RecyclerView.Adapter<CustomerChatMoreAdapter.MyHolder> {

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<String> mStringList;
    private MoreItemClickListener mMoreItemClickListener;

    public CustomerChatMoreAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setStringList(List<String> stringList) {
        this.mStringList = stringList;
        notifyDataSetChanged();
    }

    public void setMoreItemClickListener(MoreItemClickListener listener) {
        this.mMoreItemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_customer_more, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.bindView(mStringList.get(position));
        holder.mItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoreItemClickListener != null) {
                    mMoreItemClickListener.onItemClick(mStringList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStringList == null ? 0 : mStringList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final TextView mItemTv;

        public MyHolder(View itemView) {
            super(itemView);
            mItemTv = itemView.findViewById(R.id.more_item_tv);
        }

        private void bindView(String str) {
            mItemTv.setText(str);
        }
    }

    public interface MoreItemClickListener {
        void onItemClick(String itemStr);
    }

}

