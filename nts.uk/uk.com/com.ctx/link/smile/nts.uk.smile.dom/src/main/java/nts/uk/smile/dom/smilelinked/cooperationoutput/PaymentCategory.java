package nts.uk.smile.dom.smilelinked.cooperationoutput;

/**
 * 支払区分
 */
public enum PaymentCategory {
	/** 01 */
	FIRST(1, "01"),

	/** 02 */
	SECOND(2, "02"),
	
	/** 03 */
	THIRD(3,  "03");
	
	public int value;
	
	public String name;
	
	private PaymentCategory (int value, String name) {
		this.value = value;
		this.name = name;
	}
}
