package nts.uk.ctx.at.shared.dom.dailyattdcal.converter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.TimeLeavingOfDailyPerformance;
//move at record -> at shared
public interface DailyRecordToAttendanceItemConverter {

	Optional<ItemValue> convert(int attendanceItemId);

	List<ItemValue> convert(Collection<Integer> attendanceItemIds);
	
	void merge(ItemValue value);
	
	void merge(Collection<ItemValue> values);
		
	IntegrationOfDaily toDomain();
	
	DailyRecordToAttendanceItemConverter setData(IntegrationOfDaily domain);

	DailyRecordToAttendanceItemConverter withWorkInfo(WorkInfoOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withCalcAttr(CalAttrOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withBusinessType(WorkTypeOfDailyPerformance domain);
	
	DailyRecordToAttendanceItemConverter withAffiliationInfo(AffiliationInforOfDailyPerfor domain);

	DailyRecordToAttendanceItemConverter withEmployeeErrors(List<EmployeeDailyPerError> domain);

	DailyRecordToAttendanceItemConverter withOutingTime(OutingTimeOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withBreakTime(BreakTimeOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withBreakTime(List<BreakTimeOfDailyPerformance> domain);

	DailyRecordToAttendanceItemConverter withAttendanceTime(AttendanceTimeOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withAttendanceTimeByWork(AttendanceTimeByWorkOfDaily domain);

	DailyRecordToAttendanceItemConverter withTimeLeaving(TimeLeavingOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withShortTime(ShortTimeOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withSpecificDateAttr(SpecificDateAttrOfDailyPerfor domain);

	DailyRecordToAttendanceItemConverter withAttendanceLeavingGate(AttendanceLeavingGateOfDaily domain);

	DailyRecordToAttendanceItemConverter withAnyItems(AnyItemValueOfDaily domain);

	DailyRecordToAttendanceItemConverter withEditStates(EditStateOfDailyPerformance domain);

	DailyRecordToAttendanceItemConverter withEditStates(List<EditStateOfDailyPerformance> domain);

	DailyRecordToAttendanceItemConverter withTemporaryTime(TemporaryTimeOfDailyPerformance domain);
	
	DailyRecordToAttendanceItemConverter withPCLogInfo(PCLogOnInfoOfDaily domain);
	
	DailyRecordToAttendanceItemConverter withRemark(RemarksOfDailyPerform domain);
	
	DailyRecordToAttendanceItemConverter withRemarks(List<RemarksOfDailyPerform> domain);

	DailyRecordToAttendanceItemConverter employeeId(String employeeId);

	DailyRecordToAttendanceItemConverter workingDate(GeneralDate workingDate);

	DailyRecordToAttendanceItemConverter completed();
	
	WorkInfoOfDailyPerformance workInfo();

	CalAttrOfDailyPerformance calcAttr();

	Optional<WorkTypeOfDailyPerformance> businessType();
	
	AffiliationInforOfDailyPerfor affiliationInfo();

	Optional<OutingTimeOfDailyPerformance> outingTime();

	List<BreakTimeOfDailyPerformance> breakTime();

	Optional<AttendanceTimeOfDailyPerformance> attendanceTime();

	Optional<AttendanceTimeByWorkOfDaily> attendanceTimeByWork();

	Optional<TimeLeavingOfDailyPerformance> timeLeaving();

	Optional<ShortTimeOfDailyPerformance> shortTime();

	Optional<SpecificDateAttrOfDailyPerfor> specificDateAttr();

	Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGate();

	Optional<AnyItemValueOfDaily> anyItems();

	List<EditStateOfDailyPerformance> editStates();

	Optional<TemporaryTimeOfDailyPerformance> temporaryTime();
	
	Optional<PCLogOnInfoOfDaily> pcLogInfo();
	
	List<RemarksOfDailyPerform> remarks();

}