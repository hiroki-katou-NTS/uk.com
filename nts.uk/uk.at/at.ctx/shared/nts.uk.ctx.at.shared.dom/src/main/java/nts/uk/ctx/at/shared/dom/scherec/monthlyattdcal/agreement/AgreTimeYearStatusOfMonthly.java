package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

/**
 * 月別実績の36協定年間時間状態
 * @author shuichi_ishida
 */
public enum AgreTimeYearStatusOfMonthly {
	/** 正常 */
	NORMAL(0),
	/** 限度超過 */
	EXCESS_LIMIT(1);
	
	public int value;
	private AgreTimeYearStatusOfMonthly(int value){
		this.value = value;
	}
}
