package com.GetNotice;

import java.util.ArrayList;
import java.util.List;

import com.example.notification.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * @author hao
 *�û������ǩ����ת����һ�����棬��ʾ����֪ͨ������
 */
public class DetailActivity extends Activity {

	private Intent intent = null;
	private String NoticeId = null;
	private Notice notice = new Notice();
	private List<Notice> noticeList = new ArrayList<Notice>();
	private int noticeNumber = 0;
	private ParseLocalXML parseLocalXML = new ParseLocalXML();

	private TextView detail_theme, detail_sender, detail_content, detail_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_note);
		detail_theme = (TextView) findViewById(R.id.detail_theme);
		detail_sender = (TextView)findViewById(R.id.detail_sender);
		detail_content = (TextView)findViewById(R.id.detail_content);
		detail_time = (TextView) findViewById(R.id.detail_time);
		
		// ����ϸ����洫����������
		intent = getIntent();
		NoticeId = intent.getStringExtra("noticeId");
		System.out.println("noticeID--->>" + NoticeId);

		noticeList = parseLocalXML.getNoticeList();
		noticeNumber = noticeList.size();

		for (int i = 0; i < noticeNumber; i++) {
			if (noticeList.get(i).getId().equals(NoticeId)) {
				notice = noticeList.get(i);
			}
		}
		
		String Theme = "���⣺ " + notice.getEmphasis();
		String Sender = "�����ߣ�  " + notice.getSender();
		String Content = "   " + notice.getDetails();
		String Time = "����ʱ��:  " + notice.getNoticeTime();
				
		detail_theme.setText(Theme);
		detail_sender.setText(Sender);
		detail_content.setText(Content);
		detail_time.setText(Time);
	}

}
