package com.ar.siosi.swmaestrobackendproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by siosi on 2016-07-18.
 */
public class FriendndListViewAdapter extends ArrayAdapter implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }

    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    FriendndListViewAdapter(Context context, int resource, ArrayList<FriendListView> list, ListBtnClickListener clickListener) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;
        this.listBtnClickListener = clickListener ;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1);
        final TextView textTextView = (TextView) convertView.findViewById(R.id.textView1);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final FriendListView listViewItem = (FriendListView) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());

        // button1 클릭 시 TextView(textView1)의 내용 변경.
        ImageButton button1 = (ImageButton) convertView.findViewById(R.id.addFriendButton);

        button1.setOnClickListener(new ImageButton.OnClickListener() {
            public void onClick(View v) {
                // TODO: 2016-07-18  여기 친구 추가하는 함수를 넣어야되나
                // 흠 더 고민해보자

                PoketMonTrainer.currentTrainer.haveFriendList.add(textTextView.getText().toString());
                Toast.makeText(getContext(),textTextView.getText().toString() + " 친구에게 친구 요청을 보냈습니다. ",Toast.LENGTH_LONG).show();
            //    textTextView.setText(Integer.toString(pos + 1) + "친구 추가 완료.");

            }
        });

        return convertView;
    }



}
