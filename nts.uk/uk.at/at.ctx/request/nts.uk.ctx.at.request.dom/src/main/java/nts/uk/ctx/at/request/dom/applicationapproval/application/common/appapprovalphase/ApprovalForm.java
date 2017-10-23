package nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase;
/**
 * 
 * @author hieult
 *
 */
public enum ApprovalForm {
	/** 1: 全員承認 */
	EVERYONEAPPROVED(1),
	/** 2: 誰か一人 */
	SINGLEAPPROVED(2);
	
	public int value ; 
	
	ApprovalForm(int type) {
		this.value = type;
	}

}
