package kr.dsm.wherehere;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BeINone on 2017-04-01.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentVH> {

    private List<Comment> mCommentList;

    public CommentAdapter() {
        mCommentList = new ArrayList<>();
    }

    @Override
    public CommentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_comment, parent, false);
        return new CommentVH(view);
    }

    @Override
    public void onBindViewHolder(CommentVH holder, int position) {
        holder.bind(mCommentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public class CommentVH extends RecyclerView.ViewHolder {

        private TextView mWriterTV;
        private TextView mContentTV;

        public CommentVH(View itemView) {
            super(itemView);
            mWriterTV = (TextView) itemView.findViewById(R.id.tv_comment_writer);
            mContentTV = (TextView) itemView.findViewById(R.id.tv_comment_content);
        }

        public void bind(Comment comment) {
            mWriterTV.setText(comment.getWriter());
            mContentTV.setText(comment.getContent());
        }
    }

}
