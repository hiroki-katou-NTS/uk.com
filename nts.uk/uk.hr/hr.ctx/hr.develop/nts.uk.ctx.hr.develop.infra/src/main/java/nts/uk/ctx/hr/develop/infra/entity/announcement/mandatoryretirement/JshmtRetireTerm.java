package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_RETIRE_TERM")
public class JshmtRetireTerm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtRetireTermPK pkJshmtRetireEvalItem;
	
	@Column(name = "CID")
	public String cId;
	
	@Column(name = "USAGE_FLG")
	public Integer usageFlg;
	
	@Column(name = "RETIRE_PLAN_ID")
	public String retirePlanCourseId;
	
	@Override
	public Object getKey() {
		return pkJshmtRetireEvalItem;
	}

	
	
}
