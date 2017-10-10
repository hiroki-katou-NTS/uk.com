package nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted;

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
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KRQDT_APPROVE_ACCEPTED")
@AllArgsConstructor
@NoArgsConstructor
public class KafdtApproveAccepted extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KafdtApproveAcceptedPK kafdtApproveAcceptedPK;
	
	@Column(name = "FRAME_ID")
	public String frameID;
	
	@Column(name = "APPROVER_SID")
	public String approverSID;
	
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
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
        @JoinColumn(name="FRAME_ID", referencedColumnName="FRAME_ID", insertable = false, updatable = false)
    })
	public KrqdtApprovalFrame approvalFrame;
	
	@Override
	protected Object getKey() {
		return kafdtApproveAcceptedPK;
	}
}
