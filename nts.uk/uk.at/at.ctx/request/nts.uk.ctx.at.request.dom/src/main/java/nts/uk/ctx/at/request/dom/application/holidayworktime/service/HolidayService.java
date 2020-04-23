package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkBreakTimeSetOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.InitWorkTypeWorkTime;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTimeHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTypeHolidayWork;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

public interface HolidayService {
	/**
	 * 4_a.勤務種類を取得する（法定内外休日）
	 * @param companyID
	 * @param employeeID
	 * @param appEmploymentSettings
	 * @param appDate
	 * @param personalLablorCodition
	 * @return
	 */
	public WorkTypeHolidayWork getWorkTypeForLeaverApp(String companyID, String employeeID,List<AppEmploymentSetting> appEmploymentSettings,GeneralDate appDate,Optional<WorkingConditionItem> personalLablorCodition,Integer paramholidayCls);
	
	 /**
	 * 4.勤務種類を取得する
	 * @param companyID
	 * @param employeeID
	 * @param approvalFunctionSetting
	 * @param appEmploymentSettings
	 * @param appDate
	 * @return
	 */
	public WorkTypeHolidayWork getWorkTypes(String companyID, String employeeID,List<AppEmploymentSetting> appEmploymentSettings,GeneralDate appDate,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate);
	/**
	 * 4_b.勤務種類を取得する（詳細）
	 * @param companyID
	 * @param employeeID
	 * @param appEmploymentSettings
	 * @param appDate
	 * @param personalLablorCodition
	 * @return
	 */
	public WorkTypeHolidayWork getListWorkType(String companyID,
			String employeeID,List<AppEmploymentSetting> appEmploymentSettings,GeneralDate appDate,Optional<WorkingConditionItem> personalLablorCodition);
	/**
	 * 4_c.初期選択
	 * @param workType
	 * @param appDate
	 */
	public void getWorkType(String companyID,WorkTypeHolidayWork workType,GeneralDate appDate, String employeeID,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate);
	/**
	 * 5.就業時間帯を取得する
	 * @param companyID
	 * @param employeeID
	 * @param appEmploymentSettings
	 * @param baseDate
	 * @param isChangeDate 
	 * @return
	 */
	public WorkTimeHolidayWork getWorkTimeHolidayWork(String companyID, String employeeID,GeneralDate baseDate,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate);
	
	/**
	 * insert HolidayWork
	 * @param domain
	 * @param newApp
	 */
	void createHolidayWork(AppHolidayWork domain, Application_New newApp);
	
	/**
	 * 11.休出申請（振休変更）削除
	 * @param appID
	 */
	public void delHdWorkByAbsLeaveChange(String appID);
	
	/**
	 * 1.休出申請（新規）起動処理
	 * @param companyID 会社ID
	 * @param employeeIDLst 申請者リスト<Optional>
	 * @param dateLst 申請対象日リスト<Optional>
	 * @return
	 */
	public AppHdWorkDispInfoOutput getStartNew(String companyID, List<String> employeeIDLst, List<GeneralDate> dateLst);
	
	/**
	 * 1-1.休日出勤申請（新規）起動時初期データを取得する
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請対象日<Optional>
	 * @param baseDate 基準日
	 * @param prePostAtr 事前事後区分
	 * @param appEmploymentSetting 雇用別申請承認設定
	 * @param workTimeLst 就業時間帯の設定
	 * @param approvalFunctionSet 申請承認機能設定
	 * @param requestSetting 申請承認設定
	 * @param achievementOutputLst 表示する実績内容
	 * @return
	 */
	public HdWorkDispInfoWithDateOutput initDataNew(String companyID, String employeeID, Optional<GeneralDate> appDate, GeneralDate baseDate, 
			PrePostAtr prePostAtr, AppEmploymentSetting appEmploymentSetting, List<WorkTimeSetting> workTimeLst, 
			ApprovalFunctionSetting approvalFunctionSet, RequestSetting requestSetting, List<AchievementOutput> achievementOutputLst);
	
	/**
	 * 1-2.起動時勤務種類リストを取得する
	 * @param companyID 会社ID
	 * @param appEmploymentSetting 雇用別申請承認設定
	 * @return
	 */
	public List<WorkType> getWorkTypeLstStart(String companyID, AppEmploymentSetting appEmploymentSetting);
	
	/**
	 * 1-3.起動時勤務種類・就業時間帯の初期選択
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請対象日<Optional>
	 * @param baseDate 基準日
	 * @param workTypeLst 勤務種類リスト：メインモデル「勤務種類」リスト
	 * @param workTimeLst 就業時間帯リスト：メインモデル「就業時間帯の設定」リスト
	 * @param achievementOutputLst 表示する実績内容<Optional>
	 * @return
	 */
	public InitWorkTypeWorkTime initWorkTypeWorkTime(String companyID, String employeeID, Optional<GeneralDate> appDate, GeneralDate baseDate,
			List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeLst, List<AchievementOutput> achievementOutputLst,
			AppEmploymentSetting appEmploymentSetting);
	
	/**
	 * 01-01_休憩時間を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param workTypeCD 勤務種類コード
	 * @param workTimeCD 就業時間帯コード
	 * @param startTime Optional＜開始時刻＞
	 * @param endTime Optional＜終了時刻＞
	 * @param timeCalUse 時刻計算利用区分
	 * @param breakTimeDisp 休憩入力欄を表示する
	 * @return
	 */
	public HdWorkBreakTimeSetOutput getBreakTime(String companyID, ApplicationType appType, String workTypeCD, String workTimeCD, 
			Optional<TimeWithDayAttr> startTime, Optional<TimeWithDayAttr> endTime, UseAtr timeCalUse, Boolean breakTimeDisp);
	
}
	
