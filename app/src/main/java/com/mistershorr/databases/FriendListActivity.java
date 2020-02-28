package com.mistershorr.databases;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


import androidx.appcompat.app.AppCompatActivity;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.DataQueryBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FriendListActivity extends AppCompatActivity {

    private String jsonFileText;
    private ListView listView;
    private List<Friend> friendList;
    private FriendAdapter friendAdapter;
    private Friend[] friends;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_activity);

        wireWidgets();


        // search only for Friends that have ownerIds thatmatch the user's objectId
        String userId = Backendless.UserService.CurrentUser().getObjectId();
        // ownerId = '23821742184649'
        String whereClause = "ownerId = '" + userId + "'";

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of( Friend.class).find(queryBuilder,new AsyncCallback<List<Friend>>(){
            @Override
            public void handleResponse( List<Friend> foundFriends )
            {
                Log.d("LOADED FRIENDS","handleResponse" + foundFriends.toString());
                //Make a custom adapter to display the friends ad load the list that is
                // retrieved in that adapter

                // make a friend parcelable
                // when a friend is clicked, it opens the detail activity and loads the info

                friendList = foundFriends;

                friendAdapter = new FriendAdapter(friendList);
                listView.setAdapter(friendAdapter);

                setListeners();
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(FriendListActivity.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

//    @Override
//    protected void onStart()
//    {
//
//    }

    public void wireWidgets()
    {
        listView = findViewById(R.id.listView_listActivity_list);
        addButton = findViewById(R.id.floatingActionButton_listActivity_add);
    }

    public void setListeners()
    {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loggedInIntent = new Intent(FriendListActivity.this,FriendDetailActivity.class);
                startActivity(loggedInIntent);
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                Intent targetIntent = new Intent(.this, .class);
//
//
//                startActivity(targetIntent);
//                finish();
            }
        });
    }



//    ///////////////////////

    private class FriendAdapter extends ArrayAdapter<Friend> {
        // make an instance variable to keep track of the hero list
        private List<Friend> friendList;

        public FriendAdapter(List<Friend> friendList) {
            // since we're in the HeroListActivity class, we already have the context
            // we're hardcoding in a particular layout, so don't need to put it in
            // the constructor either
            // we'll send a placeholder resource to the superclass of -1
            super(FriendListActivity.this, -1, friendList);
            this.friendList = friendList;
        }

        // The goal of the adapter is to link the your list to the listview
        // and tell the listview where each aspect of the list item goes.
        // so we override a method called getView


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 1. inflate a layout
            LayoutInflater inflater = getLayoutInflater();

            // check if convertview is null, if so, replace it
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.friends_layout, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textView_friendLayout_name);
            TextView textViewClumsiness = convertView.findViewById(R.id.textView_friendLayout_clumsiness);
            TextView textViewmoneyOwed = convertView.findViewById(R.id.textView_friendLayout_moneyOwed);


            textViewName.setText(friendList.get(position).getName());
            textViewClumsiness.setText(friendList.get(position).getClumsiness()+"");
            textViewmoneyOwed.setText(friendList.get(position).getMoneyOwed()+"");


            return convertView;
        }
    }
}
