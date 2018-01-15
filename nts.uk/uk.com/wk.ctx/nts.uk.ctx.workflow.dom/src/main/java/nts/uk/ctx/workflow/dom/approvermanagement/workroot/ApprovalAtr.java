package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
/**
 * 承認者指定区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ApprovalAtr {
	/** 個人*/
	PERSON(0),
	/** 職位*/
	JOB_TITLE(1);
	public final int value;
}
