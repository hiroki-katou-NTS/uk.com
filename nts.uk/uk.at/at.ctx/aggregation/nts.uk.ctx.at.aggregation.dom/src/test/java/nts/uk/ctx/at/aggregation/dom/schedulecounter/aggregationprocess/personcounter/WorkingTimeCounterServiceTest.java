package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter.WorkingTimeCounterServiceHelper.AttendanceTimeOfDailyAttendanceHelp;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimeTotalizationService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
/**
 * UTコード
 * 個人計の労働時間カテゴリを集計する
 * @author lan_lt
 *
 */
@SuppressWarnings("static-access")
@RunWith(JMockit.class)
public class WorkingTimeCounterServiceTest {

	private List<IntegrationOfDaily> dailyWorks;

 	@Before
	public void initData() {
		/**
		 * sid1:	[2021/ 1/ 26], 総労働時間（就業時間） = 300, 割増時間（時間外時間） = 100, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 300 + 100 = 400
		 *			[2021/ 1/ 27], 総労働時間（就業時間） = 400, 割増時間（時間外時間） = 200, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 400 + 200 = 600
		 *			[2021/ 1/ 28], 総労働時間（就業時間） = 500, 割増時間（時間外時間） = 300, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 500 + 300 = 800
		 */
		this.dailyWorks = Arrays.asList(
				WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 26), new AttendanceTime(300), new AttendanceTime(100))
			,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 27), new AttendanceTime(400), new AttendanceTime(200))
			,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 28), new AttendanceTime(500), new AttendanceTime(300)));


	}

	/**
	 * input: 日別勤怠リスト: empty
	 * output: 期待値：　empty
	 */
	@Test
	public void test_countWorkingTime_empty() {
		val result = WorkingTimeCounterService.get(Collections.emptyList());
		assertThat(result).isEmpty();

	}

	/**
	 * 回数_テスト
	 * @param service
	 */
	@SuppressWarnings({"unchecked" })
	@Test
	public void test_count_number_times_of_call_countWorkingTime(@Mocked AttendanceTimeTotalizationService service) {
		/**
		 * sid1:	[2021/ 1/ 26], 総労働時間（就業時間） = 300, 割増時間（時間外時間） = 100, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 300 + 100 = 400
		 *			[2021/ 1/ 27], 総労働時間（就業時間） = 400, 割増時間（時間外時間） = 200, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 400 + 200 = 600
		 *			[2021/ 1/ 28], 総労働時間（就業時間） = 500, 割増時間（時間外時間） = 300, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 500 + 300 = 800
		 * sid2:	[2021/ 1/ 26], 総労働時間（就業時間） = 300, 割増時間（時間外時間） = 100, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 300 + 100 = 400
		 *			[2021/ 1/ 27], 総労働時間（就業時間） = 400, 割増時間（時間外時間） = 200, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 400 + 200 = 600
		 *			[2021/ 1/ 28], 総労働時間（就業時間） = 500, 割増時間（時間外時間） = 300, 合計 = 総労働時間（就業時間） + 割増時間（時間外時間） = 500 + 300 = 800
		 */
		val dailyWorks = Arrays.asList(
					WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 26), new AttendanceTime(300), new AttendanceTime(100))
				,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 27), new AttendanceTime(400), new AttendanceTime(200))
				,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 28), new AttendanceTime(500), new AttendanceTime(300))
				,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 27), new AttendanceTime(300), new AttendanceTime(100))
				,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 26), new AttendanceTime(400), new AttendanceTime(200))
				,	WorkingTimeCounterServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 28), new AttendanceTime(500), new AttendanceTime(300)));

		new Expectations() {
			{
				service.totalize((List<AttendanceTimesForAggregation>) any, (List<AttendanceTimeOfDailyAttendance>) any);
				//* 社員IDリストは「sid_1, sid_2」なので、回数 = 2です。
				times = 2;

			}
		};

		WorkingTimeCounterService.get(dailyWorks);
	}

	/**
	 * 記録した通りの引数でメソッド_テスト
	 * @param service
	 */
	@Test
	public void test_argument_of_countWorkingTime(@Injectable AttendanceTimeTotalizationService service) {
		val targetUnit =  Arrays.asList(
				AttendanceTimesForAggregation.WORKING_TOTAL
			,	AttendanceTimesForAggregation.WORKING_WITHIN
			,	AttendanceTimesForAggregation.WORKING_EXTRA);

		val dailyAttendance= new ArrayList<AttendanceTimeOfDailyAttendance>(Arrays.asList(
							/** 総労働時間（就業時間） = 300, 割増時間（時間外時間） = 100 */
							AttendanceTimeOfDailyAttendanceHelp.createDailyAttendance(new AttendanceTime(300), new AttendanceTime(100))
							/** 総労働時間（就業時間） = 400, 割増時間（時間外時間） = 200 */
						,	AttendanceTimeOfDailyAttendanceHelp.createDailyAttendance(new AttendanceTime(400), new AttendanceTime(200))
							/** 総労働時間（就業時間） = 500, 割増時間（時間外時間） = 300 */
						,	AttendanceTimeOfDailyAttendanceHelp.createDailyAttendance(new AttendanceTime(500), new AttendanceTime(300))
						));

		new Expectations() {
			{
				service.totalize(targetUnit, dailyAttendance);
			}
		};

		WorkingTimeCounterService.get(this.dailyWorks);
	}

	@Test
	public void test_countWorkingTime(){

		new MockUp<AttendanceTimeTotalizationService>() {
			/**
			 * 社員(sid1)の勤怠時間
			 * WORKING_TOTAL=	合計の　「2021/ 01/ 26」 + 「2021/ 01/ 27」 + 「2021/ 01/ 28」 = 400 + 600 + 800 = 1800
			 * WORKING_WITHIN=	合計の総労働時間（就業時間）の　「2021/ 01/ 26」 + 「2021/ 01/ 27」 +	  「2021/ 01/ 28」 = 300 + 400 + 500 = 1200
			 * WORKING_EXTRA=	合計の割増時間（時間外時間）の　「2021/ 01/ 26」 + 「2021/ 01/ 27」 + 「2021/ 01/ 28」 = 100 + 200 + 300 = 600
			 */
			@Mock
			public Map<AttendanceTimesForAggregation, BigDecimal> totalize(
					List<AttendanceTimesForAggregation> targets, List<AttendanceTimeOfDailyAttendance> values) {
					return new HashMap<AttendanceTimesForAggregation, BigDecimal>() {
						private static final long serialVersionUID = 1L;
						{
							//総労働時間
							put(AttendanceTimesForAggregation.WORKING_TOTAL, BigDecimal.valueOf(400  + 600 + 800));
							//就業時間
							put(AttendanceTimesForAggregation.WORKING_WITHIN, BigDecimal.valueOf(300 + 400 + 500));
							//時間外時間
							put(AttendanceTimesForAggregation.WORKING_EXTRA, BigDecimal.valueOf(100 + 200 + 300));
						}
					};
			}
		};

		val result = WorkingTimeCounterService.get(dailyWorks);

		val empId = new EmployeeId("sid_1");
		assertThat(result).hasSize(1);
		assertThat(result.keySet()).containsAll(Arrays.asList(empId));

		assertThat(result.get(empId).entrySet())
				.extracting(d -> d.getKey(), d -> d.getValue())
				.contains(
							tuple(AttendanceTimesForAggregation.WORKING_TOTAL,	BigDecimal.valueOf(400  + 600 + 800))
						,	tuple(AttendanceTimesForAggregation.WORKING_WITHIN,	BigDecimal.valueOf(300 + 400 + 500))
						,	tuple(AttendanceTimesForAggregation.WORKING_EXTRA,	BigDecimal.valueOf(100 + 200 + 300))
				);

	}
}
