package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum 承認活性
 *
 */
public enum ApprovalActivity {
	
	/** 活性 */
	Activity(0),
	/** 非活性  */
	Inactive(1),
	/** 承認済 */
	Approved(2),
	/** 否認済 */
	Deny(3);
	
	public final int value;

	private ApprovalActivity(int value) {
		this.value = value;
	}
}
 