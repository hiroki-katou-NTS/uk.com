package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

/**
 * 月別実績の36協定上限時間状態
 * @author shuichi_ishida
 */
public enum AgreMaxTimeStatusOfMonthly {
	/** 正常 */
	NORMAL(0),
	/** アラーム時間超過 */
	ALARM_OVER(1),
	/** エラー時間超過 */
	ERROR_OVER(2);
	
	public int value;
	private AgreMaxTimeStatusOfMonthly(int value){
		this.value = value;
	}
}
