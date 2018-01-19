package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime;

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
	
	/**
	 * 正常か判定する
	 * @return true：正常、false：正常でない
	 */
	public boolean isNormal(){
		return this.equals(NORMAL);
	}
	
	/**
	 * アラームか判定する
	 * @return true：アラーム、false：アラームでない
	 */
	public boolean isAlarm(){
		return this.equals(ALARM);
	}
	
	/**
	 * エラーか判定する
	 * @return true：エラー、false：エラーでない
	 */
	public boolean isError(){
		return this.equals(ERROR);
	}
	
	public int value;
	private DivergenceAtrOfMonthly(int value){
		this.value = value;
	}
}
