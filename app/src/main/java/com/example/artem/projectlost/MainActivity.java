package com.example.artem.projectlost;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.navItemEl) {
                    FragmentTransaction tfragmentTransaction = mFragmentManager.beginTransaction();
                    tfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.navItemDoc) {
                    FragmentTransaction tfragmentTransaction = mFragmentManager.beginTransaction();
                    tfragmentTransaction.replace(R.id.containerView,new DocumentationFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.navItemAnim) {
                    FragmentTransaction tfragmentTransaction = mFragmentManager.beginTransaction();
                    tfragmentTransaction.replace(R.id.containerView,new AnimalsFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.navItemOther) {
                    FragmentTransaction tfragmentTransaction = mFragmentManager.beginTransaction();
                    tfragmentTransaction.replace(R.id.containerView,new OthersFragment()).commit();
                }


                return false;
            }

        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }

}