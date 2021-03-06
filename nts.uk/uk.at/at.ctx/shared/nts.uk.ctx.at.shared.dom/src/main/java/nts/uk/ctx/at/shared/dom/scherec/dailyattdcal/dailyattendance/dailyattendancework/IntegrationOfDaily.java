package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimezoneToUseHourlyHoliday;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.GettingTimeVacactionService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimeVacation;
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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.CheckExcessAtr;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportInfoOfEmployee;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.GetAttendanceItemIdService;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;

/**
 * ????????????(Work)
 * UKDesign.?????????????????????.NittsuSystem.UniversalK.??????.shared(???????????????????????????).??????????????????.????????????.????????????(work).????????????(Work)
 * 
 * ????????????(Work) (old)
 * @author keisuke_hoshina
 *
 */
@Getter
@NoArgsConstructor
public class IntegrationOfDaily {
	//??????ID
	@Setter
	private String employeeId;
	
	//?????????
	@Setter
	private GeneralDate ymd;
	
	//???????????????????????????  (old)
//	private WorkInfoOfDailyPerformance workInformation;
	//????????????: ???????????????????????????
	@Setter
	private WorkInfoOfDailyAttendance workInformation;
	
//	??????????????????????????? (old)
//	private AffiliationInforOfDailyPerfor affiliationInfor;
	//????????????: ???????????????????????????
	@Setter
	private AffiliationInforOfDailyAttd  affiliationInfor;
	
	//??????????????????????????? (old)
//	private CalAttrOfDailyPerformance calAttr;
	//????????????: ???????????????????????????
	@Setter
	private CalAttrOfDailyAttd calAttr;
	
	//??????????????????????????????????????? 
	
	/**-------------Optional--------------**/
	//???????????????????????? (old)
//	private Optional<TimeLeavingOfDailyPerformance> attendanceLeave;
	//?????????: ????????????????????????
	@Setter
	private Optional<TimeLeavingOfDailyAttd> attendanceLeave;
	
	//?????????????????????????????? (old)
//	private List<BreakTimeOfDailyPerformance> breakTime;
	//???????????????: ??????????????????????????????
	@Setter
	private BreakTimeOfDailyAttd breakTime;
	
	//?????????????????????????????? (old)
//	private Optional<OutingTimeOfDailyPerformance> outingTime;
	//???????????????: ??????????????????????????????
	@Setter
	private Optional<OutingTimeOfDailyAttd> outingTime;
	
	//??????????????????????????????????????? (old)
//	private Optional<ShortTimeOfDailyPerformance> shortTime;
	//????????????????????????: ???????????????????????????????????????
	@Setter
	private Optional<ShortTimeOfDailyAttd> shortTime;
	
	//?????????????????????????????? (old)
//	private Optional<TemporaryTimeOfDailyPerformance> tempTime;
	//???????????????: ??????????????????????????????
	@Setter
	private Optional<TemporaryTimeOfDailyAttd> tempTime;
	
	//???????????????????????? (old)
//	private Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate;
	//?????????: ????????????????????????
	@Setter
	private Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate;
	
	//??????????????????????????? (old)
//	private Optional<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailyPerformance;
	//????????????: ???????????????????????????
	@Setter
	private Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance;
	
	@Setter
	//?????????????????????????????? (old)
//	private Optional<SpecificDateAttrOfDailyPerfor> specDateAttr;
	//???????????????: ??????????????????????????????
	private Optional<SpecificDateAttrOfDailyAttd> specDateAttr;
	
	//????????????????????????????????????
	@Setter
	private List<EmployeeDailyPerError> employeeError; 
	//???????????????: ?????????????????????????????? //ph???i chuy???n sang shared ?
	
	//??????????????????????????? (old)
//	private List<EditStateOfDailyPerformance> editState;
	//????????????: ???????????????????????????
	@Setter
	private List<EditStateOfDailyAttd> editState;
	
	//??????????????????????????? (old)
//	private Optional<AnyItemValueOfDaily> anyItemValue;
	//????????????: ???????????????????????????
	@Setter
	private Optional<AnyItemValueOfDailyAttd> anyItemValue;
	
