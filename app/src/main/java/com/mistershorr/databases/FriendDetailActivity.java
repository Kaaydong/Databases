package com.mistershorr.databases;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendDetailActivity extends AppCompatActivity {

    private TextView textViewClusminess, textViewGymFrequency,textViewTrust;
    private SeekBar seekBarClumsiness, seekBarGymFrequency; //
    private Switch switchAwesome; //
    private RatingBar ratingBarTrustworthiness; //
    private EditText editTextName, editTextMoneyOwed; //
    private Button buttonAddFriend;

    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frienddetail);

        wireWidgets();

        setListners();

    }

    public void wireWidgets()
    {
        textViewClusminess = findViewById(R.id.textView_detail_clusminessLabel);
        textViewGymFrequency = findViewById(R.id.textView_detail_gymFrequency);
        textViewTrust = findViewById(R.id.textView_detail_trust);

        seekBarClumsiness = findViewById(R.id.seekBar_detail_clumsiness);
        seekBarGymFrequency = findViewById(R.id.seekBar_detail_gymFrequency);

        switchAwesome = findViewById(R.id.switch_detail_awesome);
        ratingBarTrustworthiness = findViewById(R.id.ratingBar_detail_trust);
        editTextName = findViewById(R.id.editText_detail_name);
        editTextMoneyOwed = findViewById(R.id.editText_detail_moneyOwed);

        buttonAddFriend = findViewById(R.id.button_detail_addFriend);
    }

    public void makeFriend()
    {
        if (allThingsChecked())
        {
        friend.setName(editTextName.getText().toString());
        friend.setClumsiness(seekBarClumsiness.getProgress());
        friend.setAwesome(switchAwesome.isChecked());
        friend.setGymFrequency(seekBarGymFrequency.getProgress());
        friend.setTrustworthiness(ratingBarTrustworthiness.getNumStars());
        friend.setMoneyOwed(Double.valueOf(editTextMoneyOwed.getText().toString()));
    }
        else
    {
        Toast.makeText(FriendDetailActivity.this,"Please fill out all fields",Toast.LENGTH_SHORT).show();
    }

    }

    public void saveNewFriend()
    {
        makeFriend();

        // save object synchronously
        Friend savedFriend = Backendless.Persistence.save( friend );

        // save object asynchronously
        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
            public void handleResponse( Friend response )
            {
                Toast.makeText(FriendDetailActivity.this,"Friend Successfully Added",Toast.LENGTH_SHORT).show();
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(FriendDetailActivity.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListners()
    {
        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allThingsChecked()) {
                    saveNewFriend();

                    Intent loggedInIntent = new Intent(FriendDetailActivity.this,FriendListActivity.class);
                    startActivity(loggedInIntent);
                    finish();

                }
                else
                {
                    Toast.makeText(FriendDetailActivity.this,"Please fill out all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean allThingsChecked()
    {
        if (editTextName.getText().toString() == null ||
                editTextMoneyOwed.getText().toString() == null)
        {
            return false;
        }

        return true;
    }
}
