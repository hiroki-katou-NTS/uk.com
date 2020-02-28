package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	public PlanCourseApplyTerm toDomain() {
		return PlanCourseApplyTerm.createFromJavaType(
				this.applicationEnableStartAge, 
				this.applicationEnableEndAge, 
				this.endMonth, 
				this.endDate);
	}
	
}
