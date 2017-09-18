package com.example.artem.projectlost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;

    private GoogleApiClient googleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    protected FirebaseUser mFirebaseUser;
    private View v;
    private DatabaseReference mRef;
    FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

    private RecyclerView recyclerView;

    private TextView mEmail, mFullName, mNameText;
    private ImageView mImageView, mainImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();

        adapterView();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        View navHeaderView = mNavigationView.getHeaderView(0);

        mEmail = (TextView) navHeaderView.findViewById(R.id.email);
        mFullName = (TextView) navHeaderView.findViewById(R.id.fullName);
        mNameText = (TextView) navHeaderView.findViewById(R.id.nameText);
        mImageView = (ImageView) navHeaderView.findViewById(R.id.imageView);
        mainImage = (ImageView) navHeaderView.findViewById(R.id.mainImage);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.navLogOut) {
                    logOut(v);
                }

                return false;
            }

        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getAccountDetails();
                } else {
                    goLogInScreen();
                }
            }
        };
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void getAccountDetails(){
        FirebaseDatabase.getInstance().getReference("users").child(mFirebaseUser.getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users users = dataSnapshot.getValue(Users.class);
                        Glide.with(MainActivity.this).load(mFirebaseUser.getPhotoUrl()).into(mImageView);
                        mFullName.setText(users.getUser());
                        mEmail.setText(users.getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        mFirebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "not close session", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    public void transitionInfo(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    private void adapterView(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        FirebaseRecyclerAdapter<String, ViewHolder> adapter;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FirebaseRecyclerAdapter<String, ViewHolder>(
                String.class,
                R.layout.card_item,
                ViewHolder.class,
                mRef.child("users").child(user.getEmail().replace(".", ",")).child("message")
        ) {
            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, String item, final int position) {
                viewHolder.describeText.setText(item);
                Glide.with(MainActivity.this).load(user.getPhotoUrl()).into(viewHolder.userImage);

            }
        };

        recyclerView.setAdapter(adapter);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView describeText;
        public TextView optionText;
        public ImageView userImage;
        public ImageView mainImage;
        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.nameText);
            describeText = (TextView) itemView.findViewById(R.id.describeText);
            optionText = (TextView) itemView.findViewById(R.id.optionText);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            mainImage = (ImageView) itemView.findViewById(R.id.mainImage);
        }
    }
}