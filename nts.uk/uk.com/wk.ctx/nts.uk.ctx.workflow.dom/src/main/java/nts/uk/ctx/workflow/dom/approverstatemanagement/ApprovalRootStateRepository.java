package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.Optional;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateRepository {
	
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID);
	
	public void insert(ApprovalRootState approvalRootState);

	public void update(ApprovalRootState approvalRootState);
	
	public void delete(String rootStateID);
	
}
