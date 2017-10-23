package nts.uk.ctx.at.request.dom.application.common.appapprovalphase;
/**
 * 
 * @author hieult
 *
 */
public enum ApprovalAtr {

	/** 0:未承認 */
	UNAPPROVED(0,"未承認"),
	/** 1:承認済 */
	APPROVED(1,"承認済"),
	/** 2:否認 */
	DENIAL(2,"否認 "),
	/** 3:差し戻し */
	REMAND(3,"差し戻し");

	public int value;
	public String nameId;
	ApprovalAtr(int type,String nameId) {
		this.value = type;
		this.nameId = nameId;
	}
}
