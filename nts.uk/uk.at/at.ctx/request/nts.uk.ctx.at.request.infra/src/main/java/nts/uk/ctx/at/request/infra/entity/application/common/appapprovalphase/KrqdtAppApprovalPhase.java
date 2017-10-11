package nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase;

import java.io.Serializable;
import java.util.List;

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
import nts.uk.ctx.at.request.infra.entity.application.common.KafdtApplication;
import nts.uk.ctx.at.request.infra.entity.application.common.approvalframe.KrqdtApprovalFrame;
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



	

}
