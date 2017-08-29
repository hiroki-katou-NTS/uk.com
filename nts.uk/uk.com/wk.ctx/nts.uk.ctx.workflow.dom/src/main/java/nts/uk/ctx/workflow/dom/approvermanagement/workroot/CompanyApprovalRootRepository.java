package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface CompanyApprovalRootRepository {

	/**
	 * get All Company Approval Root
	 * 
	 * @param companyId
	 * @return
	 */
	List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId);

	/**
	 * get ComApprovalRoot
	 * 
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 * @return
	 */
	Optional<CompanyApprovalRoot> getComApprovalRoot(String companyId, String approvalId, String historyId);

	/**
	 * get Company Approval Root By End date
	 * 
	 * @param companyId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType);

	/**
	 * add Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void addComApprovalRoot(CompanyApprovalRoot comAppRoot);

	/**
	 * update Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void updateComApprovalRoot(CompanyApprovalRoot comAppRoot);

	/**
	 * delete Company Approval Root
	 * 
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 */
	void deleteComApprovalRoot(String companyId, String approvalId, String historyId);

	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する 就業ルート区分(申請か、確認か、任意項目か)
	 * 
	 * @param cid
	 * @param baseDate
	 * @param appType
	 * @return
	 */
	List<CompanyApprovalRoot> findByBaseDate(String cid, Date baseDate, int appType);

	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(共通）
	 * 
	 * @param cid
	 * @param baseDate
	 * @return
	 */
	List<CompanyApprovalRoot> findByBaseDateOfCommon(String cid, Date baseDate);
}
