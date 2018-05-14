package nts.uk.ctx.at.record.dom.workrecord.managectualsituation;
/**
 * 承認状況
 */
public enum ApprovalStatus {
	
	APPROVAL(0, "APPROVAL", "承認"),	
	
	UNAPPROVAL(1, "UNAPPROVAL", "未承認");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	private ApprovalStatus(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
}
