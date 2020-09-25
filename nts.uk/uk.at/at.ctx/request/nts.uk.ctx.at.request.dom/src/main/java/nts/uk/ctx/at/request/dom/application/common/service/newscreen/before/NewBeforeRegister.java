package nts.uk.ctx.at.request.dom.application.common.service.newscreen.before;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;

/**
 * 
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-1.新規画面登録前の処理(beforeRegister)
 *
 */
public interface NewBeforeRegister {
	/**
	 * 1.入社前退職チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param date 申請する開始日
	 */
	public void retirementCheckBeforeJoinCompany(String companyID, String employeeID, GeneralDate date);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-1.新規画面登録前の処理(beforeRegister).2.申請の締め切り期限のチェック.2.申請の締め切り期限のチェック
	 * @param appStartDate 申請する開始日
	 * @param appEndDate 申請する終了日
	 * @param closureStartDate 締め開始日
	 * @param closureEndDate 締め終了日
	 * @param appDispInfoWithDateOutput 申請表示情報(基準日関係あり)
	 */
	public void deadlineAppCheck(GeneralDate appStartDate, GeneralDate appEndDate,
			GeneralDate closureStartDate, GeneralDate closureEndDate, AppDispInfoWithDateOutput appDispInfoWithDateOutput);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-1.新規画面登録前の処理(beforeRegister).5.申請の受付制限をチェック.5.申請の受付制限をチェック
	 * @param prePostAtr 事前事後区分
	 * @param appStartDate 申請する開始日
	 * @param appEndDate 申請する終了日
	 * @param appType 対象申請
	 * @param overtimeAppAtr 残業区分
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 */
	public void appAcceptanceRestrictionsCheck(PrePostAtr prePostAtr, GeneralDate appStartDate, GeneralDate appEndDate,
			ApplicationType appType, OvertimeAppAtr overtimeAppAtr, AppDispInfoNoDateOutput appDispInfoNoDateOutput);
	
	/**
	 * 3.確定チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 * @param appDispInfoStartupOutput 申請表示情報
	 */
	public void confirmationCheck(String companyID, String employeeID, GeneralDate appDate, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * 6.確定チェック（事前残業申請用）
	 * @param companyID 会社ID 
	 * @param employeeID 社員ID（申請本人の社員ID）
	 * @param appDate 申請対象日
	 * @param appDispInfoStartupOutput 申請表示情報
	 */
	public void confirmCheckOvertime(String companyID, String employeeID, GeneralDate appDate, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.2-1.新規画面登録前の処理(beforeRegister).2-1.新規画面登録前の処理
	 * @param companyID 会社ID
	 * @param employmentRootAtr 就業ルート区分
	 * @param agentAtr 代行申請か
	 * @param application 申請
	 * @param overTimeAtr 残業区分
	 * @param errorFlg 承認ルートのエラーフラグ　（Refactor）
	 * @param lstDateHd 休日リスト
	 * @param appDispInfoStartupOutput
	 * @return
	 */
	public List<ConfirmMsgOutput> processBeforeRegister_New(String companyID, EmploymentRootAtr employmentRootAtr, boolean agentAtr,
			Application application, OvertimeAppAtr overtimeAppAtr, ErrorFlagImport errorFlg, List<GeneralDate> lstDateHd,
			AppDispInfoStartupOutput appDispInfoStartupOutput);
}
