package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.hdwkandcompleave;

/**　法定内休出集計区分　*/
public enum StatutoryHolidayAggregateAtr {
	
	/** 法定内含む*/
	AGGREGATE_STATUTORY_HOLIDAYS(0),
	/** 法定内含まない */
	NO_AGGREGATE_STATUTORY_HOLIDAYS(1);
	
	public int value;
	
	private StatutoryHolidayAggregateAtr(int value){
		this.value = value;
	}
}
