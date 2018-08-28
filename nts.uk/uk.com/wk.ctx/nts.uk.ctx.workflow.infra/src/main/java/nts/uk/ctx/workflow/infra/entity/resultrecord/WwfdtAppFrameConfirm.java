package nts.uk.ctx.workflow.infra.entity.resultrecord;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="WWFDT_APP_FRAME_CONFIRM")
public class WwfdtAppFrameConfirm extends UkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppFrameConfirmPK pk;
	
	@Column(name="APPROVER_ID")
	private String approverID;
	
	@Column(name="REPRESENTER_ID")
	private String representerID;
	
	@Column(name="APPROVAL_DATE")
	private GeneralDate approvalDate;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER")
	})
	private WwfdtAppPhaseConfirm wwfdtAppPhaseConfirm;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
}
