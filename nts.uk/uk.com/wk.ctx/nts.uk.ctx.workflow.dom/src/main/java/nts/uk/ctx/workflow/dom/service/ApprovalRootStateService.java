package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStateService {
	
	public void insertAppRootType(String companyID, String employeeID, String targetType, 
			GeneralDate appDate, String appID, Integer rootType, GeneralDate baseDate);
	
	public void delete(String rootStateID, Integer rootType); 
	
	/**
	 * 対象者と期間から承認ルートインスタンスを取得する
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @param rootType
	 * @return
	 */
	public List<ApprovalRootState> getByPeriod(String employeeID, GeneralDate startDate, GeneralDate endDate, Integer rootType);
	
	public void insertFromCache(String companyID, String appID, GeneralDate date, String employeeID, List<ApprovalPhaseState> listApprovalPhaseState);
	
}
