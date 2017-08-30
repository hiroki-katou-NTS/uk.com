package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
/**
 * 区分
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ApprovalAtr {
	/** 未確定*/
	NOT_CONFIRM(0),
	/** 確定*/
	CONFIRM(1);
	public final int value;
}
