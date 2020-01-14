package nts.uk.ctx.workflow.pub.service.export;

public enum ApprovalFormExport {
	/** 全員承認*/
	EVERYONE_APPROVED(1, "全員承認"),
	/** 誰か一人*/
	SINGLE_APPROVED(2, "誰か一人");
	
	public int value;
	public String name;
	
	private ApprovalFormExport(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
