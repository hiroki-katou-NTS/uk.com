package test.mandatoryretirement.dto;

import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;

@Getter
public class PlanCourseApplyTermDto {

	private Integer applicationEnableStartAge;
	
	private Integer applicationEnableEndAge;
	
	private Integer endMonth;
	
	private Integer endDate;

	public PlanCourseApplyTermDto(PlanCourseApplyTerm domain) {
		super();
		this.applicationEnableStartAge = domain.getApplicationEnableStartAge().v();
		this.applicationEnableEndAge = domain.getApplicationEnableEndAge().v();
		this.endMonth = domain.getEndMonth().value;
		this.endDate = domain.getEndDate().value;
	}
}
