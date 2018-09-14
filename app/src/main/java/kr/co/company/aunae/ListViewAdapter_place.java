package kr.co.company.aunae;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter_place extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem_place> listViewItemList = new ArrayList<>();

    private Context mContext;

    // ListViewAdapter의 생성자
    public ListViewAdapter_place() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item_ihome" layout을 inflate하여 converView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_place, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.txtName = (TextView)convertView.findViewById(R.id.list_text);
            holder.imgBack = (ImageView)convertView.findViewById(R.id.list_background);
            convertView.setTag(holder);
        }
        /******
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleview = (TextView) convertView.findViewById(R.id.list_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_background);
        ******/

        // Data Set(ListViewItem_ihomes)에서 position에 위치한 데이터 참조 획득
        ListViewItem_place listViewItem = listViewItemList.get(position);
        if(listViewItem != null) {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.txtName.setText(listViewItem.getTitle());
            holder.imgBack.setImageBitmap(listViewItem.getBackground());
        }

        /******
        // 아이템 내 각 위젯에 데이터 반영
        titleview.setText(listViewItem.getTitle());
        imageView.setImageBitmap(listViewItem.getBackground());
        ******/
        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String ti, Bitmap bit) {
        ListViewItem_place item = new ListViewItem_place(ti, bit);

        item.setTitle(ti);
        item.setBackground(bit);

        listViewItemList.add(item);
    }

    static class ViewHolder {
        TextView txtName;
        ImageView imgBack;
    }
}
