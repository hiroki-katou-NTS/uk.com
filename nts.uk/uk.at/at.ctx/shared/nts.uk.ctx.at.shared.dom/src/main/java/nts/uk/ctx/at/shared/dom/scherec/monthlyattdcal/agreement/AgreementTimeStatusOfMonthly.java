package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

/**
 * 月別実績の36協定時間状態
 * @author shuichu_ishida
 */
public enum AgreementTimeStatusOfMonthly {
	/** 正常 */
	NORMAL(0),
	/** 限度エラー時間超過 */
	EXCESS_LIMIT_ERROR(1),
	/** 限度アラーム時間超過 */
	EXCESS_LIMIT_ALARM(2),
	/** 特例限度エラー時間超過 */
	EXCESS_EXCEPTION_LIMIT_ERROR(3),
	/** 特例限度アラーム時間超過 */
	EXCESS_EXCEPTION_LIMIT_ALARM(4),
	/** 正常（特例あり） */
	NORMAL_SPECIAL(5),
	/** 限度エラー時間超過（特例あり） */
	EXCESS_LIMIT_ERROR_SP(6),
	/** 限度アラーム時間超過（特例あり） */
	EXCESS_LIMIT_ALARM_SP(7),
	/** 特別条項の上限時間超過 */
	EXCESS_BG_GRAY(8);
	
	public int value;
	private AgreementTimeStatusOfMonthly(int value){
		this.value = value;
	}
}
