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
public enum AppStampAtr {
	
	ATTENDANCE(0, "出勤／退勤"),
	
	GO_OUT(1, "外出/戻り"),
	
	CHILDCARE(2, "育児外出/育児戻り"),
	
	CARE(3, "介護外出/介護戻り"),
	
	SUPPORT(4, "応援入/応援出");

	public final int value;

	public final String name;

	AppStampAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
