package com.Register;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.CommonData.StringData;
import com.GetNotice.NotesActivity;
import com.example.notification.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText username, password, repassword, name, phone;
	private RadioGroup sex;
	private ImageButton register;
	private ProgressDialog dialog;

	private static String PATH = StringData.getRegisterPath();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 设置输入的字体
		Typeface fontFace = Typeface.createFromAsset(getAssets(),
				"fonts/Kim's GirlType.ttf");
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);

		// 控件实例化
		username = (EditText) findViewById(R.id.username_et);
		password = (EditText) findViewById(R.id.password_et);
		repassword = (EditText) findViewById(R.id.repassword_et);
		name = (EditText) findViewById(R.id.name_et);
		sex = (RadioGroup) findViewById(R.id.sex);
		phone = (EditText) findViewById(R.id.phone_et);
		register = (ImageButton) findViewById(R.id.register);

		// // 初始化
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在完成注册....");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		// 设置字体
		username.setTypeface(fontFace);
		password.setTypeface(fontFace);
		repassword.setTypeface(fontFace);
		name.setTypeface(fontFace);
		phone.setTypeface(fontFace);

		// 设置按钮点击事件
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (username.getText().toString().trim().equals("")) {
					username.setError("输入用户名");
				} else if (password.getText().toString().trim().equals("")) {
					password.setError("输入密码");
				} else if (repassword.getText().toString().trim().equals("")) {
					repassword.setError("重复密码");
				} else if (name.getText().toString().trim().equals("")) {
					name.setError("输入真实姓名");
				} else if (phone.getText().toString().trim().equals("")) {
					phone.setError("请输入您的电话号码");
				} else {
					if (password.getText().toString()
							.equals(repassword.getText().toString())) {
						new RegisterTask().execute(PATH);
					} else {
						repassword.setError("密码不匹配");
					}
				}
			}
		});
	}

	public class RegisterTask extends AsyncTask<String, Void, String> {

		// 表示执行之前要实现的效果
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String registerResult = "";

			String usernameVaule = null;
			String passwordVaule = null;
			String nameVaule = null;
			String sexVaule = null;
			String phoneVaule = null;

			usernameVaule = username.getText().toString();
			passwordVaule = password.getText().toString();
			nameVaule = name.getText().toString();

			int len = sex.getChildCount();
			for (int i = 0; i < len; i++) {
				RadioButton radioButton = (RadioButton) sex.getChildAt(i);
				if (radioButton.isChecked()) {
					sexVaule = radioButton.getText().toString();
					break;
				}
			}

			phoneVaule = phone.getText().toString();

			try {
				URL url = new URL(params[0]);
				Map<String, String> userInfo = new HashMap<String, String>();
				userInfo.put("USERNAME", usernameVaule);
				userInfo.put("PASSWORD", passwordVaule);
				userInfo.put("NAME", nameVaule);
				userInfo.put("SEX", sexVaule);
				userInfo.put("PHONE", phoneVaule);

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
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
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
					registerResult = changeInputStream(
							urlConnection.getInputStream(), "utf-8");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return registerResult;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equals("OK")) {
				Toast.makeText(RegisterActivity.this, "注册成功",
						Toast.LENGTH_SHORT).show();

				// 跳转界面
				Intent intent = new Intent(RegisterActivity.this,
						NotesActivity.class);
				RegisterActivity.this.startActivity(intent);
			} else if (result.equals("NO")) {
				Toast.makeText(RegisterActivity.this, "注册失败，请重新注册",
						Toast.LENGTH_LONG).show();
			} else if(result.equals("exist")){
				Toast.makeText(RegisterActivity.this, "此用户名已存在",
						Toast.LENGTH_LONG).show();
				username.setText("");
				username.requestFocus();
			}
			else {
				Toast.makeText(RegisterActivity.this, "请检查网络连接状态",
						Toast.LENGTH_LONG).show();
			}
			dialog.dismiss();
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
}
