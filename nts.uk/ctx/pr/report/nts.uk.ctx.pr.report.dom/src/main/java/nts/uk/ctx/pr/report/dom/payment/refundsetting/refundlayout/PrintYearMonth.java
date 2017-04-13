package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;

/** 印字する年月 */
@AllArgsConstructor
public enum PrintYearMonth {
	/**
	 * 1:現在処理年月の2ヶ月前
	 */
	DATE_PRINT1(1),
	/**
	 * 2:現在処理年月の1か月前
	 */
	DATE_PRINT2(2),
	/**
	 * 3:現在処理年月
	 */
	DATE_PRINT3(3),
	/**
	 * 4:現在処理年月
	 */
	DATE_PRINT4(4),
	/**
	 * 5:現在処理年月
	 */
	DATE_PRINT5(5);

	public final int value;
}
