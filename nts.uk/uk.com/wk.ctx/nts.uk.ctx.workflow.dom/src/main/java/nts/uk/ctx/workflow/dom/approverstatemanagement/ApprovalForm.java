package nts.uk.ctx.workflow.dom.approverstatemanagement;
/**
 * 承認形態
 * @author Doan Duy Hung
 *
 */
public enum ApprovalForm {
	
	/** 1: 全員承認 */
	EVERYONEAPPROVED(1, "全員承認"),
	
	/** 2: 誰か一人 */
	SINGLEAPPROVED(2, "誰か一人");
	
	public int value ; 
	
	public String name;
	
	ApprovalForm(int type, String name) {
		this.value = type;
		this.name = name;
	}
}
