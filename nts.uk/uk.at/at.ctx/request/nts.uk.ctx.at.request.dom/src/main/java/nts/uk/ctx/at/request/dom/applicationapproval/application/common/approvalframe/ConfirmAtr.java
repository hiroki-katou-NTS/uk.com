package nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe;
/**
 * 
 * @author hieult
 *
 */
public enum ConfirmAtr {

	/** 0: 使用しない */
	USEATR_NOTUSE(0),
	/** 1: 使用する */
	USEATR_USE(1);

	public int value;

	ConfirmAtr(int type) {
		this.value = type;
	}
}
