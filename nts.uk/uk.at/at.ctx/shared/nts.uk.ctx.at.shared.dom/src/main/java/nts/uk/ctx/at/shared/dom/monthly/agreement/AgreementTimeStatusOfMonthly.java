package nts.uk.ctx.at.shared.dom.monthly.agreement;

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
	/** 特例限度時間内 */
	IN_EXCEPTION_LIMIT(3),
	/** 特例限度エラー時間超過 */
	EXCESS_EXCEPTION_LIMIT_ERROR(4),
	/** 特例限度アラーム時間超過 */
	EXCESS_EXCEPTION_LIMIT_ALARM(5);
	
	public int value;
	private AgreementTimeStatusOfMonthly(int value){
		this.value = value;
	}
}
