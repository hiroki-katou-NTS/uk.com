package nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author hieult
 *
 */
@Entity
@Table(name = "KRQDT_APP_APPROVAL_PHASE")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppApprovalPhase extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtAppApprovalPhasePK krqdtAppApprovalPhasePK;
	
	@Column(name = "APPROVAL_FORM")
	public String approvalForm;
	
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@Column(name = "APPROVAL_ATR")
	public String approvalATR;

	@Override
	protected Object getKey() {
		return krqdtAppApprovalPhasePK;
	}

}
