package no.bekk.lollipop.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import no.bekk.lollipop.R;
import no.bekk.lollipop.list.DrawerAdapter;

public class MainActivity extends ActionBarActivity {
    private List<String> listItems = new ArrayList<String>();
    private List<Integer> icons = new ArrayList<Integer>();
    private String name = "Christoffer Marcussen";
    private String email = "christoffer.marcussen@bekk.no";
    private int profilePic = R.drawable.profile_picture;
    private Toolbar toolbar;
    private RecyclerView drawerView;
    private RecyclerView.Adapter drawerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private FloatingActionButton floatingActionButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        listItems.add("Search");
        icons.add(R.mipmap.ic_action_music_1);

        setupToolbar();
        setupRecyclerView();
        setupDrawer();
        setupFAB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        drawerView = (RecyclerView) findViewById(R.id.drawer_view);
        drawerView.setHasFixedSize(true);
        drawerAdapter = new DrawerAdapter(listItems, icons, name, email, profilePic);
        drawerView.setAdapter(drawerAdapter);
        layoutManager = new LinearLayoutManager(this);
        drawerView.setLayoutManager(layoutManager);
    }

    private void setupDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void setupFAB() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_search);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
                }
                else {
                    context.startActivity(intent);
                }
            }
        });
    }
}