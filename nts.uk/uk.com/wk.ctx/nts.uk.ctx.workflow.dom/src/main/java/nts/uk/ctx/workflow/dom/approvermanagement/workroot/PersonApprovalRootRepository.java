package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 個人別承認ルートRepository
 * @author hoatt
 *
 */
public interface PersonApprovalRootRepository {
	
	/**
	 * [1] 承認者として登録されている対象社員リストを取得する
	 * 
	 * @param sid 社員ID
	 * @param baseDate 年月日
	 * @return List<社員ID> 承認対象社員リスト
	 */
	List<String> getListSidRegistered(String sid, GeneralDate baseDate);
	
	/**
	 * [2] 承認者を取得する
	 * 
	 * @param sid 社員ID
	 * @param baseDate 年月日
	 * @return List<社員ID> 承認者リスト
	 */
	List<String> getListAppover(String sid, GeneralDate baseDate);
	
	/**
	 * [3] 社員ID、利用する種類から基準日を含む上長・社員が行う承認ルート設定を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @param appTypes 申請種類List
	 * @param confirmationRootTypes 確認ルート種類List
	 * @return 個人別承認ルート
	 */
	List<PersonApprovalRoot> getPersonApprovalRoots(String cid, String sid, GeneralDate baseDate, List<ApplicationType> appTypes, List<ConfirmationRootType> confirmationRootTypes);
	
	/**
	 * 	[4] 社員IDListから基準日を含む承認ルート設定を取得する
	 * @param cid 会社ID
	 * @param sids 社員IDList
	 * @param baseDate 基準日
	 * @param sysAtr システム区分
	 * @return 承認者設定情報
	 */
	List<ApprovalSettingInformation> getApprovalSettingByEmployees(String cid, List<String> sids, GeneralDate baseDate, SystemAtr sysAtr);

	/**
	 * [5] 社員ID、承認IDListから承認ルート設定を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param approvalIds 承認IDList
	 * @return 個人別承認ルート
	 */
	List<PersonApprovalRoot> getPsRootBySidAndApprovals(String cid, String sid, List<String> approvalIds);
	
	/**
	 * [6] 基準日後の開始日がある履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return	個人別承認ルートList
	 */
	List<PersonApprovalRoot> getHistWithStartAfterBaseDate(String cid, String sid, GeneralDate baseDate);
	
	/**
	 * 	[7] 承認IDListから履歴を削除する
	 * @param approvalIds 承認IDList
	 */
	void deleteHistFromApprovals(List<String> approvalIds);
	
	/**
	 * 	[8] 前の履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return 個人別承認ルートList
	 */
	List<PersonApprovalRoot> getPreviousHistory(String cid, String sid, GeneralDate baseDate);
	
	/**
	 * [9] 基準日後の終了日がある履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return 個人別承認ルートList
	 */
	List<PersonApprovalRoot> getHistWithEndAfterBaseDate(String cid, String sid, GeneralDate baseDate);
	
	/**
	 * [10] 基準日を含む以降の社員履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return 承認者設定情報List	
	 */
	List<ApprovalSettingInformation> getHistIncludeBaseDate(String cid, String sid, GeneralDate baseDate);
	
	/**
	 * [11] 開始日が基準日より後の社員履歴を取得する
	 * @param cid 会社ID
	 * @param sids 社員IDList
	 * @param baseDate 基準日
	 * @return 個人別承認ルートList
	 */
	List<PersonApprovalRoot> getEmpHistWithStartAfterBaseDate(String cid, List<String> sids, GeneralDate baseDate);
	
	/**
	 * [12] 全ての社員履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param systemAtr システム区分
	 * @return 個人別承認ルートList
	 */
	List<PersonApprovalRoot> getAllEmpHist(String cid, String sid, SystemAtr systemAtr);
	
	/**
	 * [13] 最新の履歴の開始日を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @return
	 */
	Optional<GeneralDate> getStartOfLastestHist(String cid, String sid);
	
	/**
	 * 	[14] 基準日から一番小さい履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @param appTypes 申請種類List
	 * @param confirmTypes 確認ルート種類List
	 * @return 個人別承認ルート
	 */
	Optional<PersonApprovalRoot> getSmallestHistFromBaseDate(String cid, String sid, GeneralDate baseDate, List<ApplicationType> appTypes, List<ConfirmationRootType> confirmTypes);

	/**
	 * [15] 期間から履歴を取得する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param period 期間
	 * @return 個人別承認ルート	
	 */
	List<PersonApprovalRoot> getHistFromBaseDate(String cid, String sid, DatePeriod period);
	
	/**
	 * [16]個人別承認ルートと承認フェーズを insertする(個人別承認ルート,List<承認フェーズ>)	
	 * @param personApprovalRoot
	 * @param approvalPhases
	 */
	void insertPersonApprovalRootAndPhases(PersonApprovalRoot personApprovalRoot, List<ApprovalPhase> approvalPhases);
	
