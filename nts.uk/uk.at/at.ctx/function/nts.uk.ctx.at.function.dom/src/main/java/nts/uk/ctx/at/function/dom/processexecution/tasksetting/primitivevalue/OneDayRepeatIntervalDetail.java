package nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue;

/**
 * 繰り返し間隔時間
 */
public enum OneDayRepeatIntervalDetail {
	MIN_1(0,"1分"),
	MIN_5(1,"5分"),
	MIN_10(2,"10分"),
	MIN_15(3,"15分"),
	MIN_20(4,"20分"),
	MIN_30(5,"30分"),
	MIN_60(6,"60分");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private OneDayRepeatIntervalDetail(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
