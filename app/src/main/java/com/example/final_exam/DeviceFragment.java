package com.example.final_exam;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeviceFragment extends Fragment {


    // khai báo các biến
    Toolbar toolbar;
    private String deviceName;
    private int position;
    private RecyclerView recyclerView_device;
    private DeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DeviceItem> deviceItems = new ArrayList<>();
    FloatingActionButton fab_device;
    private DataStorage dataStorage;
    private long cid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_device2, container, false);

        fab_device = view.findViewById(R.id.fab_device);// ánh xạ biến
        toolbar = view.findViewById(R.id.toolbar);// ánh xạ biến

        Bundle bundle = getArguments(); //để truy cập Bundle

        if (bundle != null) {
            deviceName = bundle.getString("nameroom");
            //lấy dữ liệu được gửi đến với khóa "nameroom" từ Intent
            position = bundle.getInt("position", -1);
            //lấy giá trị được gửi đến với khóa "position" từ Intent
            cid = bundle.getLong("cid", -1);
            //lấy giá trị gửi đến với khóa "cid" từ Intent
        } else {
            // Xử lý khi bundle là null
            Log.e("DeviceFragment", "Bundle is null");
        }

        dataStorage = new DataStorage(requireContext());
        //quản lý lưu trữ dữ liệu trong ứng dụng

        setToolBar(view);

        loadData();

        buildListData();

        recyclerView_device = view.findViewById(R.id.recycleview_device);// ánh xạ biến
        recyclerView_device.setHasFixedSize(true);//kích thước không thay đổi trong quá trình chạy
        layoutManager = new LinearLayoutManager(requireContext()); // hiển thị các mục dưới dạng lưới với hai cột
        recyclerView_device.setLayoutManager(layoutManager);// cách thức hiển thị dữ liệu
        deviceAdapter = new DeviceAdapter(requireContext(), deviceItems);
        recyclerView_device.setAdapter(deviceAdapter);//để hiển thị dữ liệu

        fab_device.setOnClickListener(v -> showAddDeviceDialog()); // xử lý sự kiện khi nhấn floating button

        deviceAdapter.setOnItemClickListener(new DeviceAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                // Xử lý sự kiện khi item được click
            }

            @Override
            public void onLongClick(int position) {
                deleteDevice(position);
            }
            // Xử lý sự kiện khi nhấn longclick vào item
        });

        return view;
    }

    private void buildListData() { // tạo danh sách các thiết bị có sẵn
        deviceItems.add(new DeviceItem(1, "Thiết bị 1"));
        deviceItems.add( new  DeviceItem(2, "Thiết bị 2"));
        deviceItems.add(new DeviceItem(3, "Thiết bị 3"));
    }

    private void loadData() {
        Cursor cursor = dataStorage.getDeviceTable(cid);
        //truy vấn dữ liệu từ cơ sở dữ liệu
        Log.i("1234567890", "loadData" + cid);
        //in thông tin "loadData" và giá trị của cid
        deviceItems.clear();
        //xóa tất cả các phần tử trong danh sách
        while (cursor.moveToNext())//mỗi dòng của bảng DEVICE_TABLE_NAME được duyệt
        {
            int columnIndex = cursor.getColumnIndex(DataStorage.D_ID);
            int columnIndexkey = cursor.getColumnIndex(DataStorage.DEVICE_NAME_KEY);
            // lấy giá trị của các cột tương ứng trong bảng

            long did = cursor.getLong(columnIndex);
            String namedevice = cursor.getString(columnIndexkey);
            int alpha = cursor.getInt(columnIndexkey);
            //tạo với các thông tin  như id, tên phòng
            deviceItems.add(new DeviceItem(did, namedevice));
            // thêm phòng gồm id, tên phòng và hình ảnh
        }

        cursor.close(); //giải phóng tài nguyên
    }

    private void setToolBar(View view) {
        ImageButton back = toolbar.findViewById(R.id.btnBack);// tạo nút arrow back
        TextView title = toolbar.findViewById(R.id.title_toolbar);// ánh xạ biến
        title.setText(deviceName);// đặt tiêu đề\
        back.setOnClickListener(v -> requireActivity().onBackPressed()); // xử lý sự kiện khi nhấn nút back

    }

    private void showAddDeviceDialog() {// một đối tượng MyDialog mới
        MyDialog dialog = new MyDialog(); // tạo hộp thoại mới
        dialog.setFragmentManager(requireActivity().getSupportFragmentManager());
        dialog.show(requireActivity().getSupportFragmentManager(), MyDialog.DEVICE_ADD_DIALOG);
        // gọi trên đối tượng MyDialog để hiển thị hộp thoại
        dialog.setClickListener((namedevice) -> addDevice(namedevice));
        // nhấn nút "Add" trên hộp thoại
    }

    private void addDevice(String namedevice) { // thêm thiết bị
        long did = dataStorage.addDevice(cid, namedevice); // gán id thiết bị được cập nhật từ cơ sở dữ liệu
        DeviceItem deviceitem = new DeviceItem(did, namedevice); // tạo thiết bị mới gồm tên và id
        deviceItems.add(deviceitem);//đối tượng được thêm vào danh sách deviceitem để hiển thị trên giao diện
        deviceAdapter.notifyDataSetChanged();//thông báo để cập nhật giao diện
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    private void deleteDevice(int position) {// xóa thông tin thiết bị
        dataStorage.deleteDevice(deviceItems.get(position).getDid());// cập nhật trên cơ sở dữ liệu
        deviceItems.remove(position);// xóa thông tin thiết bị tại vị trí  được chọn
        deviceAdapter.notifyItemRemoved(position);//thông báo để cập nhật đối tượng được xóa
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
}