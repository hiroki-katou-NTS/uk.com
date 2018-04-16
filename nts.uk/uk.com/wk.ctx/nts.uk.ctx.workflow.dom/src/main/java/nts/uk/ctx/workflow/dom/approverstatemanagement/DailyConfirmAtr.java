package nts.uk.ctx.workflow.dom.approverstatemanagement;
/**
 * 承認状況
 * @author Doan Duy Hung
 *
 */
public enum DailyConfirmAtr {
	/** 0:未承認 */
	UNAPPROVED(0,"未承認"),
	
	/** 1:承認中 */
	ON_APPROVED(1,"承認中 "),
	
	/** 2:承認済 */
	ALREADY_APPROVED(2,"承認済");

	public final int value;
	
	public final String name;
	
	DailyConfirmAtr(int type, String name) {
		this.value = type;
		this.name = name;
	}
}
