package nts.uk.ctx.hr.shared.dom.approval.rootstate;

public enum ApprovalFormHrExport {
	/** 全員承認*/
	EVERYONE_APPROVED(1, "全員承認"),
	/** 誰か一人*/
	SINGLE_APPROVED(2, "誰か一人");
	
	public int value;
	public String name;
	
	private ApprovalFormHrExport(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
