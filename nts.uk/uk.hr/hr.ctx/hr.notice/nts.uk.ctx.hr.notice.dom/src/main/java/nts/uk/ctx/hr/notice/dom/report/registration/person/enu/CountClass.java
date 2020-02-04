package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum カウント大区分
 *
 */
public enum CountClass {
	
	// 届出数量
	Reported_Quantity(1),
	/** 差し戻し数量 */
	Send_Back_Quantity(2);
	
	public final int value;

	private CountClass(int value) {
		this.value = value;
	}
}
 