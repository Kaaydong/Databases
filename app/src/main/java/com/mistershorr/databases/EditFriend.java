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

public class EditFriend extends AppCompatActivity {

    private TextView textViewClusminess, textViewGymFrequency,textViewTrust;
    private SeekBar seekBarClumsiness, seekBarGymFrequency; //
    private Switch switchAwesome; //
    private RatingBar ratingBarTrustworthiness; //
    private EditText editTextName, editTextMoneyOwed; //
    private Button buttonEditFriend;

    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frienddetail);

        friend = new Friend();
        wireWidgets();

        Intent lastIntent = getIntent();
        friend = lastIntent.getParcelableExtra(FriendListActivity.EXTRA_LIST);

        updateWidgets();

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

        buttonEditFriend = findViewById(R.id.button_detail_addFriend);
        buttonEditFriend.setText("Edit Friend");
    }

    public void updateWidgets()
    {
        seekBarClumsiness.setProgress(friend.getClumsiness() - 1);
        seekBarGymFrequency.setProgress((int)friend.getGymFrequency() - 1);
        Log.d("GymFrequency", friend.getGymFrequency()-1 + "");

        switchAwesome.setChecked(friend.isAwesome());
        ratingBarTrustworthiness.setProgress(friend.getTrustworthiness());
        editTextName.setText(friend.getName());
        editTextMoneyOwed.setText("" + friend.getMoneyOwed());
    }

    public void updateContact()
    {


        Backendless.Persistence.save( friend, new AsyncCallback<Friend>() {
            public void handleResponse( Friend savedContact )
            {
                savedContact.setName(editTextName.getText().toString());
                savedContact.setClumsiness(seekBarClumsiness.getProgress()+1);
                savedContact.setAwesome(switchAwesome.isChecked());
                savedContact.setGymFrequency(seekBarGymFrequency.getProgress()+1);
                savedContact.setTrustworthiness(ratingBarTrustworthiness.getProgress());
                savedContact.setMoneyOwed(Double.valueOf(editTextMoneyOwed.getText().toString()));

                Backendless.Persistence.save( savedContact, new AsyncCallback<Friend>() {
                    @Override
                    public void handleResponse( Friend response )
                    {
                        Toast.makeText(EditFriend.this,"Friend Successfully Edited",Toast.LENGTH_SHORT).show();

                        Intent loggedInIntent = new Intent(EditFriend.this,FriendListActivity.class);
                        startActivity(loggedInIntent);
                        finish();
                    }
                    @Override
                    public void handleFault( BackendlessFault fault )
                    {
                        Toast.makeText(EditFriend.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                } );
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(EditFriend.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setListeners()
    {
        buttonEditFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allThingsChecked()) {
                    updateContact();
                }
                else
                {
                    Toast.makeText(EditFriend.this,"Please fill out all fields",Toast.LENGTH_SHORT).show();
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
