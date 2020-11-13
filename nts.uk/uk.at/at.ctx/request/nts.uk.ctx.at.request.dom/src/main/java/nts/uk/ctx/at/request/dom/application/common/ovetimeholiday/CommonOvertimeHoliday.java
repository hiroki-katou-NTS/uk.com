package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.ColorConfirmResult;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.AppDisplayAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

public interface CommonOvertimeHoliday {
	
	/**
	 * 01-01_休憩時間帯を取得する
	 * @param companyID 会社ID
	 * @param workTypeCode 勤務種類コード
	 * @param workTimeCode 就業時間帯コード
	 * @param opStartTime Optional＜開始時刻＞
	 * @param opEndTime Optional＜終了時刻＞
	 * @return
	 */
	public List<DeductionTime> getBreakTimes(String companyID, String workTypeCode, String workTimeCode, 
			Optional<TimeWithDayAttr> opStartTime, Optional<TimeWithDayAttr> opEndTime);
	
	/**
	 * 01-02_時間外労働を取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param appType 時間外表示区分
	 * @return
	 */
	public Optional<AgreeOverTimeOutput> getAgreementTime(String companyID, String employeeID, ApplicationType appType);

	/**
	 * 01-04_加給時間を取得
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請日
	 * @param bonusTimeDisplayAtr 加給時間表示区分
	 * @return
	 */
	public List<BonusPayTimeItem> getBonusTime(String companyID, String employeeID, GeneralDate appDate, UseAtr bonusTimeDisplayAtr);
	
	/**
	 * 01-07_乖離理由を取得
	 * @param prePostAtr 事前事後区分
	 * @param divergenceReasonInputAtr 乖離理由入力区分
	 * @return
	 */
	public boolean displayDivergenceReasonInput(PrePostAtr prePostAtr, UseAtr divergenceReasonInputAtr);
	
	/**
	 * 01-08_乖離定型理由を取得
	 * @param companyID 会社ID
	 * @param prePostAtr 事前事後区分
	 * @param divergenceReasonFormAtr 乖離理由定型区分
	 * @param appType 申請種類
	 * @return
	 */
	public List<DivergenceReason> getDivergenceReasonForm(String companyID, PrePostAtr prePostAtr, 
			UseAtr divergenceReasonFormAtr, ApplicationType appType);
	
	/**
	 * 01-13_事前事後区分を取得
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param uiType 起動区分（メニューから起動、日別修正、トップページアラームから起動、指示画面起動）
	 * @param overtimeAtr 残業区分
	 * @param appDate 申請日
	 * @param displayPrePostFlg 事前事後区分表示
	 * @return
	 */
	public DisplayPrePost getDisplayPrePost(String companyID, ApplicationType appType, Integer uiType,
			OverTimeAtr overtimeAtr, GeneralDate appDate, AppDisplayAtr displayPrePostFlg);
	
	/**
	 * 01-17_休憩時間取得
	 * @param companyID 会社ID
	 * @param timeCalUse 時刻計算利用区分
	 * @param breakInputFieldDisp 休憩入力欄を表示する
	 * @param appType 申請種類
	 * @return
	 */
	public boolean getRestTime(String companyID, UseAtr timeCalUse, Boolean breakInputFieldDisp, ApplicationType appType);
	
	/**
	 * 03-08_申請日の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeID 申請者の社員ID
	 * @param appDate 申請日
	 * @param appType 申請種類
	 * @param appDateContradictionAtr 申請日矛盾区分
	 * @return
	 */
	public List<ConfirmMsgOutput> inconsistencyCheck(String companyID, String employeeID, GeneralDate appDate, ApplicationType appType, AppDateContradictionAtr appDateContradictionAtr);
	
	/**
	 * 01-14_勤務時間取得
	 * @param companyID 会社ID
	 * @param employeeID 申請者ID
	 * @param appDate 申請日付
	 * @param timeCalUse 時刻計算利用区分
	 * @param atworkTimeBeginDisp 出退勤時刻初期表示区分
	 * @param appType 申請種類
	 * @param workTimeCD 就業時間帯コード
	 * @param startTime Opitonal＜開始時刻＞
	 * @param endTime Opitonal＜終了時刻＞
	 * @return
	 */
//	public RecordWorkOutput getWorkingHours(String companyID, String employeeID, GeneralDate appDate, UseAtr timeCalUse, AtWorkAtr atworkTimeBeginDisp,
//			ApplicationType appType, String workTimeCD, Optional<Integer> startTime, Optional<Integer> endTime, ApprovalFunctionSetting approvalFunctionSetting);
	
