package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime;

/**
 * 割増区分
 * @author shuichi_ishida
 */
public enum PremiumAtr {
	/** 割増 */
	PREMIUM(0),
	/** 不足時 */
	WHEN_SHORTAGE(1),
	/** 法定内のみ */
	ONLY_LEGAL(2);
	
	public int value;
	private PremiumAtr(int value){
		this.value = value;
	}
}
