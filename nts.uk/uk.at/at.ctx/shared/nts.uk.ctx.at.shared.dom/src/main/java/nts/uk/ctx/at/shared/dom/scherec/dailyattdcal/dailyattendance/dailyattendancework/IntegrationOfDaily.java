package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcRange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareTimeFrameError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.CheckExcessAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別勤怠(Work)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.日別勤怠(work).日別勤怠(Work)
 * 
 * 日別実績(Work) (old)
 * @author keisuke_hoshina
 *
 */
@Getter
public class IntegrationOfDaily {
	//社員ID
	@Setter
	private String employeeId;
	
	//年月日
	@Setter
	private GeneralDate ymd;
	
	//日別実績の勤務情報  (old)
//	private WorkInfoOfDailyPerformance workInformation;
	//勤務情報: 日別勤怠の勤務情報
	@Setter
	private WorkInfoOfDailyAttendance workInformation;
	
//	日別実績の所属情報 (old)
//	private AffiliationInforOfDailyPerfor affiliationInfor;
	//所属情報: 日別勤怠の所属情報
	@Setter
	private AffiliationInforOfDailyAttd  affiliationInfor;
	
	//日別実績の計算区分 (old)
//	private CalAttrOfDailyPerformance calAttr;
	//計算区分: 日別勤怠の計算区分
	@Setter
	private CalAttrOfDailyAttd calAttr;
	
	//日別勤怠の勤務種別（廃止） 
	
	/**-------------Optional--------------**/
	//日別実績の出退勤 (old)
//	private Optional<TimeLeavingOfDailyPerformance> attendanceLeave;
	//出退勤: 日別勤怠の出退勤
	@Setter
	private Optional<TimeLeavingOfDailyAttd> attendanceLeave;
	
	//日別実績の休憩時間帯 (old)
//	private List<BreakTimeOfDailyPerformance> breakTime;
	//休憩時間帯: 日別勤怠の休憩時間帯
	@Setter
	private List<BreakTimeOfDailyAttd> breakTime;
	
	//日別実績の外出時間帯 (old)
//	private Optional<OutingTimeOfDailyPerformance> outingTime;
	//外出時間帯: 日別勤怠の外出時間帯
	@Setter
	private Optional<OutingTimeOfDailyAttd> outingTime;
	
	//日別実績の短時間勤務時間帯 (old)
//	private Optional<ShortTimeOfDailyPerformance> shortTime;
	//短時間勤務時間帯: 日別勤怠の短時間勤務時間帯
	@Setter
	private Optional<ShortTimeOfDailyAttd> shortTime;
	
	//日別実績の臨時出退勤 (old)
//	private Optional<TemporaryTimeOfDailyPerformance> tempTime;
	//臨時出退勤: 日別勤怠の臨時出退勤
	@Setter
	private Optional<TemporaryTimeOfDailyAttd> tempTime;
	
	//日別実績の入退門 (old)
//	private Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate;
	//入退門: 日別勤怠の入退門
	@Setter
	private Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate;
	
	//日別実績の勤怠時間 (old)
//	private Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance;
	//勤怠時間: 日別勤怠の勤怠時間
	@Setter
	private Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance;
	
	@Setter
	//日別実績の特定日区分 (old)
//	private Optional<SpecificDateAttrOfDailyPerfor> specDateAttr;
	//特定日区分: 日別勤怠の特定日区分
	private Optional<SpecificDateAttrOfDailyAttd> specDateAttr;
	
	//社員の日別実績エラー一覧
	@Setter
	private List<EmployeeDailyPerError> employeeError; 
	//エラー一覧: 日別勤怠のエラー一覧 //phải chuyển sang shared ?
	
	//日別実績の編集状態 (old)
//	private List<EditStateOfDailyPerformance> editState;
	//編集状態: 日別勤怠の編集状態
	@Setter
	private List<EditStateOfDailyAttd> editState;
	
