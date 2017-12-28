package nts.uk.ctx.workflow.pub.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStatePub {
	
	public ApprovalRootContentExport getApprovalRoot(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID, Boolean isCreate);
	
	public void insertAppRootType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String historyID, String appID);
	
	/**
	 * 4.次の承認の番の承認者を取得する(メール通知用)
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param approvalPhaseStateNumber ドメインモデル「承認フェーズインスタンス」・順序
	 * @return
	 */
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue, GeneralDate appDate);
	
	/**
	 * 承認する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 承認フェーズ枠番
	 */
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate);
	
	/**
	 * 2.承認全体が完了したか
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate);
	
}
