package nts.uk.pub.spr.approvalroot;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.pub.spr.approvalroot.output.ApprovalRootSpr;

/**
 * 04 承認ルート
 * @author Doan Duy Hung
 *
 */
public interface SprApprovalRootService {
	
	/**
	 * 04 承認ルート
	 * @param employeeCD
	 * @param date
	 */
	public List<ApprovalRootSpr> getApprovalRoot(String employeeCD, String date);
	
	/**
	 * パラメータチェック
	 * @param employeeCD ログイン社員コード
	 * @param date 対象日
	 */
	public void checkParam(String employeeCD, String date);
	
	/**
	 * 承認すべき対象者
	 * @param companyID 会社ID
	 * @param approverID 承認社員ID
	 * @param date 対象日
	 */
	public List<ApprovalRootSpr> getApproverStatus(String companyID, String approverID, GeneralDate date);
	
	/**
	 * 承認ルート検索
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @return
	 */
	public List<String> approvalRootSearch(String companyID, GeneralDate date, String approverID, String jobTitleID);
	
	/**
	 * 会社別
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @return
	 */
	public List<String> approvalRootSearchByCom(String companyID, GeneralDate date, String approverID, String jobTitleID);
	
	/**
	 * 会社別ルート設定対象者取得
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @param employmentRootAtr
	 * @param confirmRootType
	 * @return
	 */
	public List<String> searchApprovalRootCom(String companyID, GeneralDate date, String approverID, 
			String jobTitleID, Integer employmentRootAtr, Integer confirmRootType);
	
	/**
	 * 承認者の判定 
	 * @param companyID
	 * @param employeeID
	 * @param jobTitleID
	 * @param branchID
	 * @return
	 */
	public boolean juddmentApprover(String companyID, String employeeID, String jobTitleID, String branchID);
	
	/**
	 * 職場別
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @return
	 */
	public List<String> approvalRootSearchByWp(String companyID, GeneralDate date, String approverID, String jobTitleID);
	
	/**
	 * 職場別ルート設定対象者取得
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @param employmentRootAtr
	 * @param confirmRootType
	 * @return
	 */
	public List<String> searchApprovalRootWp(String companyID, GeneralDate date, String approverID, 
			String jobTitleID, Integer employmentRootAtr, Integer confirmRootType);
	
	/**
	 * 職場所属（下位職場含む）社員取得
	 * @param companyID
	 * @param workplaceID
	 * @param date
	 * @return
	 */
	public List<String> getEmployeeByWpList(String companyID, String workplaceID, GeneralDate date);
	
	/**
	 * 指定職場所属社員取得
	 * @param workplaceID
	 * @param date
	 * @return
	 */
	public List<String> getEmployeeByWorkplace(String workplaceID, GeneralDate date);
	
	/**
	 * 個人別
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @return
	 */
	public List<String> approvalRootSearchByPs(String companyID, GeneralDate date, String approverID, String jobTitleID);
	
	/**
	 * 個人別ルート設定対象者取得
	 * @param companyID
	 * @param date
	 * @param approverID
	 * @param jobTitleID
	 * @param employmentRootAtr
	 * @param confirmRootType
	 * @return
	 */
	public List<String> searchApprovalRootPs(String companyID, GeneralDate date, String approverID, 
			String jobTitleID, Integer employmentRootAtr, Integer confirmRootType);
	
	/**
	 * 申請済み検索
	 * @param companyID
	 * @param employeeID
	 * @param date
	 * @param rootType
	 * @return
	 */
	public List<String> getAppRootStateIDByType(String companyID, String employeeID, GeneralDate date, Integer rootType);
}
