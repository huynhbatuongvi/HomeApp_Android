package com.example.final_exam;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    // khai báo biến
    ArrayList<DeviceItem> deviceItems;
    Context context;
    private OnItemClickListener onItemClickListener;



    public interface OnItemClickListener { // xử lý sự kiện nút nhấn
        void onClick(int position);//xử lý sự kiện khi một mục trong Adapter được bấm
        void onLongClick(int position);
    }

    public DeviceAdapter( OnItemClickListener onItemClickListener) { // tạo hàm constructor
        this.onItemClickListener= onItemClickListener;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        //khi một mục được bấm, phương thức onClick của OnItemClickListener được gọi
    }

    public DeviceAdapter(Context context, ArrayList<DeviceItem> deviceItems) { // tạo hàm constructor
        this.deviceItems = deviceItems;
        this.context = context;
    }
    public static class DeviceViewHolder extends RecyclerView.ViewHolder {

        // khai báo biến
        TextView name;
        ImageView imgDevice;
        SeekBar seekBar;

        public DeviceViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            // ánh xạ biến
            name = itemView.findViewById(R.id.name);
            seekBar = itemView.findViewById(R.id.seekbar_item);
            imgDevice = itemView.findViewById(R.id.imagedevice);


            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
                itemView.setOnLongClickListener(v -> {
                    onItemClickListener.onLongClick(getAdapterPosition());
            //đối tượng được gọi và vị trí hiện tại của mục trong danh sách sẽ được chuyển vào
                    return true;
                });
            }

        }

    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,parent,false);
        //chuyển đổi tệp cấu trúc XML room_item.xml thành một đối tượng View
        return new DeviceViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) { // khi hiển thị một item mới
        DeviceItem currentitem = deviceItems.get(position);// lấy item gần nhất từ danh sách deviceItems tại vị trí hiện tại
        holder.name.setText(currentitem.getNamedevice());// đặt tên item thông qua biến name

        int initialAlpha = currentitem.getAlpha();// tại giá trị alpha ban đầu
        holder.imgDevice.setImageAlpha(initialAlpha);//thiết lập độ mờ ban đầu cho ảnh
        holder.seekBar.setProgress(initialAlpha);//thiết lập gía trị  mờ ban đầu thanh seekbar
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        // khi thay đổi trạng thái thanh Seekbar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int alpha = progress; // cập nhật trạng thái mới của thanh seekbar
                    currentitem.setAlpha(alpha);// gán trị alpha cho item hiện tại
                    holder.imgDevice.setImageAlpha(alpha);//thiết lập độ mờ ảnh thông qua giá trị alpha
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return deviceItems.size();
        //trả về số lượng mục trong tập dữ liệu
    }
}
