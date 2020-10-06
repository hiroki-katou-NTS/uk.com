package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import lombok.AllArgsConstructor;

/** 超過状態 */
@AllArgsConstructor
public enum ExcessState {

	/** 正常 */
	NORMAL(0),
	/** アラーム時間超過 */
	ALARM_OVER(0),
	/** エラー時間超過 */
	ERROR_OVER(0),
	/** 上限時間超過 */
	UPPER_LIMIT_OVER(0);
	
	public int value;
}
