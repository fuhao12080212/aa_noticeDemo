package com.GetNotice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author hao
 *编写目的：用于解析从服务器端读取的input流
 *返回值：解析的notice的list
 */
public class DomParseNoticeXML {

	public DomParseNoticeXML() {
		// TODO Auto-generated constructor stub
	}

	public List<Notice> getNotice(InputStream inputStream) {
		// TODO Auto-generated method stub
		List<Notice> list = new ArrayList<Notice>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
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
				list.add(notice);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
