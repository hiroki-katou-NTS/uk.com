package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import mockit.Mocked;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnInfoOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.StayingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;

public class CountWorkingTimeOfPerCtgServiceHelper {
	@Mocked
	static WorkInfoOfDailyAttendance workInformation;

	@Mocked
	static CalAttrOfDailyAttd calAttr;

	@Mocked
	static AffiliationInforOfDailyAttd affiliationInfor;

	@Mocked
	static Optional<PCLogOnInfoOfDailyAttd> pcLogOnInfo;

	@Mocked
	static List<EmployeeDailyPerError> employeeError;

	@Mocked
	static Optional<OutingTimeOfDailyAttd> outingTime;

	@Mocked
	static BreakTimeOfDailyAttd breakTime;

	@Mocked
	static Optional<TimeLeavingOfDailyAttd> attendanceLeave;

	@Mocked
	static Optional<ShortTimeOfDailyAttd> shortTime;

	@Mocked
	static Optional<SpecificDateAttrOfDailyAttd> specDateAttr;

	@Mocked
	static Optional<AttendanceLeavingGateOfDailyAttd> attendanceLeavingGate;

	@Mocked
	static Optional<AnyItemValueOfDailyAttd> anyItemValue;

	@Mocked
	static List<EditStateOfDailyAttd> editState;

	@Mocked
	static Optional<TemporaryTimeOfDailyAttd> tempTime;

	@Mocked
	static List<RemarksOfDailyAttd> remarks;

	@Mocked
	static Optional<SnapShot> snapshot;
	
	public static IntegrationOfDaily createDailyWorks(String sid, GeneralDate date, int workTime, Integer premiumTimeNo,
			int premitumTime) {
		return new IntegrationOfDaily(sid, date, workInformation, calAttr, affiliationInfor, pcLogOnInfo, employeeError
				,	outingTime, breakTime
				,	Optional.of(AttendanceTimeOfDailyAttendanceHelp.createDailyAttendance(workTime, premiumTimeNo
						,	premitumTime))
				,	attendanceLeave, shortTime, specDateAttr, attendanceLeavingGate, anyItemValue, editState, tempTime
				,	remarks, snapshot);
	}

	public static class AttendanceTimeOfDailyAttendanceHelp {

		@Mocked
		static WorkScheduleTimeOfDaily workScheduleTimeOfDaily;

		@Mocked
		static StayingTimeOfDaily stayingTime;

		@Mocked
		static AttendanceTimeOfExistMinus unEmployedTime;

		@Mocked
		static AttendanceTimeOfExistMinus budgetTimeVariance;

		@Mocked
		static MedicalCareTimeOfDaily medicalCareTime;

		public static AttendanceTimeOfDailyAttendance createDailyAttendance(int workTime, Integer premiumTimeNo,
				int premitumTime) {
			return new AttendanceTimeOfDailyAttendance(
					workScheduleTimeOfDaily, ActualWorkingTimeOfDailyHelper.createActualWorkingTimeOfDaily(workTime,
							premiumTimeNo, premitumTime),
					stayingTime, unEmployedTime, budgetTimeVariance, medicalCareTime);
		}
	}

	public static class ActualWorkingTimeOfDailyHelper {

		@Mocked
		private static AttendanceTime constraintDiffTime;

		@Mocked
		private static ConstraintTime constraintTime;

		@Mocked
		private static AttendanceTime timeDiff;

		@Mocked
		private static DivergenceTimeOfDaily divTime;

		public static ActualWorkingTimeOfDaily createActualWorkingTimeOfDaily(int workTime, Integer premiumTimeNo,
				int premitumTime) {

			return new ActualWorkingTimeOfDaily(constraintDiffTime, constraintTime, timeDiff,
					TotalWorkingTimeHelper.createTotalWorkingTime(workTime), divTime,
					PremiumTimeOfDailyPerformanceHepler.createPremiumTimeOfDaily(premiumTimeNo, premitumTime));
		}

	}

	public static class TotalWorkingTimeHelper {
		@Mocked
		static AttendanceTime totalTime;

		@Mocked
		static AttendanceTime totalCalcTime;

		@Mocked
		static AttendanceTime actualTime;

		@Mocked
		static AttendanceTime calcDiffTime = new AttendanceTime(0);

		@Mocked
		static ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily;

		@Mocked
		static List<LateTimeOfDaily> lateTimeOfDaily = Collections.emptyList();

		@Mocked
		static List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = Collections.emptyList();

		@Mocked
		static BreakTimeOfDaily breakTimeOfDaily;

		@Mocked
		static List<OutingTimeOfDaily> outingTimeOfDailyPerformance;

		@Mocked
		static RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor;

		@Mocked
		static WorkTimes workTimes;

		@Mocked
		static TemporaryTimeOfDaily temporaryTime;

		@Mocked
		static ShortWorkTimeOfDaily shortTime;

		@Mocked
		static HolidayOfDaily holidayOfDaily;

		@Mocked
		static AttendanceTime vacationAddTime = new AttendanceTime(0);

		@Mocked
		static IntervalTimeOfDaily intervalTime;

		public static TotalWorkingTime createTotalWorkingTime(int workTime) {
			return new TotalWorkingTime(totalTime, totalCalcTime, actualTime,
					WithinStatutoryTimeOfDailyHepler.createWithinStatutoryTimeOfDaily(workTime),
					excessOfStatutoryTimeOfDaily, lateTimeOfDaily, leaveEarlyTimeOfDaily, breakTimeOfDaily,
					outingTimeOfDailyPerformance, raiseSalaryTimeOfDailyPerfor, workTimes, temporaryTime, shortTime,
					holidayOfDaily, intervalTime);
		}
	}

	public static class WithinStatutoryTimeOfDailyHepler {

		@Mocked
		static AttendanceTime actualTime;

		@Mocked
		static AttendanceTime premiumTime;

		@Mocked
		static WithinStatutoryMidNightTime midNightTime;

		public static WithinStatutoryTimeOfDaily createWithinStatutoryTimeOfDaily(int workTime) {
			return new WithinStatutoryTimeOfDaily(new AttendanceTime(workTime), actualTime, premiumTime, midNightTime);
		}
	}

	public static class PremiumTimeOfDailyPerformanceHepler {

		@Mocked
		static AttendanceAmountDaily premiumAmount;

		public static PremiumTimeOfDailyPerformance createPremiumTimeOfDaily(Integer premiumTimeNo, int premitumTime) {
			return new PremiumTimeOfDailyPerformance(Arrays.asList(createPremiumTime(premitumTime, premitumTime)));
		}

		public static PremiumTime createPremiumTime(Integer premiumTimeNo, int premitumTime) {
			return new PremiumTime(premiumTimeNo, new AttendanceTime(premitumTime), premiumAmount);
		}
	}
}
