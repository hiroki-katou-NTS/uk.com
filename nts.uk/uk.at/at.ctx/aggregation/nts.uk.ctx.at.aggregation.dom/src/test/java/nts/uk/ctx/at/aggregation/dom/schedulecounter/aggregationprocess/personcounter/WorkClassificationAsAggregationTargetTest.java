package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personCounter;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.HalfDayWorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
/**
 * UTコード 集計対象の勤務分類
 * 
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class TargetAggreWorkClassificationTest {

	@Injectable
	TargetAggreWorkClassification.Require require;

	/**
	 * input : 勤務種類がない 
	 * output : 日数 = 0
	 */
	@Test
	public void getNumberDay_workType_not_exist(@Injectable WorkInformation workInfo) {
		val target = TargetAggreWorkClassification.WORKING;

		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result).isEqualTo(BigDecimal.ZERO);

	}

	/**
	 * input :	勤務の単位= ONEDAY
				集計対象の勤務分類 = 出勤
 				一日= 出勤 
	 * output:	日数 = 1
	 */
	@Test
	public void getNumberDay_target_working_all_workType_working(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.WORKING;
		
		//出勤
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Attendance);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1.0));
		}

		//連続出勤
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.ContinuousWork);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1.0));
		}
		
		//振出
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Shooting);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.valueOf(1.0));
		}

	}

	/**
	 * input : 	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = 出勤
	 * 			一日 = 休日
	 * output :	日数 = 0
	 */
	@Test
	public void getNumberDay_target_working_all_workType_holiday(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.WORKING;
		
		//休日
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Holiday);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.ZERO);

		}

		//振休
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Pause);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
		}

	}

	/**
	 * input :	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = 休日
	 * 			一日 = 連続出勤
	 * output :	日数 = 0
	 */
	@Test
	public void getNumberDay_target_holiday_all_workType_working(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.HOLIDAY;
		
		//出勤
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Attendance);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
		}
		
		//連続出勤
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.ContinuousWork);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
		}
		
		//振出
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Shooting);
			
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);
			assertThat(result).isEqualByComparingTo(BigDecimal.ZERO);
		}
	}

	/**
	 * input : 	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = 休日
	 * output :	日数 = 1
	 */
	@Test
	public void getNumberDay_target_holiday_all_workType_holiday(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.HOLIDAY;
		
		//休日
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Holiday);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(1.0));

		}
		
		//振休
		{
			val oneDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Pause);
			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = oneDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(1.0));
		}

	}

	/**
	 * input : 	勤務の単位= HALFDAY 
	 *　output:	日数　 = 0.5
	 */
	@Test
	public void getNumberDay_half_day_sum_equal_half(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {
		/**
		 * 集計対象の勤務分類 = HOLIDAY
		 * 午前 = 振休, 午後 = 出勤 
		 * output: 日数　 = 0.5
		 */
		{
			val target = TargetAggreWorkClassification.HOLIDAY;
			val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Pause, WorkTypeClassification.Attendance);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = halfDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(0.5));

		}
		
		/**
		 * 集計対象の勤務分類 = HOLIDAY
		 * 午前 = 休日, 午後 = 出勤 
		 * output: 日数　 = 0.5
		 */
		{
			val target = TargetAggreWorkClassification.HOLIDAY;
			val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Holiday, WorkTypeClassification.Attendance);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = halfDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(0.5));

		}
		
		/**
		 * 	集計対象の勤務分類 = WORKING
		 *	午前 = 振休, 午後 = 出勤
		 *	output:	日数 = 0.5
		 */
		
		{
			val target = TargetAggreWorkClassification.WORKING;
			val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Attendance, WorkTypeClassification.Pause);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = halfDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(0.5));
		}
		
	}
	
	/**
	 * 勤務の単位= HALFDAY
	 *　output:	日数　 = 1
	 */
	@Test
	public void getNumberDay_half_day_sum_equal_one(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {
		
		//午前 = 休日, 午後 = 振休
		{
			val target = TargetAggreWorkClassification.HOLIDAY;
			val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Holiday,	WorkTypeClassification.Pause);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = halfDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(1.0));	
			
		}

		//午前 = 出勤, 午後 = 連続出勤
		{
			val target = TargetAggreWorkClassification.WORKING;
			val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Attendance, WorkTypeClassification.ContinuousWork);

			new Expectations() {
				{
					require.getWorkType((WorkTypeCode) any);
					result = Optional.of(workType);

					dailyWork.getHalfDayWorkTypeClassification();
					result = halfDay;
				}
			};

			val result = target.getNumberDay(require, workInfo);

			assertThat(result).isEqualTo(BigDecimal.valueOf(1.0));	
			
		}
	}
	

	public static class Helper {

		public static DailyWork createDailyWorkAsHalfDay(WorkTypeClassification forAm, WorkTypeClassification forPm) {

			return new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.Holiday, forAm, forPm);

		}

		/**
		 * 1日の勤務を作る
		 * @param forOneDay　勤務種類の分類
		 * @return
		 */
		public static DailyWork createDailyWorkAsWholeDay(WorkTypeClassification forOneDay) {

			return new DailyWork(WorkTypeUnit.OneDay, forOneDay, WorkTypeClassification.Holiday,
					WorkTypeClassification.Holiday);

		}

	}

}
