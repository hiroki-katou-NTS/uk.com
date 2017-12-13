package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.Optional;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateRepository {
	
	public Optional<ApprovalRootState> findEmploymentApp(String companyID, String rootStateID);

	public void update(ApprovalRootState approvalRootState);
	
}
