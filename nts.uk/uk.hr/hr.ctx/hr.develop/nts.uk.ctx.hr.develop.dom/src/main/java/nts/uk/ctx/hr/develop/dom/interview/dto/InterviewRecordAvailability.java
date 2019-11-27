package nts.uk.ctx.hr.develop.dom.interview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewRecordAvailability {
	/** 社員ID **/
	private String employeeID ;
	/** 有無 **/
	private boolean presence;
	public InterviewRecordAvailability(String employeeID, boolean presence) {
		super();
		this.employeeID = employeeID;
		this.presence = presence;
	}
	
}
