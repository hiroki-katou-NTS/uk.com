package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_RETIRE_PLAN_COURSE")
public class JshmtRetirePlanCourse extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RETIRE_PLAN_ID")
	public long retirePlanCourseId;
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "RETIRE_PLAN_CD")
	public String retirePlanCourseCode;
	
	@Column(name = "RETIRE_PLAN_NAME")
	public String retirePlanCourseName;
	
	@Column(name = "RETIRE_PLAN_CLASS")
	public Integer retirePlanCourseClass;
	
	@Column(name = "RETIREMENT_AGE")
	public Integer retirementAge;
	
	@Column(name = "DURATION_FLG")
	public Integer durationFlg;
	
	@Column(name = "J_OUT_RIYU_KBN1_ID")
	public long resignmentReason1Id;
	
	@Column(name = "NOT_USAGE_FLG")
	public Integer notUsageFlg;
	
	@Column(name = "USAGE_START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate usageStartDate;
	
	@Column(name = "USAGE_END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate usageEndDate;
	
	@Column(name = "APPLY_START_AGE")
	public Integer applicationEnableStartAge;
	
	@Column(name = "APPLY_END_AGE")
	public Integer applicationEnableEndAge;
	
	@Column(name = "APPLY_DUE_MONTH")
	public Integer endMonth;
	
	@Column(name = "APPLY_DUE_DATE")
	public Integer endDate;
	
	@Column(name = "RECONTRACT_EMP_CODE")
	public String recontractEmpCode;
	
	@Override
	public Object getKey() {
		return retirePlanCourseId;
	}

	public JshmtRetirePlanCourse(RetirePlanCource retirePlanCource) {
		this.retirePlanCourseId = retirePlanCource.getRetirePlanCourseId();
		this.companyId = retirePlanCource.getCompanyId();
		this.retirePlanCourseCode = retirePlanCource.getRetirePlanCourseCode();
		this.retirePlanCourseName = retirePlanCource.getRetirePlanCourseName();
		this.retirePlanCourseClass = retirePlanCource.getRetirePlanCourseClass().value;
		this.retirementAge = retirePlanCource.getRetirementAge().v();
		this.durationFlg = retirePlanCource.getDurationFlg().value;
		this.resignmentReason1Id = retirePlanCource.getResignmentReason1Id();
		this.notUsageFlg = retirePlanCource.isNotUsageFlg()?1:0;
		this.usageStartDate = retirePlanCource.getUsageStartDate();
		this.usageEndDate = retirePlanCource.getUsageEndDate();
		this.recontractEmpCode = retirePlanCource.getRecontractEmpCode().orElse(null);
		if(retirePlanCource.getPlanCourseApplyTerm().isPresent()) {
			this.applicationEnableStartAge = retirePlanCource.getPlanCourseApplyTerm().get().getApplicationEnableStartAge().v();
			this.applicationEnableEndAge = retirePlanCource.getPlanCourseApplyTerm().get().getApplicationEnableEndAge().v();
			this.endMonth = retirePlanCource.getPlanCourseApplyTerm().get().getEndMonth().value;
			this.endDate = retirePlanCource.getPlanCourseApplyTerm().get().getEndDate().value;
		}
	}

	public RetirePlanCource toDomain() {
		PlanCourseApplyTerm planCourseApplyTerm = null;
		if(this.applicationEnableStartAge != null && this.applicationEnableEndAge != null && this.endMonth != null && this.endDate != null) {
			planCourseApplyTerm = PlanCourseApplyTerm.createFromJavaType(this.applicationEnableStartAge, this.applicationEnableEndAge, this.endMonth, this.endDate);
		}
		return RetirePlanCource.createFromJavaType(
				this.companyId, 
				this.retirePlanCourseId, 
				this.retirePlanCourseCode, 
				this.retirePlanCourseName, 
				this.retirePlanCourseClass, 
				this.retirementAge, 
				this.durationFlg, 
				this.resignmentReason1Id, 
				this.notUsageFlg == 1, 
				this.usageStartDate, 
				this.usageEndDate, 
				Optional.ofNullable(planCourseApplyTerm),
				Optional.ofNullable(this.recontractEmpCode));
	}
}