	/**
	 * 03-01_事前申請超過チェック
	 * @param companyId: 会社ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param prePostAtr: 事前事後区分
	 * @param attendanceId: 勤怠種類
	 * @param overtimeInputs: 申請時間(input time in a ATTENDANCE)
	 * @return 0: Normal. 1: 背景色を設定する
	 */
	ColorConfirmResult preApplicationExceededCheck(String companyId, GeneralDate appDate, GeneralDateTime inputDate, PrePostAtr prePostAtr, int attendanceId, 
			List<OverTimeInput> overtimeInputs, String employeeID) ;
	
	/**
	 * 03-01_事前申請超過チェック
	 * @param companyId: 会社ID
	 * @param appDate: 申請日
	 * @param inputDate: 入力日
	 * @param prePostAtr: 事前事後区分
	 * @param attendanceId: 勤怠種類
	 * @param overtimeInputs: 申請時間(input time in a ATTENDANCE)
	 * @return 0: Normal. 1: 背景色を設定する
	 */
	ColorConfirmResult preApplicationExceededCheck010(String companyId, GeneralDate appDate, GeneralDateTime inputDate, PrePostAtr prePostAtr, 
			int attendanceId, List<HolidayWorkInput> overtimeInputs, String employeeID) ;
	
	/**
	 * 
	 * @param 計算フラグ:CalculateFlg(0,1)
	 * @param companyID
	 * @param employeeID
	 * @param rootAtr
	 * @param targetApp
	 * @param appDate
	 */
	/**
	 * 03-06_計算ボタンチェック
	 * @param calculateFlg
	 * @param timeCalUse 時刻計算利用区分
	 */
	void calculateButtonCheck(int calculateFlg, UseAtr timeCalUse);
	
	/**
	 * 03-03_３６上限チェック（月間） KAF005
	 */
	Optional<AppOvertimeDetail> registerOvertimeCheck36TimeLimit(String companyId, String employeeId,
			GeneralDate appDate, List<OverTimeInput> overTimeInput);

	/**
	 * 05_３６上限チェック(詳細) KAF005
	 */
	Optional<AppOvertimeDetail> updateOvertimeCheck36TimeLimit(String companyId, String appId, String enteredPersonId,
			String employeeId, GeneralDate appDate, List<OverTimeInput> overTimeInput);

	/**
	 * 03-03_３６上限チェック（月間） KAF010
	 */
	Optional<AppOvertimeDetail> registerHdWorkCheck36TimeLimit(String companyId, String employeeId, GeneralDate appDate,
			List<HolidayWorkInput> holidayWorkInputs);

	/**
	 * ３６上限チェック(詳細) KAF010
	 */
	Optional<AppOvertimeDetail> updateHdWorkCheck36TimeLimit(String companyId, String appId, String enteredPersonId,
			String employeeId, GeneralDate appDate, List<HolidayWorkInput> holidayWorkInputs);
	
	/**
	 * 03-02-1_チェック条件
	 * @param prePostAtr
	 * @param companyID
	 * @return
	 */
	public boolean checkCodition(int prePostAtr,String companyID, boolean isCalculator);
	
	/**
	 * 06-01_色表示チェック
	 * @param overTimeInputs
	 * @param overtimeInputCaculations
	 */
//	public List<CaculationTime> checkDisplayColor(List<CaculationTime> overTimeInputs,
//			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID,ApprovalFunctionSetting approvalFunctionSetting,String siftCD);
//	
//	public ColorConfirmResult checkDisplayColorCF(List<CaculationTime> overTimeInputs,
//			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID,ApprovalFunctionSetting approvalFunctionSetting,String siftCD);
//	
	/**
	 * 06-01_色表示チェック
	 * @param breakTimeInputs
	 * @param overtimeInputCaculations
	 * @param prePostAtr
	 * @param inputDate
	 * @param appDate
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param approvalFunctionSetting
	 * @param siftCD
	 * @return
	 */
	public List<CaculationTime> checkDisplayColorHol(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);
	
