package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum 承認状況
 *
 */
public enum ApprovalStatus {
	
	/**  未承認 */
	Not_Acknowledged(1),
	/** 承認済 */
	Approved(2),
	/**  否認 */
	Deny(3),
	/**  代行承認済 */
	Agency_Approved(4),
	/** 差し戻し */
	Send_Back(5),
	/** 本人差し戻し  */
	Send_Back_Person(6);
	
	
	public final int value;

	private ApprovalStatus(int value) {
		this.value = value;
	}
}
 