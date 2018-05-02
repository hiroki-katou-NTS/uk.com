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
	
	public Optional<ApprovalRootState> findByID(String rootStateID);
	/**
	 * @param startDate
	 * @param endDate
	 * @return List<ApprovalRootState>
	 */
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDate(GeneralDate startDate, GeneralDate endDate,String approverID,Integer rootType);
	
	/**
	 * @param startDate
	 * @param endDate
	 * @param approverID
	 * @param rootType
	 * @return
	 */
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateNew(GeneralDate startDate, GeneralDate endDate,Integer rootType);
	/**
	 * @param startDate
	 * @param endDate
	 * @return List<ApprovalRootState>
	 */
	public List<ApprovalRootState> findEmployeeAppByApprovalRecordDateAndNoRootType(GeneralDate startDate, GeneralDate endDate,String approverID);
	
	/** 
	 * 対象者と期間から承認ルートインスタンスを取得する
	 * @param startDate
	 * @param endDate
	 * @param employeeID
	 * @param rootType
	 * @return
	 */
	public List<ApprovalRootState> findAppByEmployeeIDRecordDate(GeneralDate startDate, GeneralDate endDate,String employeeID,Integer rootType);
	/**
	 * 対象者と期間から承認ルートインスタンスを取得する(for List EmployeeID)
	 * @param startDate
	 * @param endDate
	 * @param employeeID
	 * @param rootType
	 * @return
	 */
	public List<ApprovalRootState> findAppByListEmployeeIDRecordDate(GeneralDate startDate, GeneralDate endDate,List<String> employeeID,Integer rootType);
	
	/**
	 * 対象者リストと日付リストから承認ルートインスタンスを取得する
	 * @param approvalRecordDates
	 * @param employeeIDs
	 * @param rootType
	 * @return
	 */
	public List<ApprovalRootState> findAppByListEmployeeIDAndListRecordDate(List<GeneralDate> approvalRecordDates,List<String> employeeIDs,Integer rootType);
	
	public List<ApprovalRootState> findEmploymentApps(List<String> rootStateIDs);
	
	public Optional<ApprovalRootState> findEmploymentApp(String rootStateID);
	
	public void insert(ApprovalRootState approvalRootState);

	public void update(ApprovalRootState approvalRootState);
	
	public void delete(String rootStateID);
	
	public List<ApprovalRootState> getRootStateByDateAndType(GeneralDate date, Integer rootType);
	
}
