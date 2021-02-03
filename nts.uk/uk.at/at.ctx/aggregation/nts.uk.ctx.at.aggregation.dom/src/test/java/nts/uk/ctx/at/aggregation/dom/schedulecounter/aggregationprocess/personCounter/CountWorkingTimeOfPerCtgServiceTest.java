package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimeTotalizationService;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily.AttendanceTimesForAggregation;
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
	
	@Test
	public void test_countWorkingTimeOfPer(
				@Mocked AttendanceTimeOfDailyAttendance attendance_1
			,	@Mocked AttendanceTimeOfDailyAttendance attendance_2
			,	@Mocked AttendanceTimeOfDailyAttendance attendance_3
			,	@Mocked AttendanceTimeOfDailyAttendance attendance_4
			,	@Mocked AttendanceTimeOfDailyAttendance attendance_5
			,	@Mocked AttendanceTimeOfDailyAttendance attendance_6){
		
		val dailyWorks = Arrays.asList(
					CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 26), 300, 1, 300)
				,	CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 27), 400, 1, 400)
				,	CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_1", GeneralDate.ymd(2021, 1, 28), 500, 1, 500)
				,	CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 27), 300, 1, 300)
				,	CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 26), 400, 1, 400)
				,	CountWorkingTimeOfPerCtgServiceHelper.createDailyWorks("sid_2", GeneralDate.ymd(2021, 1, 28), 500, 1, 500));
		
		new MockUp<AttendanceTimeTotalizationService>() {
			@Mock
			public Map<AttendanceTimesForAggregation, BigDecimal> totalize(
					List<AttendanceTimesForAggregation> targets, List<AttendanceTimeOfDailyAttendance> values) {
				
					return new HashMap<AttendanceTimesForAggregation, BigDecimal>() {
						private static final long serialVersionUID = 1L;
						{
							put(AttendanceTimesForAggregation.WORKING_TOTAL, BigDecimal.valueOf(2400));
							put(AttendanceTimesForAggregation.WORKING_WITHIN, BigDecimal.valueOf(1200));
							put(AttendanceTimesForAggregation.WORKING_EXTRA, BigDecimal.valueOf(1200));
						}
					};
			}
		};
		
		val result = CountWorkingTimeOfPerCtgService.get(dailyWorks);
		
		assertThat(result).hasSize(2);
		
		assertThat(result.keySet()).containsAll(Arrays.asList("sid_1", "sid_2"));
		
		Map<AttendanceTimesForAggregation, BigDecimal> value1 = result.get("sid_1");
		assertThat(value1.entrySet())
				.extracting(d -> d.getKey().getValue(), d -> d.getValue())
				.contains(
						  tuple(0, BigDecimal.valueOf(2400))
						, tuple(1, BigDecimal.valueOf(1200))
						, tuple(2, BigDecimal.valueOf(1200)));
		
		Map<AttendanceTimesForAggregation, BigDecimal> value2 = result.get("sid_2");
		assertThat(value2.entrySet())
					.extracting(d -> d.getKey().getValue(), d -> d.getValue())
					.contains(
						  tuple(0, BigDecimal.valueOf(2400))
						, tuple(1, BigDecimal.valueOf(1200))
						, tuple(2, BigDecimal.valueOf(1200)));
		
	}
	

}
