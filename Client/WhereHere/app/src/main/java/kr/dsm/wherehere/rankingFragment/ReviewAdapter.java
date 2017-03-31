package kr.dsm.wherehere.rankingFragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.dsm.wherehere.R;

/**
 * Created by hojak on 2017-04-01.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String,String>> noticeList; //공지사항 정보 담겨있음

    public ReviewAdapter(Context context, ArrayList<HashMap<String,String>> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //recycler view에 반복될 아이템 레이아웃 연결
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_review_ranking,null);
        return new ViewHolder(v);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HashMap<String,String> noticeItem = noticeList.get(position);
        holder.tv_writer.setText(noticeItem.get("writer")); //작성자
        Log.e("[writer]", noticeItem.get("writer"));
        holder.tv_title.setText(noticeItem.get("title")); //제목
        holder.tv_content.setText(noticeItem.get("content")); //내용 일부
        holder.tv_date.setText(noticeItem.get("regist_day")); //작성일
    }

    @Override
    public int getItemCount() {
        return this.noticeList.size();
    }
    /** item layout 불러오기 **/
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_date;
        TextView tv_content;
        TextView tv_writer;
        CardView cv;

        public ViewHolder(View v) {
            super(v);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_writer = (TextView) v.findViewById(R.id.tv_writer);
            cv = (CardView) v.findViewById(R.id.cv);
        }
    }
}