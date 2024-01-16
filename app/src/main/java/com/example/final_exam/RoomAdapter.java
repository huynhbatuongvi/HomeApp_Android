package com.example.final_exam;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    // khai biến biến
    ArrayList<RoomItems> roomItem;
    Context context;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener { // xử lý sự kiện nút nhấn
        void onClick(int position); //xử lý sự kiện khi một mục trong Adapter được bấm
        void onLongClick(int position);//xử lý sự kiện khi một mục trong Adapter khi bấm longclick
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        //khi một mục được bấm, phương thức onClick của OnItemClickListener được gọi
    }

    public RoomAdapter(Context context, ArrayList<RoomItems> roomItem) {// tạo hàm constructor
        this.roomItem = roomItem;
        this.context = context;

    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder  {

        // khai báo biến
        TextView roomName;
        ImageView imgRoom;
        ConstraintLayout Grid;
        public RoomViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            // ánh xạ biến
            imgRoom = itemView.findViewById(R.id.imageroom);
            roomName = itemView.findViewById(R.id.txtRoom);
            Grid = itemView.findViewById(R.id.Grid);

            if (onItemClickListener != null) {
               itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
                //đối tượng được gọi và vị trí hiện tại của mục trong danh sách sẽ được chuyển vào
            }

            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
                itemView.setOnLongClickListener(v -> {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                });
            }

        }

    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item,parent,false);
        //chuyển đổi tệp cấu trúc XML room_item.xml thành một đối tượng View
        return new RoomViewHolder(view, onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) { // để liên kết dữ liệu với ViewHolder
        holder.roomName.setText(roomItem.get(position).getRoomName());
        //thiết lập văn bản bằng tên phòng từ đối tượng
        holder.imgRoom.setImageResource(roomItem.get(position).getRoomImage());
        //đặt hình ảnh của ImageView trong ViewHolder
    }

    @Override
    public int getItemCount() {
        return roomItem.size();
    } //trả về số lượng mục trong tập dữ liệu

}
