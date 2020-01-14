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
	/** 承認者グループ*/
	APPROVER_GROUP(1),
	/** 特定職場*/
	SPEC_WKP(2);
	public final int value;
}
