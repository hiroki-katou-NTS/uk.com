package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
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
	
	@Column(name = "USAGE_FLG")
	public Integer usageFlg;
	
	@Column(name = "DISPLAY_NUM")
	public Integer displayNum;
	
	@Column(name = "PASS_VALUE")
	public String passValue;
	
	@Override
	public Object getKey() {
		return pkJshmtRetireEvalItem;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public JshmtMandatRetireReg referEvaluationTerm;
	
	public JshmtRetireEvalItem(String cId, String historyId, ReferEvaluationItem referEvaluationItem) {
		super();
		this.pkJshmtRetireEvalItem = new JshmtRetireEvalItemPK(historyId, referEvaluationItem.getEvaluationItem().value);
		this.cId = cId;
		this.usageFlg = referEvaluationItem.isUsageFlg()?1:0;
		this.displayNum = referEvaluationItem.getDisplayNum();
		this.passValue = referEvaluationItem.getPassValue().isPresent()?referEvaluationItem.getPassValue().get():null;
	}

	public ReferEvaluationItem toDomain() {
		return ReferEvaluationItem.createFromJavaType(this.pkJshmtRetireEvalItem.evaluationItem, this.usageFlg == 1, this.displayNum, this.passValue);
	}
	
}
