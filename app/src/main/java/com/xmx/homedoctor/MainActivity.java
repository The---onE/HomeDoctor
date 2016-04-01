package com.xmx.homedoctor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVObject;
import com.xmx.homedoctor.Fragments.ContactsFragment;
import com.xmx.homedoctor.Fragments.MeFragment;
import com.xmx.homedoctor.Fragments.PatientsFragment;
import com.xmx.homedoctor.Fragments.StatisticsFragment;
import com.xmx.homedoctor.Tools.ActivityBase.BaseNavigationActivity;
import com.xmx.homedoctor.Tools.PagerAdapter;
import com.xmx.homedoctor.User.Callback.AutoLoginCallback;
import com.xmx.homedoctor.User.LoginActivity;
import com.xmx.homedoctor.User.UserManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseNavigationActivity {
    private long mExitTime = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        UserManager.getInstance().autoLogin(new AutoLoginCallback() {
            @Override
            public void success(AVObject user) {
            }

            @Override
            public void notLoggedIn() {
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void errorNetwork() {
                showToast(R.string.network_error);
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void errorUsername() {
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void errorChecksum() {
                startActivity(LoginActivity.class);
                finish();
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PatientsFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new StatisticsFragment());
        fragments.add(new MeFragment());

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.patients));
        titles.add(getString(R.string.contacts));
        titles.add(getString(R.string.statistics));
        titles.add(getString(R.string.me));

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments, titles);

        ViewPager vp = (ViewPager) findViewById(R.id.pager_main);
        vp.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - mExitTime) > Constants.LONGEST_EXIT_TIME) {
                showToast(R.string.confirm_exit);
                mExitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
