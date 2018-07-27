package nts.uk.ctx.at.record.pub.dailyperform.appreflect.overtime;

public enum OverTimeRecordPubAtr {
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
	
	OverTimeRecordPubAtr(int value){
		this.value = value;
	}
}
