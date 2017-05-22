package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 枠線幅 */
@AllArgsConstructor
public enum BordWidth {
	/**
	 * 0.太い
	 */
	FAT(0),
	/**
	 * 1.標準
	 */
	STANDART(1),
	/**
	 * 2.細い
	 */
	THIN(2);

	public final int value;
}
