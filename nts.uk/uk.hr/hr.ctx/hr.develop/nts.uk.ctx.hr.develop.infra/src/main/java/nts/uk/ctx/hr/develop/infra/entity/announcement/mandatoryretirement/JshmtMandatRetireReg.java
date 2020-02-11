package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_MANDAT_RETIRE_REG")
public class JshmtMandatRetireReg extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "CID")
	public String companyId;

	@Column(name = "REACH_AGE_TERM")
	public Integer reachedAgeTerm;
	
	@Column(name = "PUBLIC_TERM")
	public Integer calculationTerm;
	
	@Column(name = "PUBLIC_SETTING_NUM")
	public Integer dateSettingNum;
	
	@Column(name = "PUBLIC_SETTING_DATE")
	public Integer dateSettingDate;
	
	@Column(name = "RETIRE_TERM")
	public Integer retireDateTerm;
	
	@Column(name = "RERIRE_SETTING_DATE")
	public Integer retireDateSettingDate;
	
	@Column(name = "APPLY_FLG")
	public Integer planCourseApplyFlg;
	
	@Column(name = "APPLY_START_AGE")
	public Integer applicationEnableStartAge;
	
	@Column(name = "APPLY_END_AGE")
	public Integer applicationEnableEndAge;
	
	@Column(name = "APPLY_DUE_MONTH")
	public Integer endMonth;
	
	@Column(name = "APPLY_DUE_DATE")
	public Integer endDate;
	
	@Override
	public Object getKey() {
		return historyId;
	}
	
	@OneToMany(mappedBy = "referEvaluationTerm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JSHMT_RETIRE_EVAL_ITEM")
	public List<JshmtRetireEvalItem> referEvaluationTerm;
	
	@OneToMany(mappedBy = "mandatoryRetireTerm", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JSHMT_RETIRE_TERM")
	public List<JshmtRetireTerm> mandatoryRetireTerm;

	public JshmtMandatRetireReg(MandatoryRetirementRegulation mandatoryRetirementRegulation) {
		super();
		this.companyId = mandatoryRetirementRegulation.getCompanyId();
		this.historyId = mandatoryRetirementRegulation.getHistoryId();
		this.reachedAgeTerm = mandatoryRetirementRegulation.getReachedAgeTerm().value;
		this.calculationTerm = mandatoryRetirementRegulation.getPublicTerm().getCalculationTerm().value;
		this.dateSettingNum = mandatoryRetirementRegulation.getPublicTerm().getDateSettingNum();
		this.dateSettingDate = mandatoryRetirementRegulation.getPublicTerm().getDateSettingDate().isPresent()?mandatoryRetirementRegulation.getPublicTerm().getDateSettingDate().get().value : null; 
		this.retireDateTerm = mandatoryRetirementRegulation.getRetireDateTerm().getRetireDateTerm().value;
		this.retireDateSettingDate = mandatoryRetirementRegulation.getRetireDateTerm().getRetireDateSettingDate().isPresent()?mandatoryRetirementRegulation.getRetireDateTerm().getRetireDateSettingDate().get().value:null;
		this.planCourseApplyFlg = mandatoryRetirementRegulation.isPlanCourseApplyFlg() ? 1 : 0;
		this.applicationEnableStartAge = mandatoryRetirementRegulation.getPlanCourseApplyTerm().getApplicationEnableStartAge().v();
		this.applicationEnableEndAge = mandatoryRetirementRegulation.getPlanCourseApplyTerm().getApplicationEnableEndAge().v();
		this.endMonth = mandatoryRetirementRegulation.getPlanCourseApplyTerm().getEndMonth().value;
		this.endDate = mandatoryRetirementRegulation.getPlanCourseApplyTerm().getEndDate().value;
		this.referEvaluationTerm =  mandatoryRetirementRegulation.getReferEvaluationTerm().stream().map(
				c-> new JshmtRetireEvalItem(mandatoryRetirementRegulation.getCompanyId(), mandatoryRetirementRegulation.getHistoryId(), c)).collect(Collectors.toList());
		this.mandatoryRetireTerm = mandatoryRetirementRegulation.getMandatoryRetireTerm().stream().map(
				c -> new JshmtRetireTerm(mandatoryRetirementRegulation.getCompanyId(), mandatoryRetirementRegulation.getHistoryId(), c)).collect(Collectors.toList());
	}

	public MandatoryRetirementRegulation toDomain() {
		return MandatoryRetirementRegulation.createFromJavaType(
				this.companyId, 
				this.historyId, 
				this.reachedAgeTerm, 
				DateCaculationTerm.createFromJavaType(this.calculationTerm, this.dateSettingNum, this.dateSettingDate), 
				RetireDateTerm.createFromJavaType(this.retireDateTerm, this.retireDateSettingDate), 
				this.planCourseApplyFlg == 1, 
				this.mandatoryRetireTerm.stream().map(c -> c.toDomain()).collect(Collectors.toList()), 
				this.referEvaluationTerm.stream().map(c -> c.toDomain()).collect(Collectors.toList()), 
				PlanCourseApplyTerm.createFromJavaType(this.applicationEnableStartAge, this.applicationEnableEndAge, this.endMonth, this.endDate));
	}
}
