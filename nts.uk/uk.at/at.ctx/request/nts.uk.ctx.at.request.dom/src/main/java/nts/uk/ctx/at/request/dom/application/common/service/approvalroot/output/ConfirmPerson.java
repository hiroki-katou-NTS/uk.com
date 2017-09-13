package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.output;

import lombok.AllArgsConstructor;
/**
 * 確定者
 * @author hoatt
 *
 */
@AllArgsConstructor
public enum ConfirmPerson {
	/** 未確定*/
	NOT_CONFIRM(0),
	/** 確定*/
	CONFIRM(1);
	public final int value;
}
