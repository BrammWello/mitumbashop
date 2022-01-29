package com.devbramm.mitumba;

import android.content.Intent;
import android.os.Bundle;

import com.devbramm.mitumba.CommonTasksUtil.CommonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;

import com.devbramm.mitumba.Adapters.TopDealsAdapter;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //vars
    private ArrayList<String> mDealNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    TextView customerNames, customerEmail, dressesCardName;
    CardView dressesCard, lingerieCard, heelsCard, socksCard, casualShoesCard, babyShopCard;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.cart_home_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent toCartFromHome = new Intent(HomePageActivity.this, CartActivity.class);
                 startActivity(toCartFromHome);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        dressesCard = findViewById(R.id.dresses_card);
        lingerieCard = findViewById(R.id.lingerie_card);
        heelsCard = findViewById(R.id.heels_card);
        socksCard = findViewById(R.id.socks_card);
        casualShoesCard = findViewById(R.id.casual_shoes_card);
        babyShopCard = findViewById(R.id.baby_shop_card);
        dressesCardName = findViewById(R.id.dresses_name);

        //header view
        View headerView = navigationView.getHeaderView(0);
        customerNames = headerView.findViewById(R.id.customer_names);
        customerEmail = headerView.findViewById(R.id.customer_email);

        //set the names and details of customer in app drawer
        customerNames.setText(CommonUtils.currentUserNames);
        customerEmail.setText(CommonUtils.currentUsermEmail);

        dressesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","dresses");
                startActivity(toDressesGrid);
            }
        });
        lingerieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","lingerie");
                startActivity(toDressesGrid);
            }
        });
        heelsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","heels");
                startActivity(toDressesGrid);
            }
        });
        socksCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","socks");
                startActivity(toDressesGrid);
            }
        });
        casualShoesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","casual_shoes");
                startActivity(toDressesGrid);
            }
        });
        babyShopCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDressesGrid = new Intent(HomePageActivity.this, ProductGridActivity.class);
                toDressesGrid.putExtra("categoryName","baby_shop");
                startActivity(toDressesGrid);
            }
        });

        getImages();




    }

    private void getImages(){

        mImageUrls.add(R.drawable.bra);
        mDealNames.add("Tight Fit Bra");

        mImageUrls.add(R.drawable.dress);
        mDealNames.add("Full Body Dress");

        mImageUrls.add(R.drawable.high_heels);
        mDealNames.add("8-Inch High Heels");

        mImageUrls.add(R.drawable.onesie);
        mDealNames.add("Baby Shawl");

        mImageUrls.add(R.drawable.shoe);
        mDealNames.add("Classic Shoe");

        initRecyclerView();

    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.best_deals_recycler);
        recyclerView.setLayoutManager(layoutManager);
        TopDealsAdapter adapter = new TopDealsAdapter(this, mDealNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_your_orders) {

            Intent toOrdersFromHome = new Intent(HomePageActivity.this, OrdersActivity.class);
            startActivity(toOrdersFromHome);

        } else if (id == R.id.nav_address_book) {

            Intent toSettingsFromHome = new Intent(HomePageActivity.this, AddressBookActivity.class);
            startActivity(toSettingsFromHome);

        } else if (id == R.id.nav_live_chat) {

            Intent toChatFromHome = new Intent(HomePageActivity.this, ChatActivity.class);
            startActivity(toChatFromHome);

        } else if (id == R.id.nav_settings) {

            Intent toSettingsFromHome = new Intent(HomePageActivity.this, SettingsActivity.class);
            startActivity(toSettingsFromHome);
        } else if (id == R.id.nav_sign_out) {

            mAuth.signOut();
            finish();

            Intent toSignUp = new Intent(HomePageActivity.this, SignUpActivity.class);
            startActivity(toSignUp);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
