package nts.uk.ctx.at.record.dom.dailyresult.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.dailyresult.service.JudgingStatusDomainService.Require;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

@RunWith(JMockit.class)
public class JudgingStatusDomainServiceTest {

	@Injectable
	private Require require;

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
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(0, now, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case1Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(0, now - 1000, 1);
		
		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case1Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, now + 1000, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case2Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, now + 1000, 1);
		
		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case2Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 <  日時＃今()
	 * $直行区分 == する
	 */
	@Test
	public void JudgingStatusTest2_3() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, now + 1000, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case2Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 2
	 * 
	 * $退勤時刻 ＞　日時＃今()
	 * $出勤時刻 < 日時＃今()
	 * $直行区分 == Empty
	 */
	@Test
	public void JudgingStatusTest2_4() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case2Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, now + 1000, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case3Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case3Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 3
	 * 
	 * $出勤時刻 < 日時＃今()
	 * $直行区分 == しない
	 */
	@Test
	public void JudgingStatusTest3_2() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, now + 1000, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case3Expected();

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
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now + 1000, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case4Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now + 1000, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case4Expected();

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
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, now + 1000, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case5Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, now + 1000, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case5Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, null, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case6Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case6Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	
	/**
	 * case 6
	 * 
	 * $出勤時刻 < 日時＃今()
	 * $直行区分 == する
	 */
	@Test
	public void JudgingStatusTest6_3() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, null, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case6Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 6
	 * 
	 * $出勤時刻 < 日時＃今()
	 * $直行区分 == Empty
	 */
	@Test
	public void JudgingStatusTest6_4() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, null, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case6Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, null, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case7Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case7Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}

	/**
	 * case 7
	 * 
	 * $出勤時刻 < 日時＃今()
	 */
	@Test
	public void JudgingStatusTest7_2() {
		// given
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		Integer now = GeneralDateTime.now().hours() * 60 + GeneralDateTime.now().minutes();

		// [R-1]
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now - 1000, null, 1);

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case7Expected();

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
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now + 1000, null, 1);

		new Expectations() {
			{
				require.getDailyAttendanceAndDeparture(sid, baseDate);
				result = r2Result;
			}
		};

		// when
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case8Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);
		
		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(now + 1000, null, 1);

		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case8Expected();

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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case9Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);
		
		//[R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case9Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, null, 0);

		// [R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case10Expected();
		
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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case10Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case10Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case11Expected();

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
		Optional<WorkInfoOfDailyPerformance> r1Result = JudgingStatusDomainServiceTestHelper.getR1Result(NotUseAttribute.Not_use);

		// [R-2]
		Optional<TimeLeavingOfDailyPerformance> r2Result = JudgingStatusDomainServiceTestHelper.getR2Result(null, null, 1);

		// [R-4]
		Optional<WorkType> r4Result = JudgingStatusDomainServiceTestHelper.getR4Result(
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
		AttendanceAccordActualData result = JudgingStatusDomainService.JudgingStatus(require, sid);
		AttendanceAccordActualData expected = JudgingStatusDomainServiceTestHelper.case11Expected();

		// then
		assertThat(result).isEqualTo(expected);
	}
}
