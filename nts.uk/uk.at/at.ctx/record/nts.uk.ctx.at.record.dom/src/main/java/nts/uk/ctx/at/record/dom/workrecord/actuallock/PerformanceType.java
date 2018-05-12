package nts.uk.ctx.at.record.dom.workrecord.actuallock;

public enum PerformanceType {
	
	DAILY(0, "DAILY", "日別"),
	
	
	MONTHLY(1, "MONTHLY", "月別");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	private PerformanceType(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
}
