package ch3.entity;

public class BlackContactInfo {

	// ����������
	private String phoneNumber;
	// �ڰ׵���ϵ������
	private String contactName;
	// ����������ģʽ 1 �绰 2���� 3�绰&����
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
			return "�绰";
		
		case 2:
			return "����";
		

		case 3:
			return "�绰&����";
		

		}
		return "";

	}

}
