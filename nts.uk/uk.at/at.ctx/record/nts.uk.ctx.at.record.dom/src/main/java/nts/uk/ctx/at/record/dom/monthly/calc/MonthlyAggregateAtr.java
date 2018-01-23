package nts.uk.ctx.at.record.dom.monthly.calc;

/**
 * 集計区分
 * @author shuichu_ishida
 */
public enum MonthlyAggregateAtr {
	/** 月の計算 */
	MONTHLY(0),
	/** 時間外超過 */
	EXCESS_OUTSIDE_WORK(1);

	/**
	 * 月の計算か判定する
	 * @return true：月の計算、false：月の計算でない
	 */
	public boolean isMonthly(){
		return this.equals(MONTHLY);
	}

	/**
	 * 時間外超過か判定する
	 * @return true：時間外超過、false時間外超過でない
	 */
	public boolean isExcessOutsideWork(){
		return this.equals(EXCESS_OUTSIDE_WORK);
	}
	
	public int value;
	private MonthlyAggregateAtr(int value){
		this.value = value;
	}
}
