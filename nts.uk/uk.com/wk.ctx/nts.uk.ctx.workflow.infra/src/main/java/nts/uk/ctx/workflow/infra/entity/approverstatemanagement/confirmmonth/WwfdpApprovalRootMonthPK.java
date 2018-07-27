package nts.uk.ctx.workflow.infra.entity.approverstatemanagement.confirmmonth;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class WwfdpApprovalRootMonthPK {
	
	@Column(name="ROOT_STATE_ID")
	public String rootStateID;
	
}
