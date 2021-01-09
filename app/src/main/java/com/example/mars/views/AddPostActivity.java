package com.example.mars.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mars.R;
import com.example.mars.entities.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.mars.utils.Constants.POST;

public class AddPostActivity extends AppCompatActivity {
    EditText txtTitle, txtDesc;
    ImageButton ttsBtn;
    View.OnClickListener btnSpeakListner;

    Intent intent;
    TextToSpeech tts;

    final int VOICE_RECOGNITION = 2;
    String mostRecentUtteranceID;

    String TTS = "";
    int count = 0;

    List<String> speech = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        initAppBar();
        setSpeakListener();
        initSpeech();
        initElements();
        initAddBtn();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void speakText() {
        if(count < speech.size()) {
            TTS = speech.get(count);

            count++;
            mostRecentUtteranceID = Integer.toString(count) + " ID";
            tts.speak(TTS, TextToSpeech.QUEUE_ADD, null, mostRecentUtteranceID);

            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {}

                @Override
                public void onDone(String utteranceId) {
                    if(count < speech.size()) {
                        startActivityForResult(intent, VOICE_RECOGNITION);
                    }
                }

                @Override
                public void onError(String utteranceId) {}
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == VOICE_RECOGNITION) {
                if (resultCode == RESULT_OK) {
                    List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    switch (count) {
                        case 1:
                            String title = results.get(0).trim();
                            if(title != null) {
                                txtTitle.setText(title);
                            }
                            break;
                        case 2:
                            String desc = results.get(0).trim();
                            if(desc != null) {
                                txtDesc.setText(desc);
                            }
                            break;
                    }

                    speakText();
                }
            }

        } catch(Exception e) {
            resetTexts();
        }
    }

    private void setSpeakListener() {
        btnSpeakListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTexts();

                // Speech Recognition
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speak));
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 4);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

                // Text to Speech
                tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onInit(int status) {

                        if (status == TextToSpeech.SUCCESS) {
                            tts.setLanguage(Locale.ENGLISH);
                            tts.setPitch((float) 1);
                            tts.setSpeechRate((float) 1);

                            speakText();
                        }
                    }
                });
            }
        };
    }

    private void initAppBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.add_post_header);
    }

    private void initElements() {
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        ttsBtn = findViewById(R.id.ttsBtn);
        ttsBtn.setOnClickListener(btnSpeakListner);
    }

    private void initAddBtn() {
        Button addPost = findViewById(R.id.addPostBtn);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtTitle.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddPostActivity.this, R.string.add_post_title_err, Toast.LENGTH_SHORT).show();
                } else if(txtDesc.getText().toString().trim().length() == 0) {
                    Toast.makeText(AddPostActivity.this, R.string.add_post_desc_err, Toast.LENGTH_SHORT).show();
                }
                else {
                    Post post = new Post(txtTitle.getText().toString().trim(), txtDesc.getText().toString().trim());
                    Intent intent = new Intent();
                    intent.putExtra(POST, post);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initSpeech() {
        speech.add(getString(R.string.speak_title));
        speech.add(getString(R.string.speak_desc));
        speech.add(getString(R.string.speak_finish));
    }

    private void resetTexts() {
        count = 0;

        txtTitle.setText("");
        txtDesc.setText("");
    }
}