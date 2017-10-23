package nts.uk.ctx.at.request.infra.entity.application.common.approvalframe;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAcceptedPK;
import nts.uk.ctx.at.request.infra.repository.application.common.approveaccepted.JpaApproveAcceptedRepository;
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
	
	@Column(name = "PHASE_ID")
	public String phaseID;
	
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
        @JoinColumn(name="PHASE_ID", referencedColumnName="PHASE_ID", insertable = false, updatable = false)
    })
	public KrqdtAppApprovalPhase approvalPhase;
	
	@OneToMany(mappedBy="approvalFrame", cascade = CascadeType.ALL)
	public List<KafdtApproveAccepted> kafdtApproveAccepteds ;
	
	public KrqdtApprovalFrame(KrqdtApprovalFramePK krqdtApprovalFramePK, String phaseID, int dispOrder,
			List<KafdtApproveAccepted> kafdtApproveAccepteds) {
		super();
		this.krqdtApprovalFramePK = krqdtApprovalFramePK;
		this.phaseID = phaseID;
		this.dispOrder = dispOrder;
		this.kafdtApproveAccepteds = kafdtApproveAccepteds;
	}
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return krqdtApprovalFramePK;
	}
	
	public static KrqdtApprovalFrame toEntity(ApprovalFrame domain, String phaseID) {
		List<KafdtApproveAccepted> kafdtApproveAccepteds =  domain.getListApproveAccepted()
				.stream().map(c -> KafdtApproveAccepted.toEntity(c, domain.getFrameID())).collect(Collectors.toList());
		return new KrqdtApprovalFrame(
				new KrqdtApprovalFramePK(domain.getCompanyID(), domain.getFrameID()),
				phaseID,
				domain.getDispOrder(),
				kafdtApproveAccepteds);
	}

	public ApprovalFrame toDomain() {
		return ApprovalFrame.createFromJavaType(
				this.krqdtApprovalFramePK.companyID,
				this.krqdtApprovalFramePK.frameID, 
				this.dispOrder, 
				this.kafdtApproveAccepteds.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}

	
}
