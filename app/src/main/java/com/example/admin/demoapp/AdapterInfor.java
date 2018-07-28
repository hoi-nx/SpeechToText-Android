package com.example.admin.demoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterInfor extends BaseAdapter {
    List<ItemData> list;


    public AdapterInfor(List<ItemData> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ItemData itemData = list.get(i);
        viewHolder.name_player.setText(itemData.getFirstName() + "" + itemData.getLastName());
        viewHolder.name_numer.setText(itemData.getNumber()+"");
        viewHolder.tv_team.setText(itemData.getTeam());
        viewHolder.tv_time.setText(itemData.getTime());
        return view;
    }


    public class ViewHolder {
        private TextView name_player, name_numer, tv_team, tv_time;

        public ViewHolder(View itemView) {
            name_player = itemView.findViewById(R.id.name_player);
            name_numer = itemView.findViewById(R.id.name_numer);
            tv_team = itemView.findViewById(R.id.tv_team);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

    }
}
