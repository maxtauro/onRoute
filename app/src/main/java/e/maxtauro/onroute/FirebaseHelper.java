package e.maxtauro.onroute;

import android.location.Location;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by maxtauro on 2018-04-07.
 */

public class FirebaseHelper {

    private User currentUser;

    public DatabaseReference onlineRef,currentUserRef,counterRef, currentUserOnlineRef;

    public DatabaseReference users, friendList;

    public FirebaseRecyclerAdapter<User,ListOnlineViewHolder> adapter;

    public FirebaseHelper(){

        users = FirebaseDatabase.getInstance().getReference("Users");
        friendList = getFriendList();

        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        //create new child name lastOnline
        counterRef = FirebaseDatabase.getInstance().getReference("lastOnline");
        //create new child in last online where key is user id
        currentUserOnlineRef = FirebaseDatabase.getInstance().getReference("lastOnline").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

    }

    public void updateCurrentUserRef(final Location mLastLocation){

        final String userName = getUserName(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        currentUser = new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                "Online",
                String.valueOf(mLastLocation.getLatitude()),
                String.valueOf(mLastLocation.getLongitude()));

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userName).exists()){

                }
                else{
                    users.child(userName).setValue(currentUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        currentUserRef = users.child(userName);
    }

    public void setupSystem(){
        onlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class)){

                    counterRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    "Online","0","0"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    Log.d("LOG",user.getUserEmail()+" is " + user.getUserStatus());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addFriend(final String userId){
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userId).exists()){
                    currentUserRef.child("friendList").setValue(userId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String getUserName(String email) {
        int index = email.indexOf("@");
        email = email.substring(0,index);
        email.replace(".","");
        return email;
    }

    public DatabaseReference getFriendList() {
        String userName = getUserName(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        return users.child(userName).child("friendList");
    }

    public void setAdapter(FirebaseRecyclerAdapter<User,ListOnlineViewHolder> adapter){
        this.adapter = adapter;
    }
}
