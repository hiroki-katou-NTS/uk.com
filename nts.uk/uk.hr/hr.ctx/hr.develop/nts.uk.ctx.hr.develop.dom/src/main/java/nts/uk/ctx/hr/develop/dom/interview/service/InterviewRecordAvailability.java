package nts.uk.ctx.hr.develop.dom.interview.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * the type of the object 面談記録有無リスト = {社員IDリスト．社員ID、有無}
 * @author lamvt
 *
 */
public class InterviewRecordAvailability {
	/** 社員ID **/
	private String employeeID ;
	/** 有無 **/
	private boolean isPresence;
	public InterviewRecordAvailability(String employeeID, boolean isPresence) {
		super();
		this.employeeID = employeeID;
		this.isPresence = isPresence;
	}
	
}
