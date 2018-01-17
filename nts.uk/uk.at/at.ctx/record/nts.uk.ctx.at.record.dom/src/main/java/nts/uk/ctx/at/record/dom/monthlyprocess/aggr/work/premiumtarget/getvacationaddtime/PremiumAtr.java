package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

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

	/**
	 * 割増か判定する
	 * @return true：割増、false：割増でない
	 */
	public boolean isPremium(){
		return this.equals(PREMIUM);
	}
	
	/**
	 * 不足時か判定する
	 * @return true：不足時、false：不足時でない
	 */
	public boolean isWhenShortage(){
		return this.equals(WHEN_SHORTAGE);
	}
	
	/**
	 * 法定内のみか判定する
	 * @return true：法定内のみ、false：法定内のみでない
	 */
	public boolean isOnlyLegal(){
		return this.equals(ONLY_LEGAL);
	}
	
	public int value;
	private PremiumAtr(int value){
		this.value = value;
	}
}
