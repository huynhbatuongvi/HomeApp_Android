package com.example.final_exam;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// giải thich

public class Adapter extends FragmentStateAdapter {
    public Adapter(@NonNull MainActivity fragment) {
        super(fragment);
    }
    //constructor của lớp Adapter

    @NonNull
    @Override
    public Fragment createFragment(int position) { // tạo ra fragment tương ứng với vị trí trong ViewPager2
        switch (position)  // xác định vị trí của mỗi fragment
        {
            case 0: // xét trường hợp
                return new HomeFragment(); // trả về giá trị tương ứng vị trí của fragment

            default:
                return new SettingFragment();// trả về giá trị tương ứng vị trí của fragment

        }
    }
    @Override
    public int getItemCount() {
        return 3;
    } //trả về tổng số fragment
}