	/**
	 * get all Person Approval Root
	 * 
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	List<PersonApprovalRoot> getPsRootStart(String companyId, String employeeId, int sysAtr,
			List<Integer> lstAppType, List<Integer> lstNoticeID, List<String> lstEventID);

	/**
	 * delete Person Approval Root
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param  historyId
	 */
	void deletePsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId);

	/**
	 * add Person Approval Root
	 * 
	 * @param psAppRoot
	 */
	void addPsApprovalRoot(PersonApprovalRoot psAppRoot);
	/**
	 * add All Person Approval Root
	 * 
	 * @param psAppRoot
	 */
	void addAllPsApprovalRoot(List<PersonApprovalRoot> psAppRoot);

	/**
	 * update Person Approval Root
	 * 
	 * @param psAppRoot
	 */
	void updatePsApprovalRoot(PersonApprovalRoot psAppRoot);
	/**
	 * update All Person Approval Root
	 * 
	 * @param psAppRoot
	 */
	void updateAllPsApprovalRoot(List<PersonApprovalRoot> psAppRoot);

	/**
	 * get Person Approval Root By End date
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<PersonApprovalRoot> getPsApprovalRootByEdate(String companyId, String employeeId, GeneralDate endDate,
			Integer applicationType, int employmentRootAtr, String id, int sysAtr);

	/**
	 * get PsApprovalRoot
	 * 
	 * @param companyId
	 * @param approvalId
	 * @param employeeId
	 * @param historyId
	 * @return
	 */
	Optional<PersonApprovalRoot> getPsApprovalRoot(String companyId, String approvalId, String employeeId,
			String historyId);

	/**
	 * 個人別承認ルート
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @param rootAtr 承認ルート区分
	 * @param targetType 対象申請
	 * @param sysAtr システム区分
	 * @return
	 */
	Optional<PersonApprovalRoot> findByBaseDate(String companyID, String employeeID, GeneralDate date, EmploymentRootAtr rootAtr,
			String targetType, int sysAtr);

	/**
	 * 個人別承認ルート common
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param baseDate 基準日
	 * @param sysAtr システム区分
	 * @return
	 */
	Optional<PersonApprovalRoot> findByBaseDateOfCommon(String companyID, String employeeID, GeneralDate baseDate, int sysAtr);
	/**
	 * ドメインモデル「個人別就業承認ルート」を取得する(lấy dữ liệu domain「個人別就業承認ルート」)
	 * @param companyId
	 * @param baseDate ・期間．開始日 <= 基準日  ・期間．終了日 >= 基準日
	 * @return
	 */
	List<PersonApprovalRoot> findAllByBaseDate(String companyId, GeneralDate baseDate, int sysAtr);
	/**
	 * get Person Approval Root By type
	 * @param companyId
	 * @param employeeId
	 * @param applicationType
	 * @param employmentRootAtr
	 * @return
	 */
	List<PersonApprovalRoot> getPsApprovalRootByType(String companyId, String employeeId, Integer applicationType,
			int employmentRootAtr, String id, int sysAtr);
	/**
	 * getPsAppRootLastest
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @return
	 */
	List<PersonApprovalRoot> getPsAppRootLastest(String companyId, String employeeId, GeneralDate endDate, int sysAtr);
	
	List<PersonApprovalRoot> getPsAppRoot(String companyID, GeneralDate date, 
			Integer employmentRootAtr, Integer confirmRootAtr);

	/**
	 * getNewestCommonPsAppRoot
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	Optional<PersonApprovalRoot> getNewestCommonPsAppRoot(String companyId, String employeeId, int sysAtr);

	/**
	 * getNewestMonthlyPsAppRoot
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	Optional<PersonApprovalRoot> getNewestMonthlyPsAppRoot(String companyId, String employeeId);

	/**
	 * getPastHistory
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	List<PersonApprovalRoot> getPastHistory(String companyId, String employeeId);

	/**
	 * getPsApprovalRootBySdate
	 * @param companyId
	 * @param employeeId
	 * @param startDate
	 * @return
	 */
	List<PersonApprovalRoot> getPsApprovalRootBySdate(String companyId, String employeeId, GeneralDate startDate, int sysAtr);
	
	Optional<PersonApprovalRoot> getHistLastestCom(String companyId, String employeeId);
	
	Optional<PersonApprovalRoot> getHistLastestPri(String companyId, String employeeId, int employmentRootAtr,
			Integer appType, String id, int sysAtr);
	//get by endDate
	List<PersonApprovalRoot> getByEndDate(String companyId, String employeeId, int sysAtr, GeneralDate endDate);
	
	List<PersonApprovalRoot> findByBaseDateJinji(String companyId, GeneralDate baseDate,
			List<Integer> lstNoticeID, List<String> lstEventID);
	/**
	 * 期間、システム、承認ルート区分から個人別承認ルートを取得
	 * @param cid
	 * @param period
	 * @param sysAtr
	 * @param lstRootAtr
	 * @return
	 */
	List<PersonApprovalRoot>  getAppRootByDatePeriod(String cid, DatePeriod period, SystemAtr sysAtr, List<Integer> lstRootAtr);
}
