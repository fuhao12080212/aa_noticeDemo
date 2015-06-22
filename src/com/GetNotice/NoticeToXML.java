package com.GetNotice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.CommonData.StringData;

/**
 * @author hao
 *编写目的：把服务器上下载的数据写入本地文件，方便软件读取
 */
public class NoticeToXML {

	private StringData mydata = new StringData();
	private String LocalXMLPath = null;
	private List<Notice> noticeList = null;
	private int DataSize = 0;

	public NoticeToXML(List<Notice> noticeList) {
		// TODO Auto-generated constructor stub
		this.noticeList = noticeList;
		LocalXMLPath = mydata.getLocalXMLPath();
		DataSize = noticeList.size();
	}

	public void ToXML() {
		File xmlFile = new File(LocalXMLPath);
		try {
			xmlFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("exception in createNewFile() method");
		}

		FileOutputStream fileos = null;
		try {
			fileos = new FileOutputStream(xmlFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("can't create FileOutputStream");
		}

		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(fileos, "UTF-8");
			serializer.startDocument(null, true);
			serializer.startTag(null, "notices");
			for (int i = 0; i < DataSize; i++) {
				serializer.startTag(null, "notice");
				
				serializer.startTag(null, "Id");
				serializer.text(noticeList.get(i).getId());
				serializer.endTag(null, "Id");
				
				serializer.startTag(null, "Emphasis");
				serializer.text(noticeList.get(i).getEmphasis());
				serializer.endTag(null, "Emphasis");
				
				serializer.startTag(null, "NoticeTime");
				serializer.text(noticeList.get(i).getNoticeTime());
				serializer.endTag(null, "NoticeTime");
				
				serializer.startTag(null, "Place");
				serializer.text(noticeList.get(i).getPlace());
				serializer.endTag(null, "Place");
				
				serializer.startTag(null, "Details");
				serializer.text(noticeList.get(i).getDetails());
				serializer.endTag(null, "Details");
				
				serializer.startTag(null, "Sender");
				serializer.text(noticeList.get(i).getSender());
				serializer.endTag(null, "Sender");
				
				serializer.endTag(null, "notice");
			}
			
			serializer.endTag(null, "notices");
			serializer.endDocument();
			serializer.flush();
			fileos.close();
			System.out.println("创建xml文件成功");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
