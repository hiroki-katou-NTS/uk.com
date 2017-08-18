package nts.uk.ctx.at.request.dom.application.common.appapprovalphase;
/**
 * 
 * @author hieult
 *
 */
public enum ApprovalATR {

	/** 0:未承認 */
	UNAPPROVED(0),
	/** 1:承認済 */
	APPROVED(1),
	/** 2:否認 */
	DENIAL(2),
	/** 3:差し戻し */
	REMAND(3);

	public int value;

	ApprovalATR(int type) {
		this.value = type;
	}
}
