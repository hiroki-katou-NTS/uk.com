package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface CompanyApprovalRootRepository {

	/**
	 * getComRootStart CMM018
	 * @param companyId
	 * @param sysAtr
	 * @param lstAppType
	 * @param lstNoticeID
	 * @param lstEventID
	 * @return
	 */
	List<CompanyApprovalRoot> getComRootStart(String companyId, int sysAtr, List<Integer> lstAppType,
			List<Integer> lstNoticeID, List<String> lstEventID);

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
	 * @param employmentRootAtr
	 * @return
	 */
	List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, 
			Integer applicationType, int employmentRootAtr, String id, int sysAtr);

	/**
	 * add Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void addComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * add All Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void addAllComApprovalRoot(List<CompanyApprovalRoot> comAppRoot);

	/**
	 * update Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void updateComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * update All Company Approval Root
	 * 
	 * @param comAppRoot
	 */
	void updateAllComApprovalRoot(List<CompanyApprovalRoot> comAppRoot);

	/**
	 * delete Company Approval Root
	 * 
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 */
	void deleteComApprovalRoot(String companyId, String approvalId, String historyId);

	/**
	 * 会社別承認ルート
	 * @param companyID 会社ID
	 * @param date 基準日
	 * @param rootAtr 就業ルート区分
	 * @param targetType 対象申請
	 * @param sysAtr システム区分 
	 * @return
	 */
	Optional<CompanyApprovalRoot> findByBaseDate(String companyID, GeneralDate date, EmploymentRootAtr rootAtr,
			String targetType, int sysAtr);

	/**
	 * 会社別承認ルート common
	 * @param companyID 会社ID
	 * @param date 基準日
	 * @param sysAtr システム区分
	 * @return
	 */
	Optional<CompanyApprovalRoot> findByBaseDateOfCommon(String companyID, GeneralDate date, int sysAtr);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する
	 * 
	 * @param cid
	 * @param baseDate
	 * @return
	 */
	List<CompanyApprovalRoot> findByBaseDate(String cid, GeneralDate baseDate, int sysAtr);
	/**
	 * get Company Approval Root By type
	 * @param companyId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	List<CompanyApprovalRoot> getComApprovalRootByType(String companyId, Integer applicationType,
			int employmentRootAtr, String id, int sysAtr);
	
	/**
	 * getComAppRootLast
	 * @param companyID
	 * @param endDate
	 * @return
	 */
	List<CompanyApprovalRoot> getComAppRootLast(String companyID,GeneralDate endDate, int sysAtr);
	
	List<CompanyApprovalRoot> getComAppRoot(String companyID, GeneralDate date, 
			Integer employmentRootAtr, Integer confirmRootAtr);
	List<CompanyApprovalRoot> findByBaseDateJinji(String cid, GeneralDate baseDate, List<Integer> lstNoticeID, List<String> lstEventID);
}