	//????????????????????????????????????
//	private Optional<AttendanceTimeByWorkOfDaily> attendancetimeByWork;
	//?????????????????????: ????????????????????????????????????  - 
		
	//???????????????PC?????????????????? (old)
//	private Optional<PCLogOnInfoOfDaily> pcLogOnInfo;
	//PC??????????????????: ???????????????PC??????????????????
	@Setter 
	private Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo;
	
	//????????????????????? (old)
//	private List<RemarksOfDailyPerform> remarks;
	//??????: ?????????????????????
	@Setter
	private List<RemarksOfDailyAttd> remarks;
	
	/**???????????????????????????*/ 
//	private Optional<WorkTypeOfDailyPerformance> businessType;
	
	@Setter
	/**????????????????????????????????? */
	private List<OuenWorkTimeOfDailyAttendance> ouenTime = new ArrayList<>();
	
	@Setter
	/**????????????: ???????????????????????????????????? */
	private List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet = new ArrayList<>();
	
	private Optional<SnapShot> snapshot;
	
	public void setSnapshot(SnapShot snapshot) {
		this.snapshot = Optional.ofNullable(snapshot);
	}
	
	/**
	 * Constructor
	 * @param workInformation ???????????????????????????
	 * @param calAttr ???????????????????????????
	 * @param affiliationInfor ???????????????????????????
	 * @param pcLogOnInfo ???????????????PC??????????????????
	 * @param employeeError ????????????????????????????????????
	 * @param outingTime ??????????????????????????????
	 * @param breakTime ??????????????????????????????
	 * @param attendanceTimeOfDailyPerformance ???????????????????????????
	 * @param attendanceLeave ????????????????????????
	 * @param shortTime ???????????????????????????????????????
	 * @param specDateAttr ??????????????????????????????
	 * @param attendanceLeavingGate ????????????????????????
	 * @param anyItemValue ???????????????????????????
	 * @param editState ???????????????????????????
	 * @param tempTime ??????????????????????????????
	 * @param ouenTime ?????????????????????????????????
	 * @param ouenTimeSheet ????????????????????????????????????
	 */
	public IntegrationOfDaily(
			WorkInfoOfDailyAttendance workInformation, 
			CalAttrOfDailyAttd calAttr,
			AffiliationInforOfDailyAttd affiliationInfor,
			Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo,
			List<EmployeeDailyPerError> employeeError,
			Optional<OutingTimeOfDailyAttd> outingTime,
			BreakTimeOfDailyAttd breakTime,
			Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave, 
			Optional<ShortTimeOfDailyAttd> shortTime,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate,
			Optional<AnyItemValueOfDailyAttd> anyItemValue,
			List<EditStateOfDailyAttd> editState, 
			Optional<TemporaryTimeOfDailyAttd> tempTime,
			List<RemarksOfDailyAttd> remarks,
			List<OuenWorkTimeOfDailyAttendance> ouenTime,
			List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet,
			Optional<SnapShot> snapshot) {
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
		this.ouenTime = ouenTime;
		this.ouenTimeSheet = ouenTimeSheet;
		this.snapshot = snapshot;
	}

	/**
	 * ?????????????????????????????????
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
	 * ???????????????????????????????????????????????????????????????
	 */
	public void removeEditStateForDeclare(){
		this.editState.removeIf(c -> c.getEditStateSetting() == EditStateSetting.DECLARE_APPLICATION);
	}
	
