package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime;

/**
 * 月別実績の乖離フラグ
 * @author shuichu_ishida
 */
public enum DivergenceAtrOfMonthly {
	/** 正常 */
	NORMAL(0),
	/** アラーム */
	ALARM(1),
	/** エラー */
	ERROR(2);
	
	public int value;
	private DivergenceAtrOfMonthly(int value){
		this.value = value;
	}
}
