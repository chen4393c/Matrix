package com.laioffer.matrix;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laioffer.matrix.model.Item;

import java.util.List;

public class ReportRecyclerViewAdapter
        extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Item> mItems;
    private LayoutInflater mInflater;

    private OnClickListener mClickListener;

    public ReportRecyclerViewAdapter(Context context, List<Item> items) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mItems = items;
    }

    public interface OnClickListener{
        void setItem(String itemLabel);
    }

    public void setClickListener(ReportRecyclerViewAdapter.OnClickListener callback) {
        mClickListener = callback;
    }

    /**
     * Step 2: create holder prepare listview to show
     * @param parent the listview
     * @param viewType view type
     * @return created view holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * Step 3: render view holder on screen
     * @param holder view holder created by onCreateViewHolder
     * @param position corresponding position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item item = mItems.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.setItem(item.getDrawableLabel());
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Step1 : declare the view holder structure
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        Item mItem;
        TextView mTextView;
        ImageView mImageView;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mTextView = (TextView) itemView.findViewById(R.id.info_text);
            mImageView = (ImageView) itemView.findViewById(R.id.info_img);
        }

        private void bind(Item item) {
            mItem = item;
            mTextView.setText(mItem.getDrawableLabel());
            mImageView.setImageResource(mItem.getDrawableId());
        }
    }
}
