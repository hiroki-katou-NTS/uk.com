package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import lombok.AllArgsConstructor;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum Time36AgreementErrorAtr {
	/*
	 * 月間エラー
	 */
	MONTH_ERROR(0,"月間エラー"),
	
	/*
	 * 年間エラー
	 */
	YEAR_ERROR(1,"年間エラー"),
	
	/*
	 * 上限月間時間エラー
	 */
	MAX_MONTH_ERROR(2,"上限月間時間エラー"),
	
	/*
	 * 上限複数月平均時間エラー
	 */
	MAX_MONTH_AVERAGE_ERROR(3,"上限複数月平均時間エラー"),
	
	/*
	 * 上限年間エラー
	 */
	MAX_YEAR_ERROR(4,"上限年間エラー");
	
	public final int value;
	
	public final String name;
}
