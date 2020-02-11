package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author hoatt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtAppoverPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/**承認ID*/
	@Column(name = "APPROVAL_ID")
	public String approvalId;
	/**承認フェーズ順序*/
	@Column(name = "PHASE_ORDER")
	public int phaseOrder;
	/**承認者順序*/
	@Column(name = "APPROVER_ORDER")
	public int approverOrder;
}
