package com.example.final_exam;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class MyDialog extends DialogFragment {

    //khai báo các biến
    public static  final String ROOM_ADD_DIALOG = "addRoom";
    public static  final String ROOM_UPDATE_DIALOG = "updateRoom";
    public static  final String DEVICE_ADD_DIALOG = "addDevice";
    public static  final String DEVICE_UPDATE_DIALOG = "updateDevice";

    private FragmentManager fragmentManager;

    private OnClickListener clickListener;
    private String namedevice;



    public MyDialog(String namedevice) {// tạo hàm constructor

        this.namedevice = namedevice;
    }

    // tạo lớp getter và setter
    public MyDialog() {// tạo hàm constructor
        this.fragmentManager = null;
    }

    // tạo hàm constructor
    public void setFragmentManager(FragmentManager manager){
        this.fragmentManager = manager;
    }

    public interface OnClickListener{ // sự kiện nút nhấn
        void onClick(String textRoom);//xử lý sự kiện khi một mục trong Adapter được bấm
    }

    public void setClickListener(OnClickListener clickListener) {
        this.clickListener = clickListener;// tạo hàm constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = null; //khởi tạo một đối tượng Dialog và gán giá trị null
        if (getTag().equals(ROOM_ADD_DIALOG))dialog = getAddRoomDialog();
        if (getTag().equals(DEVICE_ADD_DIALOG))dialog = getAddDeviceDialog();
        if (getTag().equals(ROOM_UPDATE_DIALOG))dialog = getUpdateRoomDialog();
        if (getTag().equals(DEVICE_UPDATE_DIALOG))dialog = getUpdateDeviceDialog();
        //kiểm tra xem tag của dialog có bằng biến được gán

        if (dialog != null) { // dialog khác null
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); }
        //hiết lập một hình nền trong suốt cho cửa sổ dialog
        return dialog;
    }

    private Dialog getUpdateDeviceDialog() {// hộp thoại cập nhật thông tin thiết bị
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo một đối tượng AlertDialog.Builder
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog, null);
        //sử dụng LayoutInflater để kết hợp layout
        builder.setView(view);
        //tạo view vào AlertDialog.Builder


        TextView title = view.findViewById(R.id.txtTitle);// ánh xạ biến
        title.setText("Update Device");//đặt tiêu đề cho hộp thoại

        EditText name_edit = view.findViewById(R.id.editName);// ánh xạ biến

        name_edit.setHint("Enter Name Device");//thông báo gợi ý trong EditText
        Button cancel = view.findViewById(R.id.btnCancel);// ánh xạ biến
        Button add = view.findViewById(R.id.btnAdd);// ánh xạ biến
        add.setText("update");//đặt văn bản cho nút bấm
        
        name_edit.setText(namedevice);//đặt văn bản vào EditText bằng namedevice


        cancel.setOnClickListener(v -> dismiss()); //xử lý sự kiện khi nút cancel được bấm
        add.setOnClickListener(v -> {//xử lý sự kiện khi nút add được bấm
            String name = name_edit.getText().toString();
            //gán văn bản được nhập trong trường name_edit
            clickListener.onClick(name);
            //gọi phương thức onClick
            dismiss();// đóng hộp thoại
        });


        return  builder.create();//tạo và trả đối tượng về AlertDialog

    }

    private Dialog getUpdateRoomDialog() {// hộp thoại cập nhật thông tin phòng
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo một đối tượng AlertDialog.Builder
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog, null);
        //sử dụng LayoutInflater để kết hợp layout
        builder.setView(view);//tạo view vào AlertDialog.Builder

        TextView title = view.findViewById(R.id.txtTitle);// ánh xạ biến
        title.setText("Update Room");//đặt tiêu đề cho hộp thoại


        EditText roomedit = view.findViewById(R.id.editName);// ánh xạ biến

        roomedit.setHint("Enter Name Room");//thông báo gợi ý trong EditText
        Button cancel = view.findViewById(R.id.btnCancel);// ánh xạ biến
        Button add = view.findViewById(R.id.btnAdd);// ánh xạ biến

        add.setText("Update");//đặt văn bản cho nút bấm

        cancel.setOnClickListener(v -> dismiss());//xử lý sự kiện khi nút cancel được bấm
        add.setOnClickListener(v -> {//xử lý sự kiện khi nút add được bấm
            String RoomName = roomedit.getText().toString();
            //gán văn bản được nhập trong trường name_edit
            clickListener.onClick(RoomName);
            //gọi phương thức onClick
            dismiss();// đóng hộp thoại
        });


        return  builder.create();//tạo và trả đối tượng về AlertDialog
    }

    private Dialog getAddDeviceDialog() {// hộp thoại thêm thiết bị

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo một đối tượng AlertDialog.Builder
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog, null);
        //sử dụng LayoutInflater để kết hợp layout
        builder.setView(view);//tạo view vào AlertDialog.Builder

        TextView title = view.findViewById(R.id.txtTitle);// ánh xạ biến
        title.setText("Add New Device");//đặt tiêu đề cho hộp thoại

        EditText name_edit = view.findViewById(R.id.editName);// ánh xạ biến

        name_edit.setHint("Enter Name Device");//thông báo gợi ý trong EditText
        Button cancel = view.findViewById(R.id.btnCancel);// ánh xạ biến
        Button add = view.findViewById(R.id.btnAdd);// ánh xạ biến

        cancel.setOnClickListener(v -> dismiss());//xử lý sự kiện khi nút cancel được bấm
        add.setOnClickListener(v -> {//xử lý sự kiện khi nút add được bấm
            String name = name_edit.getText().toString();
            //gán văn bản được nhập trong trường name_edit
            name_edit.setText("");// gán name_edit bằng rỗng
            clickListener.onClick(name);
            //gọi phương thức onClick
            dismiss();// đóng hộp thoại
        });

        return  builder.create();//tạo và trả đối tượng về AlertDialog
    }

    private Dialog getAddRoomDialog(){ // hộp thoại thêm phòng
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo một đối tượng AlertDialog.Builder
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.class_dialog, null);
        //sử dụng LayoutInflater để kết hợp layout
        builder.setView(view);//tạo view vào AlertDialog.Builder

        TextView title = view.findViewById(R.id.txtTitle);// ánh xạ biến
        title.setText("Add New Room");//đặt tiêu đề cho hộp thoại


         EditText roomedit = view.findViewById(R.id.editName);// ánh xạ biến

        roomedit.setHint("Enter Name Room");//thông báo gợi ý trong EditText
        Button cancel = view.findViewById(R.id.btnCancel);// ánh xạ biến
        Button add = view.findViewById(R.id.btnAdd);// ánh xạ biến

        cancel.setOnClickListener(v -> dismiss());//xử lý sự kiện khi nút cancel được bấm
        add.setOnClickListener(v -> {//xử lý sự kiện khi nút add được bấm
            String RoomName = roomedit.getText().toString();
            //gán văn bản được nhập trong trường name_edit
            clickListener.onClick(RoomName);
            //gọi phương thức onClick
            dismiss();// đóng hộp thoại
        });
        return  builder.create();//tạo và trả đối tượng về AlertDialog
    }

}