	//日別実績の任意項目 (old)
//	private Optional<AnyItemValueOfDaily> anyItemValue;
	//任意項目: 日別勤怠の任意項目
	@Setter
	private Optional<AnyItemValueOfDailyAttd> anyItemValue;
	
	//日別実績の作業別勤怠時間
//	private Optional<AttendanceTimeByWorkOfDaily> attendancetimeByWork;
	//作業別勤怠時間: 日別勤怠の作業別勤怠時間  - 
		
	//日別実績のPCログオン情報 (old)
//	private Optional<PCLogOnInfoOfDaily> pcLogOnInfo;
	//PCログオン情報: 日別勤怠のPCログオン情報
	@Setter 
	private Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo;
	
	//日別実績の備考 (old)
//	private List<RemarksOfDailyPerform> remarks;
	//備考: 日別勤怠の備考
	@Setter
	private List<RemarksOfDailyAttd> remarks;
	
	//応援時刻: 日別勤怠の応援作業時間帯 
	
	/**日別実績の勤務種別*/ 
//	private Optional<WorkTypeOfDailyPerformance> businessType;
	
	@Setter
	/**日別勤怠の応援作業時間 */
	private List<OuenWorkTimeOfDailyAttendance> ouenTime = new ArrayList<>();
	@Setter
	/**日別勤怠の応援作業時間帯 */
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet = new ArrayList<>();

	/** 申告計算範囲 */
	@Setter
	private Optional<DeclareCalcRange> declareCalcRange = Optional.empty();
	
	/**
	 * Constructor
	 * @param workInformation 日別実績の勤務情報
	 * @param calAttr 日別実績の計算区分
	 * @param affiliationInfor 日別実績の所属情報
	 * @param pcLogOnInfo 日別実績のPCログオン情報
	 * @param employeeError 社員の日別実績エラー一覧
	 * @param outingTime 日別実績の外出時間帯
	 * @param breakTime 日別実績の休憩時間帯
	 * @param attendanceTimeOfDailyPerformance 日別実績の勤怠時間
	 * @param attendanceLeave 日別実績の出退勤
	 * @param shortTime 日別実績の短時間勤務時間帯
	 * @param specDateAttr 日別実績の特定日区分
	 * @param attendanceLeavingGate 日別実績の入退門
	 * @param anyItemValue 日別実績の任意項目
	 * @param editState 日別実績の編集状態
	 * @param tempTime 日別実績の臨時出退勤
	 */
	public IntegrationOfDaily(
			WorkInfoOfDailyAttendance workInformation, 
			CalAttrOfDailyAttd calAttr,
			AffiliationInforOfDailyAttd affiliationInfor,
			Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo,
			List<EmployeeDailyPerError> employeeError,
			Optional<OutingTimeOfDailyAttd> outingTime,
			List<BreakTimeOfDailyAttd> breakTime,
			Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave, 
			Optional<ShortTimeOfDailyAttd> shortTime,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate,
			Optional<AnyItemValueOfDailyAttd> anyItemValue,
			List<EditStateOfDailyAttd> editState, 
			Optional<TemporaryTimeOfDailyAttd> tempTime,
			List<RemarksOfDailyAttd> remarks) {
		super();
		this.workInformation = workInformation;
		this.calAttr = calAttr;
		this.affiliationInfor = affiliationInfor;
		this.pcLogOnInfo = pcLogOnInfo;
		if(employeeError != null) {
			this.employeeError = new ArrayList<>(employeeError);
		}
		else {
			this.employeeError = Collections.emptyList();
		}
		this.outingTime = outingTime;
		this.breakTime = breakTime;
		this.attendanceTimeOfDailyPerformance = attendanceTimeOfDailyPerformance;
		this.attendanceLeave = attendanceLeave;
		this.shortTime = shortTime;
		this.specDateAttr = specDateAttr;
		this.attendanceLeavingGate = attendanceLeavingGate;
		this.anyItemValue = anyItemValue;
		this.editState = editState;
		this.tempTime = tempTime;
		this.remarks = remarks;
	}