	public ColorConfirmResult checkDisplayColorHolCF(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);
	
	/**
	 * 06-01-a_色表示チェック（承認者）
	 * @param breakTimeInputs
	 * @param holidayWorkCal
	 * @param prePostAtr
	 * @param inputDate
	 * @param appDate
	 * @param appType
	 * @param employeeID
	 * @param companyID
	 * @param siftCD
	 * @return
	 */
	public List<CaculationTime> checkDisplayColorForApprover(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);
	
	public ColorConfirmResult checkDisplayColorForApproverCF(List<CaculationTime> breakTimeInputs,
			Map<Integer,TimeWithCalculationImport> holidayWorkCal,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID ,String siftCD);
	
	/**
	 * @return
	 * 06-02-2_申請時間を取得
	 */
	public List<CaculationTime> getAppOvertimeCaculation(List<CaculationTime> caculationTimes,List<OvertimeInputCaculation> overtimeInputCaculations);
	
	/**
	 * 06-03_加給時間を取得
	 * @param companyID
	 * @param employeeId
	 * @param appDate
	 * @param appType
	 */
	public List<CaculationTime> getCaculationBonustime(String companyID,String employeeId, String appDate,int appType,List<CaculationTime> caculationTimes);
	
	/**
	 * 06_計算処理
	 * @param appCommonSettingOutput
	 * @param appDate
	 * @param siftCD
	 * @param workTypeCode
	 * @param startTime
	 * @param endTime
	 * @param startTimeRests
	 * @param endTimeRests
	 * @return
	 */
	public List<OvertimeInputCaculation> calculator(AppCommonSettingOutput appCommonSettingOutput, String appDate, String siftCD, String workTypeCode,
			Integer startTime,Integer endTime, List<Integer> startTimeRests,List<Integer> endTimeRests);
	/**
	 * Refactor5 06_計算処理
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム(残業・休出).06_計算処理
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param workTypeCode
	 * @param workTimeCode
	 * @param timeZones
	 * @param breakTimes
	 * @return 申請時間 List
	 */
	public List<ApplicationTime> calculator(
			String companyId,
			String employeeId,
			GeneralDate date,
			Optional<String> workTypeCode,
			Optional<String> workTimeCode,
			List<TimeZone> timeZones,
			List<BreakTimeSheet> breakTimes);
	/**
	 * 03-01-1_チェック条件
	 * @param prePostAtr 事前事後区分
	 * @param preExcessDisplaySetting 事前超過表示設定
	 * @return
	 */
	public UseAtr preAppSetCheck(PrePostAtr prePostAtr, UseAtr preExcessDisplaySetting);
	
	/**
	 * 03-02-1_チェック条件
	 * @param performanceExcessAtr 実績超過区分
	 * @param prePostAtr 事前事後区分
	 * @return
	 */
	public AppDateContradictionAtr actualSetCheck(AppDateContradictionAtr performanceExcessAtr, PrePostAtr prePostAtr);
	
	/**
	 * 03-01_事前申請超過チェック（＃108410）
	 * @param employeeName 申請者名
	 * @param appDate 申請日
	 * @param preActualColorResult 計算結果
	 * @return
	 */
	List<ConfirmMsgOutput> preAppExcessCheckHdApp(String employeeName, GeneralDate appDate, PreActualColorResult preActualColorResult, List<WorkdayoffFrame> breaktimeFrames);
	
	/**
	 * 03-02_実績超過チェック（＃108410）
	 * @param employeeName 申請者名
	 * @param appDate 申請日
	 * @param performanceExcessAtr 実績超過区分
	 * @param preActualColorResult 計算結果
	 * @return
	 */
	List<ConfirmMsgOutput> achievementCheckHdApp(String employeeName, GeneralDate appDate, AppDateContradictionAtr performanceExcessAtr, 
			PreActualColorResult preActualColorResult, List<WorkdayoffFrame> breaktimeFrames);
}
