package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ApprovalForm {
	/* 全員承認*/
	EVERYONE_APPROVED(1),
	/* 誰か一人*/
	SINGLE_APPROVED(2);
	public final int value;
}
