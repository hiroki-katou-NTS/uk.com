package nts.uk.ctx.hr.notice.dom.report.registration.person.enu;
/**
 * enum メール送信区分
 *
 */
public enum EmailTransmissionClass {
	
	/**  送信する */
	Send(0),
	/** 承認済 */
	DoNotSend(1);
	
	public final int value;

	private EmailTransmissionClass(int value) {
		this.value = value;
	}
}
 