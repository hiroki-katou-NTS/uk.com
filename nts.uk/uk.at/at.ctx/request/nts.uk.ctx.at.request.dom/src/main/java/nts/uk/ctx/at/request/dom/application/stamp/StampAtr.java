package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *	
 */
/**
 * 
 * 打刻分類
 *
 */
public enum StampAtr {

	/**
	 * 出勤／退勤
	 */
	ATTENDANCE(0, "出勤／退勤"),

	/**
	 * 臨時
	 */
	EXTRAORDINARY(1, "臨時"),
	
	/**
	 * 外出
	 */
	GO_OUT(2, "外出"),
	
	/**
	 * 育児
	 */
	CHILDCARE(3, "育児"),
	
	/**
	 * 介護
	 */
	CARE(4, "介護"),
	
	/**
	 * 応援
	 */
	SUPPORT(5, "応援");

	public final int value;

	public final String name;

	StampAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
