package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateRepository {
	
	public List<ApprovalRootState> findEmploymentApps(List<String> rootStateIDs);
	
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID);
	
	public void insert(ApprovalRootState approvalRootState);

	public void update(ApprovalRootState approvalRootState);
	
	public void delete(String rootStateID);
	
}
