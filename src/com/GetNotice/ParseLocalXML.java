package com.GetNotice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.CommonData.StringData;

/**
 * @author hao
 *编写目的：读取当地XML文件，便于页面跳转，不用再次连接服务器
 *返回值：所有notice的list
 */
public class ParseLocalXML {

	private StringData mydata = new StringData();
	private List<Notice> noticelist = new ArrayList<Notice>();
	private String XmlPath = null;

	public ParseLocalXML() {
		// TODO Auto-generated constructor stub
	}

	public List<Notice> getNoticeList() {
		XmlPath = mydata.getLocalXMLPath();
		File xmlFile = new File(XmlPath);
		try {
			DocumentBuilder builder = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			Element element = document.getDocumentElement();

			NodeList noticeNodes = element.getElementsByTagName("notice");
			for (int i = 0; i < noticeNodes.getLength(); i++) {
				Element noticeElement = (Element) noticeNodes.item(i);
				Notice notice = new Notice();

				NodeList childNodes = noticeElement.getChildNodes();

				for (int j = 0; j < childNodes.getLength(); j++) {
					if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("Id".equals(childNodes.item(j).getNodeName())) {
							notice.setId(childNodes.item(j).getFirstChild()
									.getNodeValue());
						} else if ("Emphasis".equals(childNodes.item(j)
								.getNodeName())) {
							notice.setEmphasis(childNodes.item(j)
									.getFirstChild().getNodeValue());
						} else if ("NoticeTime".equals(childNodes.item(j)
								.getNodeName())) {
							notice.setNoticeTime(childNodes.item(j)
									.getFirstChild().getNodeValue());
						} else if ("Place".equals(childNodes.item(j)
								.getNodeName())) {
							notice.setPlace(childNodes.item(j).getFirstChild()
									.getNodeValue());
						} else if ("Details".equals(childNodes.item(j)
								.getNodeName())) {
							notice.setDetails(childNodes.item(j)
									.getFirstChild().getNodeValue());
						} else if ("Sender".equals(childNodes.item(j)
								.getNodeName())) {
							notice.setSender(childNodes.item(j).getFirstChild()
									.getNodeValue());
						}
					}
				}
				noticelist.add(notice);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return noticelist;
	}

}
