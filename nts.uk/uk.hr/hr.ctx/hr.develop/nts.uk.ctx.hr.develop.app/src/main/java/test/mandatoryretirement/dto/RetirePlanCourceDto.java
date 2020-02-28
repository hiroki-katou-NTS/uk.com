package test.mandatoryretirement.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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
	
	public RetirePlanCource toDomain() {
		return RetirePlanCource.createFromJavaType(
				this.companyId,
				this.retirePlanCourseId,
				this.retirePlanCourseCode,
				this.retirePlanCourseName,
				this.retirePlanCourseClass,
				this.retirementAge,
				this.durationFlg,
				this.resignmentReason1Id,
				this.notUsageFlg,
				this.usageStartDate,
				this.usageEndDate,
				Optional.ofNullable(this.planCourseApplyTerm == null?null: this.planCourseApplyTerm.toDomain()),
				Optional.ofNullable(this.recontractEmpCode)
				);
	}
}
