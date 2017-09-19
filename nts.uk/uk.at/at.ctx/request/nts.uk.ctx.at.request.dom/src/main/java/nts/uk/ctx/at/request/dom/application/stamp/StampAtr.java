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
	
	ATTENDANCE(0, "出勤／退勤"),
	
	GO_OUT(1, "外出/戻り"),
	
	CHILDCARE(2, "育児外出/育児戻り"),
	
	SUPPORT(3, "応援入/応援出");

	public final int value;

	public final String name;

	StampAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
