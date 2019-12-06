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
	
	@Column(name = "CID")
	public String cId;
	
	@Column(name = "EVALUATION_ITEM")
	public Integer evaluationItem;
	
	@Column(name = "USAGE_FLG")
	public Integer usageFlg;
	
	@Column(name = "DISPLAY_NUM")
	public Integer displayNum;
	
	@Column(name = "PASS_VALUE")
	public Integer passValue;
	
	@Override
	public Object getKey() {
		return pkJshmtRetireEvalItem;
	}

	
}
