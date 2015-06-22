package com.GetNotice;

import java.util.ArrayList;
import java.util.List;
import com.CommonData.StringData;
import com.example.notification.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * @author hao
 *将读取的xml文件的数据显示在界面中
 */
public class NotesActivity extends Activity {

	// TODO 每个跳转到这个Activity的时候一定要传一个username
	// 屏幕高度数据

	// 获得上个Activity中的username
	private Intent intent = null;
	private String Username = null;

	// 从xml文件获得数据
	static StringData mydata = new StringData();
	
	private int noticeNumber = 0;
	private int currentNotice = 1;
	
	private List<Notice> noticeList = new ArrayList<Notice>();
	private ParseLocalXML parseLocalXML = new ParseLocalXML();

	// 控件
	private TableLayout tagsTableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 获得屏幕宽度和高度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// width = dm.widthPixels;
		// height = dm.heightPixels;

		setContentView(R.layout.notes);
		intent = getIntent();
		Username = intent.getStringExtra("username");
		System.out.println("Username->>" + Username);

		tagsTableLayout = (TableLayout) findViewById(R.id.tagsTable);

		// 获得notice数量
		noticeList = parseLocalXML.getNoticeList();
		
		noticeNumber = noticeList.size();
		// TableRow a[] = new TableRow[No];

		for (int i = 0; i < noticeNumber / 2; i++) {
			// 新建一行
			TableRow tableRow = new TableRow(this);
			// 左边的便签
			// 创建相对布局1
			RelativeLayout relativeLayout1 = new RelativeLayout(this);
			relativeLayout1.setId(currentNotice);
			relativeLayout1.setBackgroundResource(R.drawable.tags);

			// 定义文本位置
			RelativeLayout.LayoutParams Timelocation1 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Timelocation1.setMargins(30, 30, 0, 0);

			RelativeLayout.LayoutParams Emphasislocation1 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Emphasislocation1.addRule(RelativeLayout.CENTER_IN_PARENT,
					relativeLayout1.getId());

			RelativeLayout.LayoutParams Senderlocation1 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Senderlocation1.setMargins(180, 250, 20, 20);

			TextView TimeText1 = new TextView(this);
			TimeText1
					.setText(noticeList.get(currentNotice - 1).getNoticeTime());
			TimeText1.setTextColor(Color.parseColor("#8B6508"));

			TextView EmphasisText1 = new TextView(this);
			EmphasisText1.setText(noticeList.get(currentNotice - 1)
					.getEmphasis());
			EmphasisText1.setTextColor(Color.parseColor("#8B6508"));
			EmphasisText1.setSingleLine(false);
			EmphasisText1.setTextSize(20);

			TextView SenderText1 = new TextView(this);
			SenderText1.setText(noticeList.get(currentNotice - 1).getSender());
			SenderText1.setTextColor(Color.parseColor("#8B6508"));

			relativeLayout1.addView(TimeText1, Timelocation1);
			relativeLayout1.addView(EmphasisText1, Emphasislocation1);
			relativeLayout1.addView(SenderText1, Senderlocation1);
			
			relativeLayout1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int current = v.getId();
					System.out.println("---------" + current);
					String noticeId = noticeList.get(current-1).getId();
					Intent intent = new Intent(NotesActivity.this, DetailActivity.class);
					intent.putExtra("noticeId", noticeId);
					NotesActivity.this.startActivity(intent);
				}
			});
			
			// 把控件加入到行布局中
			tableRow.addView(relativeLayout1);

			// 当前消息下移
			currentNotice++;

			// 右边的便签
			RelativeLayout relativeLayout2 = new RelativeLayout(this);
			relativeLayout2.setBackgroundResource(R.drawable.tags);
			relativeLayout2.setId(currentNotice);

			RelativeLayout.LayoutParams Timelocation2 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Timelocation2.setMargins(30, 30, 0, 0);

			RelativeLayout.LayoutParams Emphasislocation2 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Emphasislocation2.addRule(RelativeLayout.CENTER_IN_PARENT,
					relativeLayout2.getId());

			RelativeLayout.LayoutParams Senderlocation2 = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Senderlocation2.setMargins(180, 250, 20, 20);

			TextView TimeText2 = new TextView(this);
			TimeText2
					.setText(noticeList.get(currentNotice - 1).getNoticeTime());
			TimeText2.setTextColor(Color.parseColor("#8B6508"));

			TextView EmphasisText2 = new TextView(this);
			EmphasisText2.setText(noticeList.get(currentNotice - 1)
					.getEmphasis());
			EmphasisText2.setTextColor(Color.parseColor("#8B6508"));
			EmphasisText2.setSingleLine(false);
			EmphasisText2.setTextSize(20);

			TextView SenderText2 = new TextView(this);
			SenderText2.setText(noticeList.get(currentNotice - 1).getSender());
			SenderText2.setTextColor(Color.parseColor("#8B6508"));

			relativeLayout2.addView(TimeText2, Timelocation2);
			relativeLayout2.addView(EmphasisText2, Emphasislocation2);
			relativeLayout2.addView(SenderText2, Senderlocation2);

			relativeLayout2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int current = v.getId();
					System.out.println("---------" + current);
					String noticeId = noticeList.get(current-1).getId();
					Intent intent = new Intent(NotesActivity.this, DetailActivity.class);
					intent.putExtra("noticeId", noticeId);
					NotesActivity.this.startActivity(intent);
				}
			});
			
			tableRow.addView(relativeLayout2);
			currentNotice++;

			tagsTableLayout.addView(tableRow);
		}

		if (noticeNumber % 2 != 0) {
			TableRow tableRow = new TableRow(this);
			// 创建相对布局1
			RelativeLayout relativeLayout = new RelativeLayout(this);
			relativeLayout.setId(currentNotice);
			relativeLayout.setBackgroundResource(R.drawable.tags);

			// 定义文本位置
			RelativeLayout.LayoutParams Timelocation = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Timelocation.setMargins(30, 30, 0, 0);

			RelativeLayout.LayoutParams Emphasislocation = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Emphasislocation.addRule(RelativeLayout.CENTER_IN_PARENT,
					relativeLayout.getId());

			RelativeLayout.LayoutParams Senderlocation = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			Senderlocation.setMargins(180, 250, 20, 20);

			TextView TimeText = new TextView(this);
			TimeText.setText(noticeList.get(currentNotice - 1).getNoticeTime());
			TimeText.setTextColor(Color.parseColor("#8B6508"));

			TextView EmphasisText = new TextView(this);
			EmphasisText.setText(noticeList.get(currentNotice - 1)
					.getEmphasis());
			EmphasisText.setTextColor(Color.parseColor("#8B6508"));
			EmphasisText.setSingleLine(false);
			EmphasisText.setTextSize(20);

			TextView SenderText = new TextView(this);
			SenderText.setText(noticeList.get(currentNotice - 1).getSender());
			SenderText.setTextColor(Color.parseColor("#8B6508"));

			relativeLayout.addView(TimeText, Timelocation);
			relativeLayout.addView(EmphasisText, Emphasislocation);
			relativeLayout.addView(SenderText, Senderlocation);

			relativeLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int current = v.getId();
					System.out.println("---------" + current);
					String noticeId = noticeList.get(current-1).getId();
					Intent intent = new Intent(NotesActivity.this, DetailActivity.class);
					intent.putExtra("noticeId", noticeId);
					NotesActivity.this.startActivity(intent);
				}
			});
			
			// 把控件加入到行布局中
			tableRow.addView(relativeLayout);

			tagsTableLayout.addView(tableRow);
		}
	}
}
