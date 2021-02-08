package nts.uk.ctx.at.record.dom.dailyresult.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.dailyresult.service.JudgingStatusDomainService.Require;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class JudgingStatusDomainServiceTest {

	@Injectable
	private Require require;

	private Optional<WorkInfoOfDailyPerformance> getR1Result(NotUseAttribute attr) {
		WorkInformation workInformation = new WorkInformation("001", "002");

		WorkInfoOfDailyAttendance workInfoOfDailyAttendance = new WorkInfoOfDailyAttendance();
		workInfoOfDailyAttendance.setRecordInfo(workInformation);
		workInfoOfDailyAttendance.setGoStraightAtr(attr);

		WorkInfoOfDailyPerformance WorkInfoOfDailyPerformance = new WorkInfoOfDailyPerformance();
		WorkInfoOfDailyPerformance.setWorkInformation(workInfoOfDailyAttendance);

		return Optional.ofNullable(WorkInfoOfDailyPerformance);
	}

	private Optional<TimeLeavingOfDailyPerformance> getR2Result(Integer nowAttendance, Integer nowLeave, Integer workNo) {
		WorkTimeInformation workTimeInformationAttendance = new WorkTimeInformation();
		workTimeInformationAttendance
				.setTimeWithDay(Optional.ofNullable(nowAttendance == null ? null : new TimeWithDayAttr(nowAttendance)));

		WorkStamp workStampAttendance = new WorkStamp();
		workStampAttendance.setTimeDay(workTimeInformationAttendance);

		TimeActualStamp timeActualStampAttendance = new TimeActualStamp();
		timeActualStampAttendance.setStamp(Optional.ofNullable(workStampAttendance));

		WorkTimeInformation workTimeInformationLeave = new WorkTimeInformation();
		workTimeInformationLeave
				.setTimeWithDay(Optional.ofNullable(nowLeave == null ? null : new TimeWithDayAttr(nowLeave)));

		WorkStamp workStampLeave = new WorkStamp();
		workStampLeave.setTimeDay(workTimeInformationLeave);

		TimeActualStamp timeActualStampLeave = new TimeActualStamp();
		timeActualStampLeave.setStamp(Optional.ofNullable(workStampLeave));

		TimeLeavingWork timeLeavingWork1 = new TimeLeavingWork();
		timeLeavingWork1.setWorkNo(new WorkNo(workNo));
		timeLeavingWork1.setAttendanceStamp(Optional.ofNullable(timeActualStampAttendance));
		timeLeavingWork1.setLeaveStamp(Optional.ofNullable(timeActualStampLeave));
		
		TimeLeavingOfDailyAttd timeLeave = new TimeLeavingOfDailyAttd();
		List<TimeLeavingWork> list = new ArrayList<>();
		list.add(timeLeavingWork1);
		timeLeave.setTimeLeavingWorks(list);
		timeLeave.setWorkTimes(new WorkTimes(1));

		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = new TimeLeavingOfDailyPerformance();
		timeLeavingOfDailyPerformance.setEmployeeId("employeeId");
		timeLeavingOfDailyPerformance.setYmd(GeneralDate.today());
		timeLeavingOfDailyPerformance.setAttendance(timeLeave);

		return Optional.ofNullable(timeLeavingOfDailyPerformance);
	}

	private Optional<WorkType> getR4Result(WorkTypeUnit workTypeUnit, WorkTypeClassification oneDay,
			WorkTypeClassification morning, WorkTypeClassification afternoon) {
		DailyWork dailyWork = new DailyWork();
		dailyWork.setWorkTypeUnit(workTypeUnit);
		dailyWork.setOneDay(oneDay);
		dailyWork.setMorning(morning);
		dailyWork.setAfternoon(afternoon);

		WorkType workType = new WorkType();
		workType.setWorkTypeCode(new WorkTypeCode("001"));
		workType.setDailyWork(dailyWork);
		return Optional.ofNullable(workType);
	}
	
	@Before
	public void before() {
		JudgingStatusDomainService.clearStaticVariable();
	}

	/**
	 * case 1
	 * 
	 * $退勤時刻 = 日時＃今()
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest1_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(0, now, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_HOME)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 1
	 * 
	 * $退勤時刻 < 日時＃今()
	 * $勤務が出勤ですか　＝　Not Empty
	 */
	@Test
	public void JudgingStatusTest1_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();
		
		//[R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(0, now - 1000, 1);
		
		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
				
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_HOME)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == する
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest2_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == する
	 * $勤務が出勤ですか　＝　Not Empty
	 */
	@Test
	public void JudgingStatusTest2_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, now + 1000, 1);
		
		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 ＞ 日時＃今()
	 * $直行区分 == する
	 */
	@Test
	public void JudgingStatusTest2_3() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 ＞ 日時＃今()
	 * $直行区分 == Empty
	 */
	@Test
	public void JudgingStatusTest2_4() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isNotEqualTo(expected);
	}

	
	/**
	 * case 3
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == しない
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest3_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 3
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == しない
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest3_3() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 3
	 * 
	 * $出勤時刻 > 日時＃今()
	 * $直行区分 == しない
	 */
	@Test
	public void JudgingStatusTest3_2() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 4
	 * 
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest4_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now - 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 4
	 * 
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest4_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		//[R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now - 1000, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 5
	 * 
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest5_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 5
	 * 
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest5_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		//[R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}
	
	
	/**
	 * case 6
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == する
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest6_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, null, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 6
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $直行区分 == する
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest6_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	
	/**
	 * case 6
	 * 
	 * $出勤時刻 > 日時＃今()
	 * $直行区分 == する
	 */
	@Test
	public void JudgingStatusTest6_3() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, null, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 6
	 * 
	 * $出勤時刻 > 日時＃今()
	 * $直行区分 == Empty
	 */
	@Test
	public void JudgingStatusTest6_4() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, null, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.GO_OUT)
				.workingNow(false).build();

		// then
		assertThat(result).isNotEqualTo(expected);
	}

	/**
	 * case 7
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest7_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, null, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}
	
	/**
	 * case 7
	 * 
	 * $出勤時刻 = 日時＃今()
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest7_3() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 7
	 * 
	 * $出勤時刻 > 日時＃今()
	 */
	@Test
	public void JudgingStatusTest7_2() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now + 1000, null, 1);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 8
	 * 
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest8_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now - 1000, null, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 8
	 * 
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest8_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		//[R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(now - 1000, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder().attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false).build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	
	/**
	 * case 9
	 * 
	 * $勤務が出勤ですか　＝　Empty
	 */
	@Test
	public void JudgingStatusTest9_1() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 9
	 * 
	 * $勤務が出勤ですか　＝ Not　Empty
	 */
	@Test
	public void JudgingStatusTest9_2() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		String code = "001";
		
		//[R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);
		
		//[R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure, 
				WorkTypeClassification.Closure
				);
		
		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(false)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	
	/**
	 * case 10
	 * 
	 * 1日場合 IN 「出勤、休日出勤、振出、連続勤務」
	 */
	@Test
	public void JudgingStatusTest10_1() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, null, 0);

		// [R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.Attendance,
				WorkTypeClassification.Attendance, 
				WorkTypeClassification.Attendance);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(true)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 10
	 * 
	 * 午前と午後の場合 午前 IN 「出勤、休日出勤、振出、連続勤務」 午後 NOT IN 「出勤、休日出勤、振出、連続勤務」
	 */
	@Test
	public void JudgingStatusTest10_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.MonringAndAfternoon,
				WorkTypeClassification.AnnualHoliday, 
				WorkTypeClassification.Attendance,
				WorkTypeClassification.AnnualHoliday);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(true)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 10
	 * 
	 * 午前と午後の場合 午前 NOT IN 「出勤、休日出勤、振出、連続勤務」 午後 IN 「出勤、休日出勤、振出、連続勤務」
	 */
	@Test
	public void JudgingStatusTest10_3() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.MonringAndAfternoon,
				WorkTypeClassification.AnnualHoliday, 
				WorkTypeClassification.AnnualHoliday,
				WorkTypeClassification.Attendance);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.NOT_PRESENT)
				.workingNow(true)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 11
	 * 
	 * 1日場合 NOT IN 「出勤、休日出勤、振出、連続勤務」
	 */
	@Test
	public void JudgingStatusTest11_1() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.OneDay, 
				WorkTypeClassification.AnnualHoliday,
				WorkTypeClassification.AnnualHoliday, 
				WorkTypeClassification.AnnualHoliday);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.HOLIDAY)
				.workingNow(false)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 11
	 * 
	 * 午前と午後の場合 午前 NOT IN 「出勤、休日出勤、振出、連続勤務」 午後 NOT IN 「出勤、休日出勤、振出、連続勤務」
	 */
	@Test
	public void JudgingStatusTest11_2() {
		// given
		String sid = "sid";
		String code = "001";
		GeneralDate baseDate = GeneralDate.today();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = this.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = this.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = this.getR4Result(
				WorkTypeUnit.MonringAndAfternoon,
				WorkTypeClassification.AnnualHoliday, 
				WorkTypeClassification.AnnualHoliday,
				WorkTypeClassification.AnnualHoliday);

		new Expectations() {
			{
				require.getDailyActualWorkInfo(sid, baseDate);
				result = r1Result;
			}
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
			{
				require.getWorkType(code);
				result = r4Result;
			}
		};

		// when
		val result = JudgingStatusDomainService.JudgingStatus(require, sid);
		val expected = AttendanceAccordActualData.builder()
				.attendanceState(StatusClassfication.HOLIDAY)
				.workingNow(false)
				.build();

		// then
		assertThat(result).isEqualTo(expected);
	}
}
