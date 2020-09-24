package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDetailOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSettingOutput;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppWorkChangeService {
	
	/**
	 * 勤務変更申請画面初期（新規）
	 * @param companyID 会社ID
	 * @param employeeIDLst 申請者リスト
	 * @param dateLst 申請対象日リスト
	 * @return
	 */
	public AppWorkChangeDispInfo getStartNew(String companyID, List<String> employeeIDLst, List<GeneralDate> dateLst, 
			AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * 勤務種類を取得する
	 * @param appEmploymentSet ドメインモデル「雇用別申請承認設定」
	 * @return 勤務種類リスト
	 */
	public List<WorkType> getWorkTypeLst(AppEmploymentSet appEmploymentSet);
	
	/**
	 * 勤務種類・就業時間帯の初期選択項目を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @param workTypeLst 勤務種類リスト
	 * @param workTimeLst 就業時間帯リスト
	 * @return
	 */
	public WorkTypeWorkTimeSelect initWorkTypeWorkTime(String companyID, String employeeID, GeneralDate date, 
			List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeLst);
	
	/**
	 * 勤務種類・就業時間帯を変更する時
	 * @param companyID 会社ID
	 * @param workTypeCD 勤務種類コード
	 * @param workTimeCD 就業時間帯コード<Optional>
	 * @param appWorkChangeSet ドメインモデル「勤務変更申請設定」
	 * @return
	 */
	public ChangeWkTypeTimeOutput changeWorkTypeWorkTime(String companyID, String workTypeCD, Optional<String> workTimeCD, 
			AppWorkChangeSet appWorkChangeSet);
	
	/**
	 * 申請日を変更する(thay đổi ngày nộp đơn)
	 * @param companyID 会社ID
	 * @param dateLst 申請対象日リスト
	 * @param appWorkChangeDispInfo 勤務変更申請の表示情報
	 * @return
	 */
	public AppWorkChangeDispInfo changeAppDate(String companyID, List<GeneralDate> dateLst, AppWorkChangeDispInfo appWorkChangeDispInfo);
	
	/**
	 * 登録前のエラーチェック処理
	 * @param companyID 会社ID
	 * @param errorFlag 承認ルートエラー情報
	 * @param application 申請
	 * @param appWorkChange 勤務変更申請
	 * @return
	 */
	public WorkChangeCheckRegOutput checkBeforeRegister(String companyID, ErrorFlagImport errorFlag, Application application, AppWorkChange appWorkChange, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * 登録時チェック処理（勤務変更申請）
	 * @param application 申請
	 * @param appWorkChange 勤務変更申請
	 */
	public void checkRegisterWorkChange(Application application, AppWorkChange appWorkChange);
	
	/**
	 * 勤務変更申請就業時間チェックの内容
	 * @param application 申請
	 * @param appWorkChange 勤務変更申請
	 * @return
	 */
	public List<String> detailWorkHoursCheck(Application application, AppWorkChange appWorkChange);
	
	/**
	 * 1日休日のチェック
	 * @param employeeID 社員ID
	 * @param period 期間
	 * @return
	 */
	public List<GeneralDate> checkHoliday(String employeeID, DatePeriod period);
	
	/**
	 * 勤務変更申請の起動処理 B Refactor 4
	 * 勤務変更申請画面初期（更新）
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param appDispInfoStartupOutput 申請表示情報
	 * @return
	 */
	public AppWorkChangeDetailOutput startDetailScreen(String companyID, String appID, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * 更新前のエラーチェック処理
	 * @param companyID 会社ID
	 * @param application 申請
	 * @param appWorkChange 勤務変更申請
	 * @param agentAtr 代行申請区分
	 * @return
	 */
	public List<ConfirmMsgOutput> checkBeforeUpdate(String companyID, Application application, 
			AppWorkChange appWorkChange, boolean agentAtr, AppDispInfoStartupOutput appDispInfoStartupOutput);
	/**
	 * 勤務変更申請の起動処理
	 * Refactor4
	 * UKDesign.UniversalK.就業.KAF_申請.KAF007_勤務変更申請.A:勤務変更申請（新規）.アルゴリズム.勤務変更申請画面初期（新規）
	 * @param mode 画面モード
	 * @param companyId 会社ID
	 * @param employeeId 社員ID＜Optional＞
	 * @param dates 申請対象日リスト＜Optional＞ 
	 * @param appWorkChangeDispInfo 勤務変更申請の表示情報＜Optional＞
	 * @param appWorkChange 勤務変更申請＜Optional＞
	 * @return 
	 */
	public AppWorkChangeOutput getAppWorkChangeOutput(boolean mode, String companyId, Optional<String> employeeId,
			Optional<List<GeneralDate>> dates, Optional<AppWorkChangeDispInfo> appWorkChangeDispInfo, Optional<AppWorkChange> appWorkChange );
	
	/**
	 * 勤務変更申請画面初期（新規）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param dates 申請対象日リスト
	 * @param appDispInfoStartupOutput 申請表示情報
	 * @return 勤務変更申請の表示情報
	 */
	public AppWorkChangeDispInfo getAppWorkChangeDisInfo(String companyId, String employeeId, List<GeneralDate> dates, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * 勤務変更申請設定を取得する
	 * @param companyId 会社ID
	 * @return
	 */
	public AppWorkChangeSettingOutput getAppWorkChangeSettingOutput(String companyId);
	
	
	/**
	 * 勤務変更申請の登録前チェック処理
	 * @param mode
	 * @param companyId
	 * @param application
	 * @param appWorkChange
	 * @param opErrorFlag
	 * @return
	 */
	public WorkChangeCheckRegOutput checkBeforeRegister(Boolean mode, String companyId, Application application, AppWorkChange appWorkChange, ErrorFlagImport opErrorFlag, AppDispInfoStartupOutput appDispInfoStartupOutput);
}
