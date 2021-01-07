package com.example.mars.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mars.R;
import com.example.mars.entities.Post;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.mars.utils.Constants.POST;

public class AddPostActivity extends AppCompatActivity {
    EditText title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        initAppBar();
        initElements();
        initAddBtn();
    }

    private void initAppBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.add_post_header);
    }

    private void initElements() {
        title = findViewById(R.id.txtTitle);
        desc = findViewById(R.id.txtDesc);
    }

    private void initAddBtn() {
        Button addPost = findViewById(R.id.addPostBtn);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.getText().toString().length() == 0) {
                    Toast.makeText(AddPostActivity.this, R.string.add_post_title_err, Toast.LENGTH_SHORT).show();
                } else if(desc.getText().toString().length() == 0) {
                    Toast.makeText(AddPostActivity.this, R.string.add_post_desc_err, Toast.LENGTH_SHORT).show();
                }
                else {
                    Post post = new Post(title.getText().toString().trim(), desc.getText().toString().trim());
                    Intent intent = new Intent();
                    intent.putExtra(POST, post);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}