package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import lombok.AllArgsConstructor;

/** 超過状態 */
@AllArgsConstructor
public enum ExcessState {

	/** 正常 */
	NORMAL(0),
	/** アラーム時間超過 */
	ALARM_OVER(1),
	/** エラー時間超過 */
	ERROR_OVER(2),
	/** 上限時間超過 */
	UPPER_LIMIT_OVER(3);
	
	public int value;
}
