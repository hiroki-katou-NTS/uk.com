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
	 * ドメインモデル「職場別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param appType
	 * @return
	 */
	List<WorkplaceApprovalRoot> findByBaseDate(String cid, String workplaceId, GeneralDate baseDate, int appType);

	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通の)
	 * 
	 * @param cid
	 * @param workplaceId
	 * @param baseDate
	 * @return WorkplaceApprovalRoots
	 */
	List<WorkplaceApprovalRoot> findByBaseDateOfCommon(String cid, String workplaceId, GeneralDate baseDate);

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
}
