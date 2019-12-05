package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "JSHMT_RETIRE_EVAL_ITEM")
public class JshmtRetireEvalItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtRetireEvalItemPK pkJshmtRetireEvalItem;
	
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
		return pkJshmtRetireEvalItem;
	}

	
}
