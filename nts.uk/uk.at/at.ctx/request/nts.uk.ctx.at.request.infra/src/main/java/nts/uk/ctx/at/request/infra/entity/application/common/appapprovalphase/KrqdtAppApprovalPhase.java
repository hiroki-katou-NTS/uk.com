package nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFramePK;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAccepted;
import nts.uk.ctx.at.request.infra.entity.application.common.approveaccepted.KafdtApproveAcceptedPK;
import nts.uk.ctx.at.request.infra.repository.application.common.approvalframe.JpaApprovalFrameRepository;
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
	
	@Column(name = "APP_ID")
	public String appID;
	
	@Column(name = "APPROVAL_FORM")
	public int approvalForm;
	
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@Column(name = "APPROVAL_ATR")
	public int approvalATR;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
        @JoinColumn(name="APP_ID", referencedColumnName="APP_ID", insertable = false, updatable = false)
    })
	public KafdtApplication application;
	
	@OneToMany(mappedBy="approvalPhase", cascade = CascadeType.ALL)
	public List<KrqdtApprovalFrame> approvalFrames;
	
	public KrqdtAppApprovalPhase(KrqdtAppApprovalPhasePK krqdtAppApprovalPhasePK, String appID, int approvalForm,
			int dispOrder, int approvalATR, List<KrqdtApprovalFrame> approvalFrames) {
		super();
		this.krqdtAppApprovalPhasePK = krqdtAppApprovalPhasePK;
		this.appID = appID;
		this.approvalForm = approvalForm;
		this.dispOrder = dispOrder;
		this.approvalATR = approvalATR;
		this.approvalFrames = approvalFrames;
	}
	
	@Override
	protected Object getKey() {
		return krqdtAppApprovalPhasePK;
	}

	public static KrqdtAppApprovalPhase toEntity(AppApprovalPhase domain) {
		List<KrqdtApprovalFrame> approvalFrames = domain.getListFrame().stream()
				.map(x -> KrqdtApprovalFrame.toEntity(x, domain.getPhaseID())).collect(Collectors.toList());
		
		return new KrqdtAppApprovalPhase (
			new KrqdtAppApprovalPhasePK(domain.getCompanyID(), domain.getPhaseID()),
			domain.getAppID(),
			domain.getApprovalForm().value,
			domain.getDispOrder(),
			domain.getApprovalATR().value,
			approvalFrames
		);
	}

	public AppApprovalPhase toDomain() {
		return AppApprovalPhase.createFromJavaType(
			this.krqdtAppApprovalPhasePK.companyID,
			this.appID,
			this.krqdtAppApprovalPhasePK.phaseID,
			Integer.valueOf(this.approvalForm).intValue(),
			Integer.valueOf(this.dispOrder).intValue(),
			Integer.valueOf(this.approvalATR).intValue(),
			this.approvalFrames.stream().map(c -> c.toDomain()).collect(Collectors.toList())
		);
	}
	
}