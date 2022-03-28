package nts.uk.ctx.at.record.dom.jobmanagement.manhourinput;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourinput.OvertimeLeaveEncourageConfirmationService.Require1;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author tutt
 *
 */
@RunWith(JMockit.class)
public class OvertimeLeaveEncourageConfirmationServiceTest {

	@Injectable
	private Require1 require1;

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is true
	// $基準終了 < $比較終了
	// if $申請種類 isPresent
	@Test
	public void check1() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 10, 20);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 40);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(40))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(70))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(25))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

				require1.toDecide("companyId", "employeeId", GeneralDate.ymd(2021, 12, 15),
						new WorkTypeCode("workTypeCode"));
				result = Optional.of(ApplicationTypeShare.OVER_TIME_APPLICATION);
			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(expectedResult.get(0).getAppType()).isEqualTo(actualResult.get(0).getAppType());
		assertThat(expectedResult.get(0).getDate()).isEqualTo(actualResult.get(0).getDate());
	}

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is true
	// $基準開始 > $比較開始
	// if $申請種類 isPresent
	@Test
	public void check2() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 50, 30);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 60);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(40))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(20))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(18))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(25))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

				require1.toDecide("companyId", "employeeId", GeneralDate.ymd(2021, 12, 15),
						new WorkTypeCode("workTypeCode"));
				result = Optional.of(ApplicationTypeShare.OVER_TIME_APPLICATION);
			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(expectedResult.get(0).getAppType()).isEqualTo(actualResult.get(0).getAppType());
		assertThat(expectedResult.get(0).getDate()).isEqualTo(actualResult.get(0).getDate());
	}

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is true
	// $基準開始.isEmpty AND $比較開始.isPresent
	// if $申請種類 isPresent
	@Test
	public void check3() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 50, 25);
		ts1.setAttendance(null);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 45);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(20))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(55))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(18))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

				require1.toDecide("companyId", "employeeId", GeneralDate.ymd(2021, 12, 15),
						new WorkTypeCode("workTypeCode"));
				result = Optional.of(ApplicationTypeShare.OVER_TIME_APPLICATION);
			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(expectedResult.get(0).getAppType()).isEqualTo(actualResult.get(0).getAppType());
		assertThat(expectedResult.get(0).getDate()).isEqualTo(actualResult.get(0).getDate());
	}

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is true
	// $基準終了.isEmpty AND $比較終了.isPresent
	// if $申請種類 isPresent
	@Test
	public void check4() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 50, 20);
		ts1.setLeaveWork(null);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 40);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(70))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(55))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(18))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

				require1.toDecide("companyId", "employeeId", GeneralDate.ymd(2021, 12, 15),
						new WorkTypeCode("workTypeCode"));
				result = Optional.of(ApplicationTypeShare.OVER_TIME_APPLICATION);
			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(expectedResult.get(0).getAppType()).isEqualTo(actualResult.get(0).getAppType());
		assertThat(expectedResult.get(0).getDate()).isEqualTo(actualResult.get(0).getDate());
	}

	// $処理日リスト isEmpty
	@Test
	public void check5() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 50, 20);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 40);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(40))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(70))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(25))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 14));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));
			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(actualResult.size()).isEqualTo(0);

	}

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is false
	// ($基準開始.isEmpty AND $比較開始.isPresent) is false
	// ($基準終了.isEmpty AND $比較終了.isPresent) is false
	// $基準開始 > $比較開始  is false
	// $基準終了 < $比較終了 is false
	@Test
	public void check6() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 14, 70);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 3, 40);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(40))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(10))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(10))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

			}
		};
		List<EncouragedTargetApplication> expectedResult = new ArrayList<>();
		expectedResult.add(new EncouragedTargetApplication(GeneralDate.ymd(2021, 12, 15),
				ApplicationTypeShare.OVER_TIME_APPLICATION));

		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(actualResult.size()).isEqualTo(0);
	}

	// $処理日リスト is not empty
	// if [prv-1] 申請を促す必要か($対象勤務情報,$対象応援作業) is true
	// if $申請種類 is not Present
	@Test
	public void check7() {
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();

		ScheduleTimeSheet ts1 = new ScheduleTimeSheet(1, 50, 20);
		ScheduleTimeSheet ts2 = new ScheduleTimeSheet(2, 30, 40);

		scheduleTimeSheets.add(ts1);
		scheduleTimeSheets.add(ts2);

		WorkInfoOfDailyAttendance workInformation = new WorkInfoOfDailyAttendance(
				new WorkInformation("workTypeCode", "workTimeCode"), null, null, null, null, scheduleTimeSheets,
				Optional.empty());

		WorkInfoOfDailyPerformance workInfo = new WorkInfoOfDailyPerformance("employeeId",
				GeneralDate.ymd(2021, 12, 15), workInformation);

		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();

		TimeSheetOfAttendanceEachOuenSheet timeSheet1 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(40))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(70))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet2 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(15))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(25))));

		TimeSheetOfAttendanceEachOuenSheet timeSheet3 = TimeSheetOfAttendanceEachOuenSheet.create(null,
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(50))),
				Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(60))));

		OuenWorkTimeSheetOfDailyAttendance att1 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet1,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att2 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet2,
				Optional.empty());

		OuenWorkTimeSheetOfDailyAttendance att3 = OuenWorkTimeSheetOfDailyAttendance.create(null, null, timeSheet3,
				Optional.empty());

		ouenTimeSheets.add(att1);
		ouenTimeSheets.add(att2);
		ouenTimeSheets.add(att3);

		OuenWorkTimeSheetOfDaily ouenTask = new OuenWorkTimeSheetOfDaily("employeeId", GeneralDate.ymd(2021, 12, 15),
				ouenTimeSheets);

		List<WorkInfoOfDailyPerformance> workInfos = new ArrayList<>();
		workInfos.add(workInfo);

		List<OuenWorkTimeSheetOfDaily> ouenTasks = new ArrayList<>();
		ouenTasks.add(ouenTask);

		List<GeneralDate> inputDates = new ArrayList<>();
		inputDates.add(GeneralDate.ymd(2021, 12, 15));

		new Expectations() {
			{
				require1.getPeriod(anyString, GeneralDate.today());
				result = new DatePeriod(GeneralDate.ymd(2021, 12, 15), GeneralDate.ymd(2021, 12, 19));

				require1.findByListDate(anyString, inputDates);
				result = workInfos;

				require1.get(anyString, inputDates);
				result = ouenTasks;

				require1.toDecide("companyId", "employeeId", GeneralDate.ymd(2021, 12, 15),
						new WorkTypeCode("workTypeCode"));
				result = Optional.empty();
			}
		};
		List<EncouragedTargetApplication> actualResult = OvertimeLeaveEncourageConfirmationService.check(require1,
				"companyId", "employeeId", inputDates);

		assertThat(actualResult.size()).isEqualTo(0);
	}

}
