package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

public enum ApprovalFormImport {
	/** 全員承認*/
	EVERYONE_APPROVED(1, "全員承認"),
	/** 誰か一人*/
	SINGLE_APPROVED(2, "誰か一人");
	
	public int value;
	public String name;
	
	private ApprovalFormImport(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
