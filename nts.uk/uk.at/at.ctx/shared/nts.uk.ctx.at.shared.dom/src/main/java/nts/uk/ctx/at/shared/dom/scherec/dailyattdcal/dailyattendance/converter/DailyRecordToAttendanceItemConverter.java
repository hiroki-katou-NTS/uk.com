package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
//move at record -> at shared
public interface DailyRecordToAttendanceItemConverter {

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(Collection<Integer> attendanceItemIds);
	
	void merge(ItemValue value);
	
	void merge(Collection<ItemValue> values);
		
	IntegrationOfDaily toDomain();
	
	DailyRecordToAttendanceItemConverter setData(IntegrationOfDaily domain);

	DailyRecordToAttendanceItemConverter withWorkInfo(String employeeId,GeneralDate ymd, WorkInfoOfDailyAttendance domain);

	DailyRecordToAttendanceItemConverter withCalcAttr(String employeeId, GeneralDate ymd, CalAttrOfDailyAttd domain);

//	DailyRecordToAttendanceItemConverter withBusinessType(WorkTypeOfDailyPerformance domain);
	
	DailyRecordToAttendanceItemConverter withAffiliationInfo(String employeeId, GeneralDate ymd, AffiliationInforOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withEmployeeErrors(List<EmployeeDailyPerError> domain);

	DailyRecordToAttendanceItemConverter withOutingTime(String employeeId, GeneralDate ymd, OutingTimeOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withBreakTime(String employeeId, GeneralDate ymd, BreakTimeOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withAttendanceTime(String employeeId, GeneralDate ymd, AttendanceTimeOfDailyAttendance domain);

//	DailyRecordToAttendanceItemConverter withAttendanceTimeByWork(AttendanceTimeByWorkOfDaily domain);

	DailyRecordToAttendanceItemConverter withTimeLeaving(String employeeId, GeneralDate ymd, TimeLeavingOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withShortTime(String employeeId, GeneralDate ymd, ShortTimeOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withSpecificDateAttr(String employeeId, GeneralDate ymd, SpecificDateAttrOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withAttendanceLeavingGate(String employeeId, GeneralDate ymd, AttendanceLeavingGateOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withAnyItems(String employeeId, GeneralDate ymd, AnyItemValueOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withEditStates(String employeeId, GeneralDate ymd, EditStateOfDailyAttd domain);

	DailyRecordToAttendanceItemConverter withEditStates(String employeeId, GeneralDate ymd, List<EditStateOfDailyAttd> domain);

	DailyRecordToAttendanceItemConverter withTemporaryTime(String employeeId, GeneralDate ymd, TemporaryTimeOfDailyAttd domain);
	
	DailyRecordToAttendanceItemConverter withPCLogInfo(String employeeId, GeneralDate ymd, PCLogOnInfoOfDailyAttd domain);
	
	DailyRecordToAttendanceItemConverter withRemark(String employeeId, GeneralDate ymd, RemarksOfDailyAttd domain);
	
	DailyRecordToAttendanceItemConverter withRemarks(String employeeId, GeneralDate ymd, List<RemarksOfDailyAttd> domain);
	
	DailyRecordToAttendanceItemConverter withSnapshot(String employeeId, GeneralDate ymd, SnapShot domain);

	DailyRecordToAttendanceItemConverter employeeId(String employeeId);

	DailyRecordToAttendanceItemConverter workingDate(GeneralDate workingDate);

	DailyRecordToAttendanceItemConverter completed();
	
	WorkInfoOfDailyAttendance workInfo();

	CalAttrOfDailyAttd calcAttr();

//	Optional<WorkTypeOfDailyPerformance> businessType();
	
	AffiliationInforOfDailyAttd affiliationInfo();

	Optional<OutingTimeOfDailyAttd> outingTime();

	Optional<BreakTimeOfDailyAttd> breakTime();

	Optional<AttendanceTimeOfDailyAttendance> attendanceTime();

//	Optional<AttendanceTimeByWorkOfDaily> attendanceTimeByWork();

	Optional<TimeLeavingOfDailyAttd> timeLeaving();

	Optional<ShortTimeOfDailyAttd> shortTime();

	Optional<SpecificDateAttrOfDailyAttd> specificDateAttr();

	Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate();

	Optional<AnyItemValueOfDailyAttd> anyItems();

	List<EditStateOfDailyAttd> editStates();

	Optional<TemporaryTimeOfDailyAttd> temporaryTime();
	
	Optional<PCLogOnInfoOfDailyAttd> pcLogInfo();
	
	List<RemarksOfDailyAttd> remarks();
	
	Optional<SnapShot> snapshot();

}