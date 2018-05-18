package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

public enum OverTimeRecordAtr {
	/**
	 * 早出残業
	 */
	PREOVERTIME(0),
	/**
	 * 通常残業
	 */
	REGULAROVERTIME(1),
	/**
	 * 早出残業・通常残業
	 */
	ALL(2);
	public final int value;
	
	OverTimeRecordAtr(int value){
		this.value = value;
	}
}
