package nts.uk.ctx.workflow.infra.entity.approverstatemanagement;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="WWFDT_APPROVAL_FRAME")
public class WwfdtApprovalFrame extends UkJpaEntity {
	
	@EmbeddedId
	public WwfdpApprovalFramePK wwfdpApprovalFramePK;
	
	@Column(name="APPROVAL_ATR")
	public Integer approvalAtr;
	
	@Column(name="CONFIRM_ATR")
	public Integer confirmAtr;
	
	@Column(name="APPROVER_ID")
	public String approverID;
	
	@Column(name="REPRESENTER_ID")
	public String representerID;
	
	@Column(name="APPROVAL_DATE")
	public GeneralDate approvalDate;
	
	@Column(name="APPROVAL_REASON")
	public String approvalReason;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_STATE_ID",referencedColumnName="ROOT_STATE_ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER")
	})
	private WwfdtApprovalPhaseState wwfdtApprovalPhaseState;
	
	@OneToMany(targetEntity=WwfdtApproverState.class, cascade = CascadeType.ALL, mappedBy = "wwfdtApprovalFrame", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "WWFDT_APPROVER_STATE")
	public List<WwfdtApproverState> listWwfdtApproverState;

	@Override
	protected Object getKey() {
		return wwfdpApprovalFramePK;
	}
	
}
