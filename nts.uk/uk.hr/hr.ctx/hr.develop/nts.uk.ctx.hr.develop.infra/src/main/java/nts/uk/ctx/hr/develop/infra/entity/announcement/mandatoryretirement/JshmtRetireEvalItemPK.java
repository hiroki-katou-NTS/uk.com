package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class JshmtRetireEvalItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "HIST_ID")
	public String historyId;
	
	@Column(name = "EVALUATION_ITEM")
	public String evaluationItem;

}
