package nts.uk.ctx.hr.develop.dom.interview;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
@Getter
public class SubInterviewer extends DomainObject{

	private EmployeeId subInterviewerSid;

	public SubInterviewer(EmployeeId subInterviewerSid) {
		super();
		this.subInterviewerSid = subInterviewerSid;
	}
	
	
}
