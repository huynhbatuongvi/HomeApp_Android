package com.example.final_exam;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // khai báo biến
    
    private DrawerLayout drawerLayout;
   private ViewPager2 viewPager2;

   private FrameLayout frameLayout;
    private Adapter adapter;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SETTING = 1;

    private int currentfrag = FRAGMENT_HOME;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // khởi tạo sự kiện
        super.onCreate(savedInstanceState); // khởi tạo sự kiện cua Activity
        setContentView(R.layout.activity_main);

        // ánh xạ biến
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager2 = findViewById(R.id.viewpager2);
        frameLayout = findViewById(R.id.containers);
        NavigationView navigationView = findViewById(R.id.nav_view);


        adapter = new Adapter(this); // tạo đối tượng adapter
        viewPager2.setAdapter(adapter);//thiết lập adapter cho ViewPager2
        setSupportActionBar(toolbar); // tùy chỉnh và quản lý ActionBar

        navigationView.setNavigationItemSelectedListener(this); // xử lý sự kiện khi mục menu trong NavigationView được chọn

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav); //kết nối ActionBar với DrawerLayout và quản lý sự kiện khi mở hoặc đóng Navigation Drawer

        drawerLayout.addDrawerListener(toggle);// theo dõi sự kiện mở và đóng Navigation Drawer
        toggle.syncState();//đồng bộ trạng thái ActionBarDrawerToggle

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {//sự kiện thay đổi trang trong ViewPager2
            @Override
            public void onPageSelected(int position) {//khi một trang mới được chọn
                super.onPageSelected(position);
                switch (position) //kiểm tra giá trị của position
                {
                    case 0: // xét từng trường hợp dựa vào số thứ tự
                        currentfrag = FRAGMENT_HOME;// gán giá trị cho biến currentfrag
                        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        //đánh dấu một mục có ID tương ứng trong thanh điều hướng
                        break;
                    // ngắt lệnh

                    case 1: // xét từng trường hợp dựa vào số thứ tự
                        currentfrag = FRAGMENT_SETTING;
                        navigationView.getMenu().findItem(R.id.nav_setting).setChecked(true);//đánh dấu mục menu có ID tương ứng
                        break;// ngắt lệnh

                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { // khi một mục menu được chọn
        switch (item.getItemId()) //  // xác định ID của mỗi fragment của menu
        {
            case R.id.nav_home: // xét từng trường hợp dựa vào ID của các mục menu
                openHomeFragment(); //  thực hiện hành động mở fragment tương ứng với hàm
                break; // ngắt lệnh

            case R.id.nav_setting: // xét từng trường hợp dựa vào ID của các mục menu
                openSettingFragmnet();//  thực hiện hành động mở fragment tương ứng với hàm
                break;// ngắt lệnh


            case R.id.nav_logout:// xét từng trường hợp dựa vào ID của các mục menu
                logout();//thực hiện hành động thoát ứng dụng
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START); // đóng drawer khi đã chọn mục menu
        return true; // sự kiện xử lý thành công
    }

    boolean backPressedOnce = false; // khai báo giá trị
    private void logout() {
        if (backPressedOnce){  // nếu đã nhấn 2 lần
            super.onBackPressed();// thực hiện thoát ứng dụng
        } else {
            backPressedOnce = true;// nếu đã nhấn 1 lần
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressedOnce = false;
                }//trả giá trị false nếu cách nhau quá 2s
            }, 2000);
        }
    }

    private void openHomeFragment() { // khởi tạo hàm
        if (currentfrag != FRAGMENT_HOME) {
                currentfrag = FRAGMENT_HOME;// gán giá trị cho biến currentfrag
                viewPager2.setCurrentItem(FRAGMENT_HOME);// thiết lập trang hiện tại của ViewPager2 thành trang có vị trí được xét

        }
    }

    private void openSettingFragmnet() {// khởi tạo hàm
        if (currentfrag != FRAGMENT_SETTING)//kiểm tra xem trạng thái hiện tại của currentfrag
        {
                currentfrag = FRAGMENT_SETTING; // gán giá trị cho biến currentfrag
                viewPager2.setCurrentItem(FRAGMENT_SETTING);// thiết lập trang hiện tại của ViewPager2 thành trang có vị trí được xét
            }
        }


    @Override
    public void onBackPressed() { // khi nhấn nút back
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) // nếu drawer đang mở
        {
            drawerLayout.closeDrawer(GravityCompat.START); // đóng drawer
        } else {
            super.onBackPressed(); // mặc định nhấn nút back vẫn được thực hiện
        }
    }

}