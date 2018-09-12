package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.workrecord.AttendanceTimeByWorkOfDaily;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.LogOnInfo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.SystemFixedErrorAlarm;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;

/**
 * 日別実績(Work)
 * @author keisuke_hoshina
 *
 */
@Getter
public class IntegrationOfDaily {
	//日別実績の勤務情報
	@Setter
	private WorkInfoOfDailyPerformance workInformation;
	//日別実績の計算区分
	@Setter
	private CalAttrOfDailyPerformance calAttr;
	//日別実績の所属情報
	private AffiliationInforOfDailyPerfor affiliationInfor;
	
	/**-------------Optional--------------**/
	/**日別実績の勤務種別*/
	private Optional<WorkTypeOfDailyPerformance> businessType;
	
	//日別実績のPCログオン情報
	private Optional<PCLogOnInfoOfDaily> pcLogOnInfo;
	//社員の日別実績エラー一覧
	@Setter
	private List<EmployeeDailyPerError> employeeError;
	//日別実績の外出時間帯
	@Setter
	private Optional<OutingTimeOfDailyPerformance> outingTime;
	//日別実績の休憩時間帯
	@Setter
	private List<BreakTimeOfDailyPerformance> breakTime;
	//日別実績の勤怠時間
	@Setter
	private Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance;
	//日別実績の作業別勤怠時間
	private Optional<AttendanceTimeByWorkOfDaily> attendancetimeByWork;
	//日別実績の出退勤
	@Setter
	private Optional<TimeLeavingOfDailyPerformance> attendanceLeave;
	//日別実績の短時間勤務時間帯
	@Setter
	private Optional<ShortTimeOfDailyPerformance> shortTime;
	//日別実績の特定日区分
	private Optional<SpecificDateAttrOfDailyPerfor> specDateAttr;
	//日別実績の入退門
	private Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate;
	@Setter
	//日別実績の任意項目
	private Optional<AnyItemValueOfDaily> anyItemValue;
	//日別実績の編集状態
	@Setter
	private List<EditStateOfDailyPerformance> editState;
	//日別実績の臨時出退勤
	private Optional<TemporaryTimeOfDailyPerformance> tempTime;
	
	//日別実績の備考
	@Setter
	private List<RemarksOfDailyPerform> remarks;
	
	/**
	 * Constructor
	 * @param workInformation 日別実績の勤務情報
	 * @param calAttr 日別実績の計算区分
	 * @param affiliationInfor 日別実績の所属情報
	 * @param businessType 日別実績の勤務種別
	 * @param pcLogOnInfo 日別実績のPCログオン情報
	 * @param employeeError 社員の日別実績エラー一覧
	 * @param outingTime 日別実績の外出時間帯
	 * @param breakTime 日別実績の休憩時間帯
	 * @param attendanceTimeOfDailyPerformance 日別実績の勤怠時間
	 * @param attendancetimeByWork 日別実績の作業別勤怠時間
	 * @param attendanceLeave 日別実績の出退勤
	 * @param shortTime 日別実績の短時間勤務時間帯
	 * @param specDateAttr 日別実績の特定日区分
	 * @param attendanceLeavingGate 日別実績の入退門
	 * @param anyItemValue 日別実績の任意項目
	 * @param editState 日別実績の編集状態
	 * @param tempTime 日別実績の臨時出退勤
	 */
	public IntegrationOfDaily(WorkInfoOfDailyPerformance workInformation, CalAttrOfDailyPerformance calAttr,
			AffiliationInforOfDailyPerfor affiliationInfor, Optional<WorkTypeOfDailyPerformance> businessType,
			Optional<PCLogOnInfoOfDaily> pcLogOnInfo,
			List<EmployeeDailyPerError> employeeError, Optional<OutingTimeOfDailyPerformance> outingTime,
			List<BreakTimeOfDailyPerformance> breakTime,
			Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance,
			Optional<AttendanceTimeByWorkOfDaily> attendancetimeByWork,
			Optional<TimeLeavingOfDailyPerformance> attendanceLeave, Optional<ShortTimeOfDailyPerformance> shortTime,
			Optional<SpecificDateAttrOfDailyPerfor> specDateAttr,
			Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate, Optional<AnyItemValueOfDaily> anyItemValue,
			List<EditStateOfDailyPerformance> editState, Optional<TemporaryTimeOfDailyPerformance> tempTime,
			List<RemarksOfDailyPerform> remarks) {
		super();
		this.businessType = businessType;
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
		this.attendancetimeByWork = attendancetimeByWork;
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
	
}
