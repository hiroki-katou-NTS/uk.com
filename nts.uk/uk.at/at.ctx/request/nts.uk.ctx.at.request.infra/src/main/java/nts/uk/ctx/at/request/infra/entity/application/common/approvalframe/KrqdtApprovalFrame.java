package nts.uk.ctx.at.request.infra.entity.application.common.approvalframe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author hieult
 *
 */
@Entity
@Table(name = "KRQDT_APPROVAL_FRAME")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtApprovalFrame extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtApprovalFramePK krqdtApprovalFramePK;
	
	
	@Column(name = "APPROVAL_ATR")
	public int approvalATR;

	@Column(name = "CONFIRM_ATR")
	public int confirmATR;
	
	
	
	@Column(name="APPROVAL_DATE")
	public GeneralDate approvalDate;
	
	@Column(name="REASON")
	public String reason;
	
	@Column(name="REPRESENTER_SID")
	public String representerSID; 
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krqdtApprovalFramePK;
	}

}