	/**
	 * ????????????????????????????????????
	 * @param itemId ????????????ID
	 */
	public void addEditStateForDeclare(Integer itemId){
		// ??????????????????????????????????????????ID??????????????????????????????
		if(!this.editState.stream().filter(c -> c.getAttendanceItemId() == itemId.intValue()).findFirst().isPresent()){
			// ??????????????????????????????????????????
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
			BreakTimeOfDailyAttd breakTime,
			Optional<AttendanceTimeOfDailyAttendance> attendanceTimeOfDailyPerformance,
			Optional<TimeLeavingOfDailyAttd> attendanceLeave, 
			Optional<ShortTimeOfDailyAttd> shortTime,
			Optional<SpecificDateAttrOfDailyAttd> specDateAttr,
			Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate,
			Optional<AnyItemValueOfDailyAttd> anyItemValue,
			List<EditStateOfDailyAttd> editState, 
			Optional<TemporaryTimeOfDailyAttd> tempTime,
			List<RemarksOfDailyAttd> remarks,
			List<OuenWorkTimeOfDailyAttendance> ouenTime,
			List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet,
			Optional<SnapShot> snapshot) {
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
		this.ouenTime = ouenTime;
		this.ouenTimeSheet = ouenTimeSheet;
		this.snapshot = snapshot;
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
		this.ouenTime = daily.getOuenTime();
		this.ouenTimeSheet = daily.getOuenTimeSheet();
		this.snapshot = daily.getSnapshot();
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
		this.ouenTime = daily.getOuenTime();
		this.ouenTimeSheet = daily.getOuenTimeSheet();
		this.snapshot = daily.getSnapshot();
	}
	
	/**
	 * ???????????????????????????
	 * @return Map<TimezoneToUseHourlyHoliday, TimeVacation>
	 */
	public Map<TimezoneToUseHourlyHoliday, TimeVacation> getTimeVacation() {
		return GettingTimeVacactionService.get(this.attendanceLeave
				,	this.attendanceTimeOfDailyPerformance
				,	this.outingTime);
	}

	/**
	 * [3] ?????????????????????
	 */
	public void clearEditedStates() {
		this.editState.clear();
	}
	/**
	 * ????????????????????????????????????
	 * @return
	 */
	public SupportInfoOfEmployee getSupportInfoOfEmployee() {
		
		if ( this.ouenTimeSheet.isEmpty() ) {
			
			return SupportInfoOfEmployee.createWithoutSupport(
					new EmployeeId(this.employeeId), 
					this.ymd, 
					this.affiliationInfor.getAffiliationOrg() );
		}
		
		if ( this.ouenTimeSheet.get(0).getSupportType() == SupportType.ALLDAY ) {
			return SupportInfoOfEmployee.createWithAllDaySupport(
					new EmployeeId(this.employeeId), 
					this.ymd, 
					this.affiliationInfor.getAffiliationOrg(),
					this.ouenTimeSheet.get(0).getWorkContent().getWorkplace().getRecipientOrg() );
		} else {
			val recipientList = this.ouenTimeSheet.stream()
					.map(timeSheet -> timeSheet.getWorkContent().getWorkplace().getRecipientOrg())
					.collect(Collectors.toList());
			
			return SupportInfoOfEmployee.createWithTimezoneSupport(
					new EmployeeId(this.employeeId), 
					this.ymd, 
					this.affiliationInfor.getAffiliationOrg(),
					recipientList);
		}
	}
		
	/**
	 * ?????????????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param List<????????????ID>
	 * @return void
	 */
	public void clearEditStateByDeletedTimeSheet(List<Integer> lstExcludedApp) {
		val itemIds = IntStream.range(1, MaximumNumberOfSupport.getMax() + 1).boxed()
				.filter(y -> !this.ouenTimeSheet.stream()
						.filter(x -> x.getTimeSheet().getStartTimeWithDayAttr().isPresent()
								&& x.getTimeSheet().getEndTimeWithDayAttr().isPresent())
						.map(x -> x.getWorkNo().v()).collect(Collectors.toList()).contains(y))
				.map(x -> new SupportFrameNo(x)).collect(Collectors.toList());

		this.editState.removeIf(x -> {
			return GetAttendanceItemIdService.getAttendanceItemIds(itemIds)
					.contains(x.getAttendanceItemId()) && !lstExcludedApp.contains(x.getAttendanceItemId());
		});
	}
	
	/**
	 * ????????????????????????????????????ID???????????????????????????????????????ID???????????????
	 * 
	 * @return List<????????????ID>
	 */
	public List<Integer> getListWplLocationIdFromOuen(){
		return this.ouenTimeSheet.stream().flatMap(x -> {
			return Arrays.asList(CancelAppStamp.createItemId(921, x.getWorkNo().v(), 10),
					CancelAppStamp.createItemId(922, x.getWorkNo().v(), 10)).stream();
		}).collect(Collectors.toList());
	}
}
