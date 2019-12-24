package test.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;

@AllArgsConstructor
@Getter
public class RetirePlanCourceDto {

	private String companyId;
	
	private long retirePlanCourseId;
	
	private String retirePlanCourseCode;
	
	private String retirePlanCourseName;
	
	private Integer retirePlanCourseClass;
	
	private Integer retirementAge;
	
	private Integer durationFlg;
	
	private Long resignmentReason1Id;
	
	private Boolean notUsageFlg;
	
	private GeneralDate usageStartDate;
	
	private GeneralDate usageEndDate;
	
	private PlanCourseApplyTermDto planCourseApplyTerm;
	
	private String recontractEmpCode;

	public RetirePlanCourceDto(RetirePlanCource domain) {
		super();
		this.companyId = domain.getCompanyId();
		this.retirePlanCourseId = domain.getRetirePlanCourseId();
		this.retirePlanCourseCode = domain.getRetirePlanCourseCode();
		this.retirePlanCourseName = domain.getRetirePlanCourseName();
		this.retirePlanCourseClass = domain.getRetirePlanCourseClass().value;
		this.retirementAge = domain.getRetirementAge().v();
		this.durationFlg = domain.getDurationFlg().value;
		this.resignmentReason1Id = domain.getResignmentReason1Id();
		this.notUsageFlg = domain.isNotUsageFlg();
		this.usageStartDate = domain.getUsageStartDate();
		this.usageEndDate = domain.getUsageEndDate();
		this.planCourseApplyTerm = domain.getPlanCourseApplyTerm().isPresent()?new PlanCourseApplyTermDto(domain.getPlanCourseApplyTerm().get()):null;
		this.recontractEmpCode = domain.getRecontractEmpCode().orElse(null);
	}
}
