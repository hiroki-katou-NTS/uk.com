package nts.uk.ctx.at.record.dom.application.realitystatus.enums;

public enum ApprovalStatusMailType {
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

	private ApprovalStatusMailType(int value) {
		this.value = value;
	}
}
