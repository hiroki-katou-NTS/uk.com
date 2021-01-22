package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TemporaryTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
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
/**
 * UTコード
 * 個人計の労働時間カテゴリを集計する
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class CountWorkingTimeOfPerCtgServiceTest {
	
	/**
	 * input: 日別勤怠リスト: empty
	 * output: 期待値：　empty
	 */
	@Test
	public void countWorkingTimeOfPer_empty() {
		
		val result = CountWorkingTimeOfPerCtgService.get(Collections.emptyList());
		
		assertThat(result).isEmpty();
	
	}
	
	/**
	 * input: 日別勤怠リスト: [sid_1, sid_2, sid_3]
	 */
	@Test
	public void countWorkingTimeOfPer(@Mocked AttendanceTimeOfDailyAttendance attendance_1
			, @Mocked AttendanceTimeOfDailyAttendance attendance_2){
		
		val dailyWorks = Arrays.asList(IntegrationOfDailyHelper.createDailyWorks("sid_1", Optional.of(attendance_1))
				, IntegrationOfDailyHelper.createDailyWorks("sid_2", Optional.of(attendance_2))
				, IntegrationOfDailyHelper.createDailyWorks("sid_3", Optional.empty()));
		
		new MockUp<AttendanceTimesForAggregation>() {
			@Mock
			private BigDecimal getTime(AttendanceTimesForAggregation target, AttendanceTimeOfDailyAttendance dailyAttendance) {
				return new BigDecimal(540);
			}
		};
		
		val result = CountWorkingTimeOfPerCtgService.get(dailyWorks);
		
		assertThat(result).hasSize(3);
		
		assertThat(result.keySet()).containsAll(Arrays.asList("sid_1", "sid_2", "sid_3"));
		
		Map<AttendanceTimesForAggregation, BigDecimal> value1 = result.get("sid_1");
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey().getValue(), d -> d.getValue())
				.contains(
						  tuple(0, new BigDecimal(540))
						, tuple(1, new BigDecimal(540))
						, tuple(2, new BigDecimal(540)));
		
		Map<AttendanceTimesForAggregation, BigDecimal> value2 = result.get("sid_1");
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey().getValue(), d -> d.getValue())
					.contains(
						  tuple(0, new BigDecimal(540))
						, tuple(1, new BigDecimal(540))
						, tuple(2, new BigDecimal(540)));
		
		Map<AttendanceTimesForAggregation, BigDecimal> value3 = result.get("sid_3");
		assertThat(value3.entrySet()).isEmpty();
	
	}
	
	public static class IntegrationOfDailyHelper {
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
		
		public static IntegrationOfDaily createDailyWorks(String sid, Optional<AttendanceTimeOfDailyAttendance> attendance) {
			return new IntegrationOfDaily(
					sid,
					GeneralDate.today(),
					workInformation, 
					calAttr,
					affiliationInfor,
					pcLogOnInfo,
					employeeError,
					outingTime,
					breakTime,
					attendance,
					attendanceLeave, 
					shortTime,
					specDateAttr,
					attendanceLeavingGate,
					anyItemValue,
					editState, 
					tempTime,
					remarks,
					snapshot);
		}
	}
}
