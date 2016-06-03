package ch3.entity;

public class BlackContactInfo {

	// 黑名单号码
	private String phoneNumber;
	// 黑白淡联系人名字
	private String contactName;
	// 黑名单拦截模式 1 电话 2短信 3电话&短信
	private int mode;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getModeString(int mode) {
		switch (mode) {
		case 1:
			return "电话";
		
		case 2:
			return "短信";
		

		case 3:
			return "电话&短信";
		

		}
		return "";

	}

}
