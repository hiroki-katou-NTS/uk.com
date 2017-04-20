package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import lombok.AllArgsConstructor;

/** 確認状況 */
@AllArgsConstructor
public enum ConfirmedStatus {

	/**
	 * 0:未確認
	 */
	UNCONFIRMED(0),

	/**
	 * 1:確認済み
	 */
	CONFIRMED(1);

	public final int value;
}
