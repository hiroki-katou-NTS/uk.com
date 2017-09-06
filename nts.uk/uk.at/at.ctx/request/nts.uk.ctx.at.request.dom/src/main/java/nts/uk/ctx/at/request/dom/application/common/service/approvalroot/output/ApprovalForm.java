package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output;

import lombok.AllArgsConstructor;
/**
 * 承認形態
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ApprovalForm {
	/** 全員承認*/
	EVERYONE_APPROVED(1),
	/** 誰か一人*/
	SINGLE_APPROVED(2);
	public final int value;
}
