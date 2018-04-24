package nts.uk.ctx.at.request.dom.application.approvalstatus.service.enums;

public enum TransmissionAttr {
	/**
	 * 本人
	 */
	PERSON(1),
	/**
	 * 日次
	 */
	DAILY(2),
	/**
	 * 月次
	 */
	MONTHLY(3);

	public final int value;

	private TransmissionAttr(int value) {
		this.value = value;
	}
}