	/**
	 * 残業時間実績超過の取得
	 * @param attendanceItemConverter 
//	 * @param integrationOfDaily
	 */
	public  List<EmployeeDailyPerError> getErrorList(String employeeId,
			   											 GeneralDate targetDate,
			   											 SystemFixedErrorAlarm fixedErrorAlarmCode,
			   											 CheckExcessAtr checkAtr) {
		if(this.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return this.getAttendanceTimeOfDailyPerformance().get().getErrorList(employeeId, targetDate, fixedErrorAlarmCode, checkAtr);
		}
		return Collections.emptyList();
	}
	
	public void stampReplaceFromPcLogInfo(List<LogOnInfo> pcLogOnInfo) {
		if(this.getAttendanceLeave().isPresent()) {
			this.getAttendanceLeave().get().getTimeLeavingWorks().forEach(tc ->{
				tc.setStampFromPCLogOn(pcLogOnInfo.stream().filter(ts -> ts.getWorkNo().v().equals(tc.getWorkNo().v())).findFirst());
			});
		}
	}
	
	/**
	 * 申告（システム固定エラーチェック）
	 * @param fixedErrorAlarmCode エラーコード
	 * @return 社員の日別実績エラー一覧(List)
	 */
	public List<EmployeeDailyPerError> getDeclareErrorList(
			SystemFixedErrorAlarm fixedErrorAlarmCode) {
	
		List<EmployeeDailyPerError> result = new ArrayList<>();
//		AttendanceItemDictionaryForCalc attdIdDic = AttendanceItemDictionaryForCalc.setDictionaryValue();
		String employeeId = this.employeeId;
		GeneralDate date = this.ymd;
		
		// 申告設定を取得する
		if (!this.declareCalcRange.isPresent()) return result;
		DeclareCalcRange calcRange = this.declareCalcRange.get();
		DeclareSet declareSet = calcRange.getDeclareSet();
		// 申告設定残業枠エラーチェック
		if (declareSet.getOvertimeFrame().checkErrorOvertimeFrame()){
			// システムエラーとする
			throw new BusinessException("Msg_2053");
		}
		// 申告設定休出枠エラーチェック
		if (declareSet.getHolidayWorkFrame().checkErrorHolidayWorkFrame()){
			// システムエラーとする
			throw new BusinessException("Msg_2054");
		}
		// 申告設定深夜枠エラーチェック
		if (declareSet.checkErrorMidnightFrame()){
			// システムエラーとする
			throw new BusinessException("Msg_2055");
		}
		// 申告時間枠エラーチェック
		List<DeclareTimeFrameError> frameErrors = declareSet.checkErrorFrame(
				calcRange.isHolidayWork(), calcRange.getAttdLeave());
		if (frameErrors.size() > 0){
			result.add(new EmployeeDailyPerError(
					AppContexts.user().companyCode(), employeeId, date,
					new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), new ArrayList<>()));
		}
		// 【参考】　勤怠項目IDを設定する場合
//		boolean outOvertime = false;
//		boolean outOvertimeMn = false;
//		for (DeclareTimeFrameError frameError : frameErrors){
//			// 社員の日別実績のエラーを作成する
//			switch(frameError){
//			case EARLY_OT:
//			case OVERTIME:
//				if (outOvertime) break;
//				outOvertime = true;
//				if (this.attendanceTimeOfDailyPerformance.isPresent()){
//					AttendanceTimeOfDailyAttendance attdTime = this.attendanceTimeOfDailyPerformance.get();
//					TotalWorkingTime totalWorkTime = attdTime.getActualWorkingTimeOfDaily().getTotalWorkingTime();
//					ExcessOfStatutoryTimeOfDaily notStatTime = totalWorkTime.getExcessOfStatutoryTimeOfDaily();
//					if (notStatTime.getOverTimeWork().isPresent()){
//						OverTimeOfDaily overTime = notStatTime.getOverTimeWork().get();
//						for (OverTimeFrameTime frameTime : overTime.getOverTimeWorkFrameTime()){
//							attdIdDic.findId("残業時間" + frameTime.getOverWorkFrameNo().v())
//								.ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//									AppContexts.user().companyCode(), employeeId, date,
//									new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//							attdIdDic.findId("振替残業時間" + frameTime.getOverWorkFrameNo().v())
//								.ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//									AppContexts.user().companyCode(), employeeId, date,
//									new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//						}
//					}
//				}
//				break;
//				
//			case EARLY_OT_MN:
//			case OVERTIME_MN:
//				if (outOvertimeMn) break;
//				outOvertimeMn = true;
//				attdIdDic.findId("就外残業深夜時間").ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//						AppContexts.user().companyCode(), employeeId, date,
//						new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//				break;
//				
//			case HOLIDAYWORK:
//				if (this.attendanceTimeOfDailyPerformance.isPresent()){
//					AttendanceTimeOfDailyAttendance attdTime = this.attendanceTimeOfDailyPerformance.get();
//					TotalWorkingTime totalWorkTime = attdTime.getActualWorkingTimeOfDaily().getTotalWorkingTime();
//					ExcessOfStatutoryTimeOfDaily notStatTime = totalWorkTime.getExcessOfStatutoryTimeOfDaily();
//					if (notStatTime.getWorkHolidayTime().isPresent()){
//						HolidayWorkTimeOfDaily holidayWork = notStatTime.getWorkHolidayTime().get();
//						for (HolidayWorkFrameTime frameTime : holidayWork.getHolidayWorkFrameTime()){
//							attdIdDic.findId("休出時間" + frameTime.getHolidayFrameNo().v())
//								.ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//									AppContexts.user().companyCode(), employeeId, date,
//									new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//							attdIdDic.findId("振替時間" + frameTime.getHolidayFrameNo().v())
//								.ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//									AppContexts.user().companyCode(), employeeId, date,
//									new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//						}
//					}
//				}
//				break;
//				
//			case HOLIDAYWORK_MN:
//				attdIdDic.findId("法内休出外深夜").ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//						AppContexts.user().companyCode(), employeeId, date,
//						new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//				attdIdDic.findId("法外休出外深夜").ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//						AppContexts.user().companyCode(), employeeId, date,
//						new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//				attdIdDic.findId("就外法外祝日深夜").ifPresent(itemId -> result.add(new EmployeeDailyPerError(
//						AppContexts.user().companyCode(), employeeId, date,
//						new ErrorAlarmWorkRecordCode(fixedErrorAlarmCode.value), itemId)));
//				break;
//			}
//		}
		return result;
	}
	
	/**
	 * 編集状態から「申告反映」のデータを削除する
	 */
	public void removeEditStateForDeclare(){
		this.editState.removeIf(c -> c.getEditStateSetting() == EditStateSetting.DECLARE_APPLICATION);
	}
	
	/**
	 * 申告用編集状態を追加する
	 * @param itemId 勤怠項目ID
	 */
	public void addEditStateForDeclare(Integer itemId){
		// 日別勤怠の編集状態に勤怠項目IDが存在するか確認する
		if(!this.editState.stream().filter(c -> c.getAttendanceItemId() == itemId.intValue()).findFirst().isPresent()){
			// 日別勤怠の編集状態を追加する
			this.editState.add(new EditStateOfDailyAttd(itemId.intValue(), EditStateSetting.DECLARE_APPLICATION));
		}
	}
	
	public IntegrationOfDaily(
			String employeeId,
			GeneralDate ymd,
			WorkInfoOfDailyAttendance workInformation, 
			CalAttrOfDailyAttd calAttr,
			AffiliationInforOfDailyAttd affiliationInfor,
			Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo,
			List<EmployeeDailyPerError> employeeError,
			Optional<OutingTimeOfDailyAttd> outingTime,
			List<BreakTimeOfDailyAttd> breakTime,
			Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave, 
			Optional<ShortTimeOfDailyAttd> shortTime,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate,
			Optional<AnyItemValueOfDailyAttd> anyItemValue,
			List<EditStateOfDailyAttd> editState, 
			Optional<TemporaryTimeOfDailyAttd> tempTime,
			List<RemarksOfDailyAttd> remarks) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.workInformation = workInformation;
		this.calAttr = calAttr;
		this.affiliationInfor = affiliationInfor;
		this.pcLogOnInfo = pcLogOnInfo;
		if(employeeError != null) {
			this.employeeError = new ArrayList<>(employeeError);
		}
		else {
			this.employeeError = Collections.emptyList();
		}
		this.outingTime = outingTime;
		this.breakTime = breakTime;
		this.attendanceTimeOfDailyPerformance = attendanceTimeOfDailyPerformance;
		this.attendanceLeave = attendanceLeave;
		this.shortTime = shortTime;
		this.specDateAttr = specDateAttr;
		this.attendanceLeavingGate = attendanceLeavingGate;
		this.anyItemValue = anyItemValue;
		this.editState = editState;
		this.tempTime = tempTime;
		this.remarks = remarks;
	}

	public IntegrationOfDaily(IntegrationOfDaily daily) {
		this.employeeId = daily.getEmployeeId();
		this.ymd = daily.getYmd();
		this.workInformation = daily.getWorkInformation();
		this.calAttr = daily.getCalAttr();
		this.affiliationInfor = daily.getAffiliationInfor();
		this.pcLogOnInfo = daily.getPcLogOnInfo();
		if (daily.getEmployeeError() != null) {
			this.employeeError = new ArrayList<>(daily.getEmployeeError());
		} else {
			this.employeeError = Collections.emptyList();
		}
		this.outingTime = daily.getOutingTime();
		this.breakTime = daily.getBreakTime();
		this.attendanceTimeOfDailyPerformance = daily.getAttendanceTimeOfDailyPerformance();
		this.attendanceLeave = daily.getAttendanceLeave();
		this.shortTime = daily.getShortTime();
		this.specDateAttr = daily.getSpecDateAttr();
		this.attendanceLeavingGate = daily.getAttendanceLeavingGate();
		this.anyItemValue = daily.getAnyItemValue();
		this.editState = daily.getEditState();
		this.tempTime = daily.getTempTime();
		this.remarks = daily.getRemarks();
		this.ouenTimeSheet = daily.getOuenTimeSheet();
		this.ouenTime = daily.getOuenTime();
	}
	
	public IntegrationOfDaily getDomain() {
		return this;
	}
	
	public void setDomain(IntegrationOfDaily daily) {
		this.employeeId = daily.getEmployeeId();
		this.ymd = daily.getYmd();
		this.workInformation = daily.getWorkInformation();
		this.calAttr = daily.getCalAttr();
		this.affiliationInfor = daily.getAffiliationInfor();
		this.pcLogOnInfo = daily.getPcLogOnInfo();
		if (daily.getEmployeeError() != null) {
			this.employeeError = new ArrayList<>(daily.getEmployeeError());
		} else {
			this.employeeError = Collections.emptyList();
		}
		this.outingTime = daily.getOutingTime();
		this.breakTime = daily.getBreakTime();
		this.attendanceTimeOfDailyPerformance = daily.getAttendanceTimeOfDailyPerformance();
		this.attendanceLeave = daily.getAttendanceLeave();
		this.shortTime = daily.getShortTime();
		this.specDateAttr = daily.getSpecDateAttr();
		this.attendanceLeavingGate = daily.getAttendanceLeavingGate();
		this.anyItemValue = daily.getAnyItemValue();
		this.editState = daily.getEditState();
		this.tempTime = daily.getTempTime();
		this.remarks = daily.getRemarks();
	}
}
