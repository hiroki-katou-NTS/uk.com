package nts.uk.ctx.at.request.dom.application.workchange;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
/**
 * 勤務変更申請の登録を実行する
 */
public interface IWorkChangeRegisterService {
	/**
	 * Refactor4
	 */
	/**
	 * ドメインモデル「勤務変更申請設定」の新規登録をする
	 * 勤務変更申請（新規）登録処理
	 * @param workChange: 勤務変更申請設定
	 * @param app: 申請
	 * @return List approval email.
	 */
    ProcessResult registerData(String companyId, Application application, AppWorkChange workChange, List<GeneralDate> lstDateHd, Boolean isMail, AppDispInfoStartupOutput appDispInfoStartupOutput);
	/**
	 * アルゴリズム「勤務変更申請就業時間チェックの内容」を実行する
	 * 就業時間
	 * @param AppWorkChange:  勤務変更申請
	 */	
	void checkWorkHour(AppWorkChange_Old workChange);
	/**
	 * アルゴリズム「勤務変更申請休憩時間１チェックの内容」を実行する
	 * 休憩時間
	 * @param AppWorkChange:  勤務変更申請
	 */
	void checkBreakTime1(AppWorkChange_Old workChange);
	
	/**
	 * 就業時間帯の必須チェック
	 * @param workTypeCD
	 * @return
	 */
	boolean isTimeRequired(String workTypeCD);
	
	/**
	 * 勤務変更申請（新規）登録処理
	 * UKDesign.UniversalK.就業.KAF_申請.KAFS07_勤務変更申請（スマホ）.A：勤務変更申請（新規）.アルゴリズム.勤務変更申請の登録処理
	 * Refactor4
	 * @param mode
	 * @param companyId
	 * @param application
	 * @param appWorkchange
	 * @param lstDates
	 * @param isMail
	 */
	public ProcessResult registerProcess(Boolean mode, String companyId, Application application, AppWorkChange appWorkchange, List<GeneralDate> lstDates, Boolean isMail, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
}
