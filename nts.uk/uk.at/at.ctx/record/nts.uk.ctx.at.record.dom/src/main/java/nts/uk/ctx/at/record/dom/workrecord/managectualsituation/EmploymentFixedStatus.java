package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;

/**
 * 就業確定状態
 */
public enum EmploymentFixedStatus {
	
	CONFIRM(0, "CONFIRM", "確定"),	
	
	PENDING(1, "PENDING", "未確定");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	private EmploymentFixedStatus(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
}
