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

        friend = new Friend(); // get the friend from the extras. if the extra is null, then make the new friend
        // then set the all the widget values to those of the friend you just pulled up

        wireWidgets();

        setListeners();

    }

    public void wireWidgets()
    {
        textViewClusminess = findViewById(R.id.textView_detail_clusminessLabel);
        textViewGymFrequency = findViewById(R.id.textView_detail_gymFrequency);
        textViewTrust = findViewById(R.id.textView_detail_trust);

        seekBarClumsiness = findViewById(R.id.seekBar_detail_clumsiness);
        seekBarGymFrequency = findViewById(R.id.seekBar_detail_gymFrequency);
        seekBarClumsiness.setMax(9);
        seekBarGymFrequency.setMax(9);

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
        friend.setClumsiness(seekBarClumsiness.getProgress()+1);
        friend.setAwesome(switchAwesome.isChecked());
        friend.setGymFrequency(seekBarGymFrequency.getProgress()+1);
        friend.setTrustworthiness(ratingBarTrustworthiness.getProgress());
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


        // save object asynchronously
        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
            public void handleResponse( Friend response )
                {
                    Toast.makeText(FriendDetailActivity.this,"Friend Successfully Added",Toast.LENGTH_SHORT).show();

                    Intent loggedInIntent = new Intent(FriendDetailActivity.this,FriendListActivity.class);
                    startActivity(loggedInIntent);
                    finish();
                }

                public void handleFault( BackendlessFault fault )
                {
                    Toast.makeText(FriendDetailActivity.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListeners()
    {
        buttonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allThingsChecked()) {
                    saveNewFriend();


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
