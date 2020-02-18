package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WwfdpApproverStatePK  implements Serializable{

	private static final long serialVersionUID = 1L;
	/**ルートインスタンスID*/
	@Column(name="ROOT_STATE_ID")
	public String rootStateID;
	/**承認フェーズ順序*/
	@Column(name="PHASE_ORDER")
	public Integer phaseOrder;
	/**承認枠順序*/
	@Column(name="APPROVER_ORDER")
	public Integer approverOrder;
	/**承認者*/
	@Column(name="APPROVER_ID")
	public String approverId;
}
