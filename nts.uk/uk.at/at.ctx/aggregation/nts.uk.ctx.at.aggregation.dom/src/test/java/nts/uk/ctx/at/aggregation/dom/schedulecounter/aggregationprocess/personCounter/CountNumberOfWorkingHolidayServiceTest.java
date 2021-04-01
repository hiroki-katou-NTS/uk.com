package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.aggregation.AggregationByTypeService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.HalfDayWorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * UTコード
 * 個人計の出勤休日日数カテゴリを集計する
 * @author lan_lt
 */
@RunWith(JMockit.class)
public class CountNumberOfWorkingHolidayServiceTest {
	
	@Injectable
	CountNumberOfWorkingHolidayService.Require require;
	
	/**
	 * input:	勤務単位　= ONEDAY
	 * 			半日ごとの勤務種類の分類を取得する = WORKING
	 * output:	WORKING = 1
	 * 			HOLIDAY = 0
	 */
	@Test
	public void getValueOfTargetAggregation_ONEDAY_WORKING(
				@Injectable WorkInformation workInfo
			,	@Injectable WorkType workType
			,	@Injectable DailyWork dailyWork) {
		
		val targetCounts = new ArrayList<>(Arrays.asList(TargetAggreWorkClassification.values()));
		
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Attendance);
			}
		};
		
		Map<TargetAggreWorkClassification, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountNumberOfWorkingHolidayService.class, "getValueOfTargetAggregation", require, targetCounts, workInfo);
		
		//HOLIDAY
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.HOLIDAY);
			assertThat(numberHoliday).isEqualByComparingTo(BigDecimal.ZERO);
			
		}
		
		//WORKING
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.WORKING);
			assertThat(numberHoliday).isEqualByComparingTo(new BigDecimal(1));
			
		}
	}
	
	/**
	 * input:	勤務単位　= ONEDAY
	 * 			半日ごとの勤務種類の分類を取得する = HOLIDAY
	 * output:	WORKING = 0
	 * 			HOLIDAY = 1
	 */
	@Test
	public void getValueOfTargetAggregation_ONEDAY_HOLIDAY(
				@Injectable WorkInformation workInfo
			,	@Injectable WorkType workType
			,	@Injectable DailyWork dailyWork) {
		
		val targetCounts = new ArrayList<>(Arrays.asList(TargetAggreWorkClassification.values()));
		
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Holiday);
			}
		};
		
		Map<TargetAggreWorkClassification, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountNumberOfWorkingHolidayService.class, "getValueOfTargetAggregation", require, targetCounts, workInfo);
		
		//HOLIDAY
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.HOLIDAY);
			assertThat(numberHoliday).isEqualByComparingTo(new BigDecimal(1));
			
			
		}
		
		//WORKING
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.WORKING);
			assertThat(numberHoliday).isEqualByComparingTo(BigDecimal.ZERO);
			
		}
	}
	
	/**
	 * input:	勤務単位　= HALFDAY
	 * 			半日ごとの勤務種類の分類を取得する : 午前= WORKING, 午後 = HOLIDAY
	 * output:	WORKING = 	0.5
	 * 			HOLIDAY =	0.5
	 */
	@Test
	public void getValueOfTargetAggregation_HALFDAY(
				@Injectable WorkInformation workInfo
			,	@Injectable WorkType workType
			,	@Injectable DailyWork dailyWork) {
		
		val targetCounts = new ArrayList<>(Arrays.asList(TargetAggreWorkClassification.values()));
		
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Attendance, WorkTypeClassification.Holiday);
			}
		};
		
		Map<TargetAggreWorkClassification, BigDecimal>  result = NtsAssert.Invoke.staticMethod(CountNumberOfWorkingHolidayService.class, "getValueOfTargetAggregation", require, targetCounts, workInfo);
		
		//HOLIDAY
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.HOLIDAY);
			assertThat(numberHoliday).isEqualByComparingTo(new BigDecimal(0.5));
			
		}
		
		//WORKING
		{
			val numberHoliday = result.get(TargetAggreWorkClassification.WORKING);
			assertThat(numberHoliday).isEqualByComparingTo(new BigDecimal(0.5));
			
		}
	}
	
	@Test
	public void getValueOfTargetAggregation_HALFDAY(
				@Injectable WorkInfoOfDailyAttendance workInformation_1
			,	@Injectable WorkInfoOfDailyAttendance workInformation_2
			,	@Injectable WorkInfoOfDailyAttendance workInformation_3
			,	@Injectable WorkType workType
			,	@Injectable DailyWork dailyWork
			) {
		
		/**
		 * sid1 : 3 days  -> working = 1.5day, holiday = 1.5day
		 * sid2 : 3 days  -> working = 1.5day, holiday = 1.5day
		 * sid3 : 3 days  -> working = 1.5day, holiday = 1.5day
		 */
		val dailyWorks = Arrays.asList(
					Helper.createDailyWorks("sid1", workInformation_1)
				,	Helper.createDailyWorks("sid1", workInformation_2)
				,	Helper.createDailyWorks("sid1", workInformation_3)
				,	Helper.createDailyWorks("sid2", workInformation_1)
				,	Helper.createDailyWorks("sid2", workInformation_2)
				,	Helper.createDailyWorks("sid2", workInformation_3)
				,	Helper.createDailyWorks("sid3", workInformation_1)
				,	Helper.createDailyWorks("sid3", workInformation_2)
				,	Helper.createDailyWorks("sid3", workInformation_3));
		
		val numberOfWorkingHolidays = new HashMap<TargetAggreWorkClassification, BigDecimal>();
		numberOfWorkingHolidays.put(TargetAggreWorkClassification.WORKING, new BigDecimal(1.5));
		numberOfWorkingHolidays.put(TargetAggreWorkClassification.HOLIDAY, new BigDecimal(1.5));
		
		/**
		 *  HOLIDAY = 0.5, WORKING = 0.5
		 */
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Attendance, WorkTypeClassification.Holiday);
				
			}
		};
		

		
		new MockUp<AggregationByTypeService>() {
			@SuppressWarnings("unchecked")
			@Mock
			public <T> Map<T, BigDecimal> totalize(List<T> targets, List<Map<T, BigDecimal>> values) {

				return (Map<T, BigDecimal>) numberOfWorkingHolidays;
			}
		};
		
		val result = CountNumberOfWorkingHolidayService.count(require, dailyWorks);
		
		assertThat(result.size()).isEqualTo(3);
		
		//sid1
		{
			val numberWorkingHoliday = result.get("sid1");
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.HOLIDAY)).isEqualTo(new BigDecimal(1.5));
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.WORKING)).isEqualTo(new BigDecimal(1.5));
			
		}
		
		//sid2
		{
			val numberWorkingHoliday = result.get("sid2");
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.HOLIDAY)).isEqualTo(new BigDecimal(1.5));
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.WORKING)).isEqualTo(new BigDecimal(1.5));
			
		}
		
		//sid3
		{
			val numberWorkingHoliday = result.get("sid3");
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.HOLIDAY)).isEqualTo(new BigDecimal(1.5));
			
			assertThat(numberWorkingHoliday.get(TargetAggreWorkClassification.WORKING)).isEqualTo(new BigDecimal(1.5));
			
		}
		
	}

	
	public static class Helper{
		@Injectable
		private static AffiliationInforOfDailyAttd affiliationInfor;
		
		@Injectable
		private static WorkInfoOfDailyAttendance workInformation;
		/**
		 * 日別勤怠(Work)	を作る
		 * @param sid 社員ID
		 * @param workInformation 勤務情報
		 * @return
		 */
		public static IntegrationOfDaily createDailyWorks(String sid, WorkInfoOfDailyAttendance workInformation ) {
			return new IntegrationOfDaily(
					sid, GeneralDate.ymd(2021, 4, 1), workInformation
					, CalAttrOfDailyAttd.defaultData()
					, affiliationInfor
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, new BreakTimeOfDailyAttd(Collections.emptyList())
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty()
					, Collections.emptyList()
					, Optional.empty());
		}
	}
}
