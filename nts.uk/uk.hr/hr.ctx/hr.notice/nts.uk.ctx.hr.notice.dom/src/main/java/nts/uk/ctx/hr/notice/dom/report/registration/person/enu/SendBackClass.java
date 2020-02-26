package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum 差し戻し区分
 *
 */
public enum SendBackClass {
	
	/** 活性 */
	Incomplete_Description(0),
	/** 非活性  */
	Incomplete_Attachment(1);
	
	public final int value;

	private SendBackClass(int value) {
		this.value = value;
	}
}
 