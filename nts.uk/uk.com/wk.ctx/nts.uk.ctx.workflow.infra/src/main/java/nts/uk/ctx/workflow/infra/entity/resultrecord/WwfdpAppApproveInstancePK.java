package nts.uk.ctx.workflow.infra.entity.resultrecord;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WwfdpAppApproveInstancePK {
	
	@Column(name="ROOT_ID")
	private String rootID;
	
	@Column(name="PHASE_ORDER")
	private Integer phaseOrder;
	
	@Column(name="FRAME_ORDER")
	private Integer frameOrder;
	
	@Column(name="APPROVER_CHILD_ID")
	private String approverChildID;
}
