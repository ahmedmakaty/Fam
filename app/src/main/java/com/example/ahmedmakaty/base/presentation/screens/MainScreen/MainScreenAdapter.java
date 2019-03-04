package com.example.ahmedmakaty.base.presentation.screens.MainScreen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ahmedmakaty.base.R;
import com.example.ahmedmakaty.base.data.model.Article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL_TYPE = 1020;
    private static final int PROGRESS_BAR_TYPE = 1030;

    ArticleClickListener articleClickListener;

    ArrayList<Article> articles = new ArrayList();

    public MainScreenAdapter(ArticleClickListener articleClickListener) {
        this.articleClickListener = articleClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL_TYPE:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
                return new ArticleViewHolder(view);
            case PROGRESS_BAR_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                return new ProgressViewHolder(view);
            default:
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
                return new ArticleViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case NORMAL_TYPE:
                Article article = articles.get(position);
                ArticleViewHolder mViewHolder = (ArticleViewHolder) holder;

                Glide.with(mViewHolder.itemView.getContext())
                        .load(article.getUrlToImage())
                        .into(mViewHolder.header);

                mViewHolder.title.setText(article.getTitle());
                mViewHolder.source.setText(article.getSource().getName());
                mViewHolder.desc.setText(article.getDescription());
                mViewHolder.author.setText(article.getAuthor());


                mViewHolder.date.setText(formatPrettyDate(article.getPublishedAt(), "yyyy-MM-dd'T'HH:mm:ss", "MMM d HH:mm"));

                mViewHolder.itemView.setOnClickListener((View v) -> {
                    articleClickListener.onArticleClicked(article);
                });

                break;
            case PROGRESS_BAR_TYPE:
                ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
                progressViewHolder.progressBar.setIndeterminate(true);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (articles.get(position) != null) {
            return NORMAL_TYPE;
        } else {
            return PROGRESS_BAR_TYPE;
        }
    }


    public void setData(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    private String formatPrettyDate(String publishedAt, String fromDormat, String toFormat) {
        String time = publishedAt;
        SimpleDateFormat spf = new SimpleDateFormat(fromDormat);
        Date newDate = null;
        try {
            newDate = spf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat(toFormat);
        time = spf.format(newDate);
        return time;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.header)
        ImageView header;
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.title)
        TextView title;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        ProgressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface ArticleClickListener {
        void onArticleClicked(Article article);
    }
}
