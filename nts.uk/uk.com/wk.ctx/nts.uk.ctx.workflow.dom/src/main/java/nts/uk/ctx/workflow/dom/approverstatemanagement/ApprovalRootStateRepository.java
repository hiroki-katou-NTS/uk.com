package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateRepository {
	/**
	 * @param startDate
	 * @param endDate
	 * @return List<ApprovalRootState>
	 */
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDate(GeneralDate startDate, GeneralDate endDate,String approverID,Integer rootType);
	
	public List<ApprovalRootState> findEmploymentApps(List<String> rootStateIDs);
	
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID);
	
	public void insert(ApprovalRootState approvalRootState);

	public void update(ApprovalRootState approvalRootState);
	
	public void delete(String rootStateID);
	
}
