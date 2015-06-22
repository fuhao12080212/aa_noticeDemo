package com.Login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.CommonData.StringData;
import com.GetNotice.DomParseNoticeXML;
import com.GetNotice.NotesActivity;
import com.GetNotice.Notice;
import com.GetNotice.NoticeToXML;
import com.Register.RegisterActivity;
import com.example.notification.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author hao
 *��½���ܣ����û������ť��ʱ�����ӷ�������ȡ����
 */
public class LoginActivity extends Activity {

	static StringData myData = new StringData();
	// �ؼ�����
	private EditText username, password;
	private CheckBox rem_pw, Auto_login;
	private ImageButton btn_register, btn_login;
	private ProgressDialog dialog;
	private SharedPreferences sharedPreferences;
	private String userNameValue, passwordValue;
	private String loginState = null;

	// �ӷ������˻������
	static StringData mydata = new StringData();
	private static String XMLPath = mydata.getXMLPath();
	private static String getNoticePath = mydata.getGetNoticePath();
	private List<Notice> noticeList = new ArrayList<Notice>();
	private final int IS_FINISH = 1;

	// �õ���������ַ
	private static String PATH = myData.getLoginPath();

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �������������
		Typeface fontFace = Typeface.createFromAsset(getAssets(),
				"fonts/Kim's GirlType.ttf");
		// ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		sharedPreferences = this.getSharedPreferences("userInfo",
				Context.MODE_WORLD_READABLE);
		username = (EditText) findViewById(R.id.username_ed);
		password = (EditText) findViewById(R.id.password_ed);
		rem_pw = (CheckBox) findViewById(R.id.remember_cb);
		Auto_login = (CheckBox) findViewById(R.id.auto_login_cb);
		btn_login = (ImageButton) findViewById(R.id.login_bt);
		btn_register = (ImageButton) findViewById(R.id.register_bt);
		dialog = new ProgressDialog(this);
		dialog.setMessage("���ڵ�½....");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		username.setTypeface(fontFace);
		password.setTypeface(fontFace);
		// �жϼ�ס�����ѡ���״̬
		// getBoolean����һΪ�ļ��е�xmlֵ��������λȱʡֵ
		if (sharedPreferences.getBoolean("ISCHECK", false)) {

			// ����Ĭ���Ǽ�¼����״̬
			rem_pw.setChecked(true);
			username.setText(sharedPreferences.getString("USER_NAME", ""));
			password.setText(sharedPreferences.getString("PASSWORD", ""));

			// �ж��Զ���¼��ѡ��״̬
			if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) {

				new Thread(new NoticeToXMLThread()).start();
				// ����Ĭ�����Զ���½״̬
				Auto_login.setChecked(true);
				// ��ת����

				Intent intent = new Intent(LoginActivity.this,
						NotesActivity.class);
				intent.putExtra("username",
						sharedPreferences.getString("USER_NAME", ""));
				LoginActivity.this.startActivity(intent);
			}
		}

		// ��½�����¼� ����Ĭ��Ϊ�û���Ϊ��fuhao ���룺fuhao
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��½�ɹ��ͼ�ס�����Ϊѡ��״̬�ű����û���Ϣ
				new Thread(new NoticeToXMLThread()).start();
				new AuthenticationTask().execute(PATH);
			}
		});

		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ת����
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});

		// ������ס�����ѡ��ť�¼�
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (rem_pw.isChecked()) {
					sharedPreferences.edit().putBoolean("ISCHECK", true)
							.commit();
				} else {
					sharedPreferences.edit().putBoolean("ISCHECK", false)
							.commit();
				}
			}
		});

		// �����Զ���½��ѡ���¼�
		Auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (Auto_login.isChecked()) {
					sharedPreferences.edit().putBoolean("AUTO_ISCHECK", true)
							.commit();
				} else {
					sharedPreferences.edit().putBoolean("AUTO_ISCHECK", false)
							.commit();
				}
			}
		});

	}

	/**
	 * @author hao ʹ���첽����Ĺ��� 1������һ���̳�AsyncTask ��ע��������������
	 *         2����һ��������ʾҪִ�е�����ͨ���������·�����ڶ���������ʾ���ȵĿ̶� ��������������ʾ����ִ�еķ��ؽ��
	 * 
	 */
	public class AuthenticationTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String loginResult = "";
			try {
				URL url = new URL(params[0]);
				Map<String, String> userInfo = new HashMap<String, String>();
				userNameValue = username.getText().toString();
				passwordValue = password.getText().toString();
				userInfo.put("USER_NAME", userNameValue);
				userInfo.put("PASSWORD", passwordValue);

				StringBuffer buffer = new StringBuffer();
				if (userInfo != null && !userInfo.isEmpty()) {
					for (Map.Entry<String, String> entry : userInfo.entrySet()) {
						buffer.append(entry.getKey())
								.append("=")
								.append(URLEncoder.encode(entry.getValue(),
										"utf-8")).append("&");
					}
					buffer.deleteCharAt(buffer.length() - 1);
				}
				System.out.println("-->>" + buffer.toString());
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.setConnectTimeout(3000);
				byte[] mydata = buffer.toString().getBytes();
				urlConnection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				urlConnection.setRequestProperty("Content-Length",
						String.valueOf(mydata.length));
				OutputStream outputStream = urlConnection.getOutputStream();
				outputStream.write(mydata, 0, mydata.length);
				outputStream.close();

				int responseCode = urlConnection.getResponseCode();

				if (responseCode == 200) {
					loginResult = changeInputStream(
							urlConnection.getInputStream(), "utf-8");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return loginResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			loginState = result;
			if (rem_pw.isChecked()) {
				// ��ס�û���������
				Editor editor = sharedPreferences.edit();
				editor.putString("USER_NAME", userNameValue);
				editor.putString("PASSWORD", passwordValue);
				editor.commit();
			}
			if (loginState.equals("pass")) {
				Toast.makeText(LoginActivity.this, "��½�ɹ�", Toast.LENGTH_SHORT)
						.show();

				// ��ת����
				Intent intent = new Intent(LoginActivity.this,
						NotesActivity.class);
				intent.putExtra("username", userNameValue);
				LoginActivity.this.startActivity(intent);
			} else if (loginState.equals("not pass")) {
				Toast.makeText(LoginActivity.this, "�û�����������������µ�½",
						Toast.LENGTH_LONG).show();
			} else if (loginState.equals("not find")) {
				Toast.makeText(LoginActivity.this, "���û�������", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(LoginActivity.this, "������������״̬",
						Toast.LENGTH_LONG).show();
			}
			dialog.dismiss();
		}

		private String changeInputStream(InputStream inputStream, String encode) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int len = 0;
			String result = "";
			if (inputStream != null) {
				try {
					while ((len = inputStream.read(data)) != -1) {
						outputStream.write(data, 0, len);
					}
					result = new String(outputStream.toByteArray(), encode);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}
	}

	private Handler ToXMLHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == IS_FINISH) {
				if (msg.obj.equals("ok")) {
					new Thread(new GetNoticeThread()).start();
				}
				System.out.println("result->>" + msg.obj);
			}
		};
	};

	private Handler GetNoticeHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			if (msg.what == IS_FINISH) {
				noticeList = (List<Notice>) msg.obj;
				NoticeToXML noticeToXML = new NoticeToXML(noticeList);
				noticeToXML.ToXML();
			}
		};
	};

	//���������˷�������ڷ�������������Ҫ��xml�ļ�
	public class NoticeToXMLThread implements Runnable {
		String getNoticeResult;

		@Override
		public void run() {

			try {
				URL url = new URL(getNoticePath);
				Map<String, String> userInfo = new HashMap<String, String>();
				userInfo.put("USER_NAME", username.getText().toString());

				StringBuffer buffer = new StringBuffer();
				if (userInfo != null && !userInfo.isEmpty()) {
					for (Map.Entry<String, String> entry : userInfo.entrySet()) {
						buffer.append(entry.getKey())
								.append("=")
								.append(URLEncoder.encode(entry.getValue(),
										"utf-8")).append("&");
					}
					buffer.deleteCharAt(buffer.length() - 1);
				}
				System.out.println("-->>-->>" + buffer.toString());
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				urlConnection.setConnectTimeout(3000);
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.setRequestMethod("POST");
				byte[] mydata = buffer.toString().getBytes();
				urlConnection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				urlConnection.setRequestProperty("Content-Length",
						String.valueOf(mydata.length));
				OutputStream outputStream = urlConnection.getOutputStream();
				outputStream.write(mydata, 0, mydata.length);
				outputStream.close();

				int responseCode = urlConnection.getResponseCode();

				if (responseCode == 200) {
					getNoticeResult = changeInputStream(
							urlConnection.getInputStream(), "utf-8");
				}

				Message notice = Message.obtain();
				notice.obj = getNoticeResult;
				notice.what = IS_FINISH;
				ToXMLHandler.sendMessage(notice);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public String changeInputStream(InputStream inputStream, String encode) {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//�ӷ������˶�ȡ�Ѿ�ˢ�µ�xml�ļ�
	public class GetNoticeThread implements Runnable {

		InputStream input = null;
		List<Notice> notice = new ArrayList<Notice>();

		@Override
		public void run() {
			try {
				URL XMlurl = new URL(XMLPath);
				HttpURLConnection connection = (HttpURLConnection) XMlurl
						.openConnection();
				connection.setConnectTimeout(3000);
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				if (responseCode == 200) {
					input = connection.getInputStream();
				}

				DomParseNoticeXML domParseNoticeXML = new DomParseNoticeXML();
				notice = domParseNoticeXML.getNotice(input);

				Message sendNotice = Message.obtain();
				sendNotice.obj = notice;
				sendNotice.what = IS_FINISH;
				GetNoticeHandler.sendMessage(sendNotice);
			} catch (Exception e) {
				System.out.println("-----url ERROR");
			}
		}

	}
}
