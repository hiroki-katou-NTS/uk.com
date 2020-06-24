package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr_Old;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;

/**
 * 
 * 2-1.新規画面登録前の処理
 *
 */
public interface NewBeforeRegister_New {
	/**
	 * 2-1.新規画面登録前の処理
	 * @param companyID
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @param postAtr
	 * @param routeAtr
	 * @param targetApp
	 * @param overTimeAtr, If APP_TYPE = 0 thì mới có overTime Atr, còn các loại đơn khác thì truyền 0 vào
	 */
    public void processBeforeRegister(Application_New application, OverTimeAtr overTimeAtr, boolean checkOver1Year, List<GeneralDate> lstDateHd);
	/**
	 * 1.入社前退職チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param date 申請する開始日
	 */
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date);
	
	/**
	 * 2.申請の締め切り期限のチェック
	 * @param companyID
	 * @param appID
	 * @param appStartDate
	 * @param appEndDate
	 * @param startDate
	 * @param endDate
	 */
	public void deadlineApplicationCheck(String companyID, Integer closureID, String employeeID,
			GeneralDate deadlineStartDate, GeneralDate deadlineEndDate, GeneralDate appStartDate, GeneralDate appEndDate);
	
	/**
	 * 5.申請の受付制限をチェック
	 * @param postAtr 事前事後区分
	 * @param startDate 申請する開始日
	 * @param endDate 申請する終了日
	 */
	public void applicationAcceptanceRestrictionsCheck(String companyID, ApplicationType appType, PrePostAtr_Old postAtr, GeneralDate startDate, GeneralDate endDate, OverTimeAtr overTimeAtr);
	
	/**
	 * 3.確定チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 */
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate);
	
	/**
	 * 6.確定チェック（事前残業申請用）
	 * @param companyID 会社ID 
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 */
	public void confirmCheckOvertime(String companyID, String employeeID, GeneralDate appDate);
	
	/**
	 * 2-1.新規画面登録前の処理
	 * @param companyID 会社ID
	 * @param employmentRootAtr 就業ルート区分
	 * @param agentAtr 代行申請か
	 * @param application 申請
	 * @param overTimeAtr 残業区分
	 * @param errorFlg 承認ルートのエラーフラグ　（Refactor）
	 * @param lstDateHd 休日リスト
	 * @return
	 */
	public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr, boolean agentAtr,
			Application_New application, OverTimeAtr overTimeAtr, ErrorFlagImport errorFlg, List<GeneralDate> lstDateHd);
}
