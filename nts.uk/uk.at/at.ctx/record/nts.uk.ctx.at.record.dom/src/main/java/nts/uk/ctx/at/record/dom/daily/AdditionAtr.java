package nts.uk.ctx.at.record.dom.daily;

public enum AdditionAtr {

	//就業加算時間のみ
	WorkingHoursOnly,
	//全て
	All;
	
	/**
	 * 就業加算時間のみであるか判定する
	 * @return　就業加算時間のみである
	 */
	public boolean isWorkingHoursOnly() {
		return this.equals(WorkingHoursOnly);
	}
		
}
