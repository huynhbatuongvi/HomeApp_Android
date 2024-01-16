package com.example.final_exam;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //khai báo biến
    FloatingActionButton fab_room;
    RecyclerView recyclerView_Room;
    RoomAdapter roomAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<RoomItems> roomItem = new ArrayList<>();
    Toolbar toolbar;
    DataStorage dataStorage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // ánh xạ biến
        fab_room =  view.findViewById(R.id.fab_main);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView_Room = view.findViewById(R.id.recycleview_room);

        recyclerView_Room.setHasFixedSize(true); //kích thước không thay đổi trong quá trình chạy
        layoutManager = new GridLayoutManager(requireContext(), 2); // hiển thị các mục dưới dạng lưới với hai cột

        fab_room.setOnClickListener( v -> showDialog()); // sự kiện khi bấm vào nút FloatingActionButton

        dataStorage = new DataStorage(requireContext()); //khởi tạo lớp DataStorage
        
        loadData();

       roomAdapter = new RoomAdapter(requireContext(), roomItem); // cung cấp dữ liệu cho RecyclerView
        recyclerView_Room.setAdapter(roomAdapter); //để hiển thị dữ liệu
        recyclerView_Room.setLayoutManager(layoutManager); // cách thức hiển thị dữ liệu

        //xử lý sự kiện khi một mục được chọn
        roomAdapter.setOnItemClickListener(new RoomAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                gotoItemActivity(position);
                // Xử lý sự kiện khi item được click
            }

            @Override
            public void onLongClick(int position) {
                deleteRoom(position);
            }
            // Xử lý sự kiện xóa thiết bị khi nhấn longclick
        });

        setToolBar(view);

        return view;

    }


    private void loadData() { // tải dữ liệu từ cơ sở dữ liệu và cập nhật danh sách roomItem
        Cursor cursor = dataStorage.getRoomTable(); //lấy dữ liệu từ bảng ROOM_TABLE_NAME

        roomItem.clear(); // xóa tất cả các mục có sẵn trong danh sách roomItem trước khi tải dữ liệu mới vào
        while (cursor.moveToNext()) //mỗi dòng của bảng ROOM_TABLE_NAME được duyệt
        {
            int columnIndex = cursor.getColumnIndex(DataStorage.C_ID);
            int columnIndexkey = cursor.getColumnIndex(DataStorage.ROOM_NAME_KEY);
            // lấy giá trị của các cột tương ứng trong bảng

            int id = cursor.getInt(columnIndex);
            String nameroom = cursor.getString(columnIndexkey);
            //tạo với các thông tin  như id, tên phòng

            roomItem.add(new RoomItems(id, nameroom, R.drawable.room));
            // thêm phòng gồm id, tên phòng và hình ảnh

        }
    }

    private void setToolBar(View view) {
        ImageButton back = toolbar.findViewById(R.id.btnBack); // tạo nút arrow back
        TextView title = toolbar.findViewById(R.id.title_toolbar); // ánh xạ biến

        title.setText("Main Room");// đặt tiêu đề
        back.setVisibility(View.INVISIBLE); // ẩn nút arow back

    }

    private void gotoItemActivity(int position) { // intent
        DeviceFragment deviceFragment = new DeviceFragment();//tạo Fragment mới
        Bundle bundle = new Bundle();//tạo truy cập Bundle mới
        bundle.putString("nameroom", roomItem.get(position).getRoomName());// truyền tên phòng
        bundle.putInt("roomImage",roomItem.get(position).getRoomImage());//truyền hình ảnh phòng
        bundle.putInt("position",position);// truyền vị trí của phòng
        bundle.putLong("cid",roomItem.get(position).getCid());// truyền ID của phòng
        deviceFragment.setArguments(bundle);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//thực hiện các thay đổi trên Fragment
            fragmentTransaction.replace(R.id.homefragment, deviceFragment, "DeviceFragment");// thay thế Fragment mới có giá trị ID tương ứng
            fragmentTransaction.addToBackStack(null);//sắp xếp thứ tự Fragment
            fragmentTransaction.commit();// hoàn thành giao dịch
    }



    private void showDialog(){ // một đối tượng MyDialog mới
        MyDialog dialog = new MyDialog(); // mở hộp thoại mới
        dialog.setFragmentManager(requireActivity().getSupportFragmentManager());
        dialog.show(requireActivity().getSupportFragmentManager(), MyDialog.ROOM_ADD_DIALOG); // gọi trên đối tượng MyDialog để hiển thị hộp thoại
        dialog.setClickListener((nameroom)-> addClass(nameroom));// nhấn nút "Add" trên hộp thoại
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    private void addClass(String nameroom) { // thêm một phòng mới

        long cid = dataStorage.addRoom(nameroom);//thêm một phòng mới vào cơ sở dữ liệu
        RoomItems roomitem = new RoomItems(cid,nameroom, R.drawable.room);
        roomItem.add(roomitem);//đối tượng được thêm vào danh sách roomItem để hiển thị trên giao diện
        roomAdapter.notifyDataSetChanged();//thông báo để cập nhật giao diện

    }


    private void deleteRoom(int position) { // xóa thông tin phòng
        dataStorage.deleteRoom(roomItem.get(position).getCid()); // cập nhật trên cơ sở dữ liệu
        roomItem.remove(position); // xóa thông tin phòng tại vị trí  được chọn
        roomAdapter.notifyItemRemoved(position);//thông báo để cập nhật đối tượng được xóa

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.nav_menu, menu);
        // lấy MenuInflater có id nav_menu của Activity chứa Fragment
        super.onCreateOptionsMenu(menu, inflater);
        //gọi onCreateOptionsMenu của lớp Fragment
    }

}
