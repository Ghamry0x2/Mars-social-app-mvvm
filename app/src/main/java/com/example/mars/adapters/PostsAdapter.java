package com.example.mars.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mars.R;
import com.example.mars.entities.Post;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> posts;
    private LayoutInflater mInflater;
    private Context context;

    public PostsAdapter(Context context, List<Post> data) {
        this.mInflater = LayoutInflater.from(context);
        this.posts = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.authorName.setText(post.authorName);
        holder.title.setText(post.title);
        holder.desc.setText(post.desc);
        holder.createdAt.setText(post.formattedCreationDate());

        Glide.with(context)
                .load(post.authorAvatar)
                .centerCrop()
                .circleCrop()
                .into(holder.authorAvatar);

        holder.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MarsTag", "onClick: ");
                PopupMenu popupMenu = new PopupMenu(context, holder.menuBtn);
                popupMenu.inflate(R.menu.post_options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_item_read:
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return posts.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        ImageView authorAvatar;
        TextView title;
        TextView desc;
        TextView createdAt;
        TextView menuBtn;

        ViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.authorName);
            authorAvatar = itemView.findViewById(R.id.authorAvatar);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            createdAt = itemView.findViewById(R.id.createdAt);
            menuBtn = itemView.findViewById(R.id.menuBtn);
        }
    }
}