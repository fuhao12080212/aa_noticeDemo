package com.GetNotice;

/**
 * @author hao
 *数据类：存放所有notice的具体值
 */
public class Notice {

	private String Id;
	private String Emphasis;
	private String NoticeTime;
	private String Place;
	private String Details;
	private String Sender;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getEmphasis() {
		return Emphasis;
	}

	public void setEmphasis(String emphasis) {
		Emphasis = emphasis;
	}

	public String getNoticeTime() {
		return NoticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		NoticeTime = noticeTime;
	}

	public String getPlace() {
		return Place;
	}

	public void setPlace(String place) {
		Place = place;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	
	@Override
	public String toString() {
		return "Notice [Id=" + Id + ", Emphasis=" + Emphasis + ", NoticeTime="
				+ NoticeTime + ", Place=" + Place + ", Details=" + Details
				+ ", Sender=" + Sender + "]";
	}

	public Notice() {
		// TODO Auto-generated constructor stub
	}

}
