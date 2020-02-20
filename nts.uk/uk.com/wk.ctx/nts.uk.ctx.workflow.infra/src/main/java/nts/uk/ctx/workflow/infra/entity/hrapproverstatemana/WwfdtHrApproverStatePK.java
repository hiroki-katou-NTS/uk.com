package nts.uk.ctx.workflow.infra.entity.hrapproverstatemana;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WwfdtHrApproverStatePK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**ルートインスタンスID*/
	@Column(name="ID")
	public String rootStateID;
	/**承認フェーズNo*/
	@Column(name="PHASE_ORDER")
	public int phaseOrder;
	/**承認枠No*/
	@Column(name="APPROVER_ORDER")
	public int approverOrder;
	/**承認者*/
	@Column(name="APPROVER_ID")
	public String approverID;
}
