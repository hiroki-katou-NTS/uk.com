package nts.uk.ctx.at.shared.dom.monthly.agreement;

/**
 * 月別実績の36協定上限時間状態
 * @author shuichi_ishida
 */
public enum AgreMaxTimeStatusOfMonthly {
	/** 正常 */
	NORMAL(0),
	/** 上限時間超過 */
	EXCESS_MAXTIME(1);
	
	public int value;
	private AgreMaxTimeStatusOfMonthly(int value){
		this.value = value;
	}
}
