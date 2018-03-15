package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class AnnualLeaveEmpBasicInfo extends AggregateRoot{
	
	private String employeeId;
	
	private Integer workingDaysPerYear;
	
	private Integer workingDayBeforeIntroduction;
	
	private AnnualLeaveGrantRule grantRule;
	
}
