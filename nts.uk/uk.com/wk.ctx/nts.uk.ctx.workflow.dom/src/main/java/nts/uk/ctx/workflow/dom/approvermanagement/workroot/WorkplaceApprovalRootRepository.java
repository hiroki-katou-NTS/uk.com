package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkplaceApprovalRootRepository {

	/**
	 * get All Workplace Approval Root
	 * 
	 * @param companyId
	 * @param workplaceId
	 * @return
	 */
	List<WorkplaceApprovalRoot> getAllWpApprovalRoot(String companyId, String workplaceId);

	/**
	 * get WpApprovalRoot
	 * 
	 * @param companyId
	 * @param approvalId
	 * @param workplaceId
	 * @param historyId
	 * @return 
	 */
	Optional<WorkplaceApprovalRoot> getWpApprovalRoot(String companyId, String approvalId, String workplaceId,
			String historyId);

	/**
	 * get Workplace Approval Root By End date
	 * 
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, GeneralDate endDate,
			Integer applicationType, int employmentRootAtr);

	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する
	 * @param companyID
	 * @param workplaceID
	 * @param date
	 * @param appType
	 * @param rootAtr
	 * @return
	 */
	Optional<WorkplaceApprovalRoot> findByBaseDate(String companyID, String workplaceID, GeneralDate date, ApplicationType appType, EmploymentRootAtr rootAtr);

	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通の)
	 * @param companyID
	 * @param workplaceID
	 * @param date
	 * @param appType
	 * @return
	 */
	Optional<WorkplaceApprovalRoot> findByBaseDateOfCommon(String companyID, String workplaceID, GeneralDate date);

	/**
	 * add Workplace Approval Root
	 * 
	 * @param wpAppRoot
	 */
	void addWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * add All Workplace Approval Root
	 * 
	 * @param wpAppRoot
	 */
	void addAllWpApprovalRoot(List<WorkplaceApprovalRoot> wpAppRoot);

	/**
	 * update Workplace Approval Root
	 * 
	 * @param wpAppRoot
	 */
	void updateWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * update All Workplace Approval Root
	 * 
	 * @param wpAppRoot
	 */
	void updateAllWpApprovalRoot(List<WorkplaceApprovalRoot> wpAppRoot);

	/**
	 * delete Person Approval Root
	 * 
	 * @param companyId
	 * @param workplaceId
	 * @param historyId
	 */
	void deleteWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId);
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(lấy dữ liệu domain 「職場別就業承認ルート」)
	 * @param companyID
	 * @param baseDate・期間．開始日 <= 基準日  ・期間．終了日 >= 基準日
	 * @return
	 */
	List<WorkplaceApprovalRoot> findAllByBaseDate(String companyID, GeneralDate baseDate);
	/**
	 * get Work place Approval Root By type
	 * @param companyId
	 * @param workplaceId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	List<WorkplaceApprovalRoot> getWpApprovalRootByType(String companyId, String workplaceId, Integer applicationType, int employmentRootAtr);
	/**
	 * getWpAppRootLast
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @return
	 */
	List<WorkplaceApprovalRoot> getWpAppRootLast(String companyId, String workplaceId,GeneralDate endDate);
	
	List<WorkplaceApprovalRoot> getWpAppRoot(String companyID, GeneralDate date, 
			Integer employmentRootAtr, Integer confirmRootAtr);
	
	List<WorkplaceApprovalRoot> findEmpByConfirm(String companyID, String workplaceID, ConfirmationRootType confirmType, GeneralDate date);
}
