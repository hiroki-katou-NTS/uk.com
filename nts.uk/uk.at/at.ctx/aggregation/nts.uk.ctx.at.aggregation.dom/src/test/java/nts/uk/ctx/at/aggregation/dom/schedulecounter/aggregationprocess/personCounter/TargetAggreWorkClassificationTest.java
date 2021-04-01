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
import nts.arc.enums.EnumAdaptor;
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
		val target = EnumAdaptor.valueOf(0, TargetAggreWorkClassification.class);

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
				集計対象の勤務分類 = WORKING
 				一日= WORKING 
	 * output:	日数 = 1
	 */
	@Test
	public void getNumberDay_target_working_all_workType_working(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.WORKING;

		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Attendance);
			}
		};

		val result = target.getNumberDay(require, workInfo);
		assertThat(result).isEqualByComparingTo(new BigDecimal(1.0));

	}

	/**
	 * input : 	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = WORKING
	 * 			一日 = HOLIDAY 
	 * output :	日数 = 0
	 */
	@Test
	public void getNumberDay_target_working_all_workType_holiday(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.WORKING;
		val halfDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Holiday);
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = halfDay;
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result).isEqualTo(BigDecimal.ZERO);
	}

	/**
	 * input :	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = HOLIDAY
	 * 			一日 = WORKING
	 * output :	日数 = 0
	 */
	@Test
	public void getNumberDay_target_holiday_all_workType_working(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.HOLIDAY;
		val halfDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.ContinuousWork);
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = halfDay;
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result).isEqualTo(BigDecimal.ZERO);

	}

	/**
	 * input : 	勤務の単位= ONEDAY
	 * 			集計対象の勤務分類 = HOLIDAY
	 * 			一日= HOLIDAY 
	 * output :	日数 = 1
	 */
	@Test
	public void getNumberDay_target_holiday_all_workType_holiday(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.HOLIDAY;
		val halfDay = HalfDayWorkTypeClassification.createByWholeDay(WorkTypeClassification.Pause);
		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = halfDay;
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result.intValue()).isEqualTo(1);

	}

	/**
	 * input : 	勤務の単位= HALFDAY 
	 * 			集計対象の勤務分類 = HOLIDAY
	 * 			 午前 = HOLIDAY, 午後 = WORKING 
	 * output: 日数 = 0.5
	 */
	@Test
	public void getNumberDay_target_holiday_half_workType_holiday(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.HOLIDAY;

		val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Pause,
				WorkTypeClassification.Attendance);

		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = halfDay;
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result).isEqualTo(new BigDecimal(0.5));

	}

	/**
	 * input : 	勤務の単位= HALFDAY 
	 * 			集計対象の勤務分類 = WORKING 
	 * 			午前 = HOLIDAY, 午後 = WORKING 
	 * output: 日数 = 0.5
	 */
	@Test
	public void getNumberDay_target_holiday_half_workType_working(@Injectable WorkInformation workInfo,
			@Injectable WorkType workType, @Injectable DailyWork dailyWork) {

		val target = TargetAggreWorkClassification.WORKING;

		val halfDay = HalfDayWorkTypeClassification.createByAmAndPm(WorkTypeClassification.Pause,
				WorkTypeClassification.Attendance);

		new Expectations() {
			{
				require.getWorkType((WorkTypeCode) any);
				result = Optional.of(workType);

				dailyWork.getHalfDayWorkTypeClassification();
				result = halfDay;
			}
		};

		val result = target.getNumberDay(require, workInfo);

		assertThat(result).isEqualTo(new BigDecimal(0.5));

	}

	public static class Helper {

		public static DailyWork createDailyWorkAsHalfDay(WorkTypeClassification forAm, WorkTypeClassification forPm) {

			return new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.Holiday, forAm, forPm);

		}

		public static DailyWork createDailyWorkAsWholeDay(WorkTypeClassification forOneDay) {

			return new DailyWork(WorkTypeUnit.OneDay, forOneDay, WorkTypeClassification.Holiday,
					WorkTypeClassification.Holiday);

		}

	}

}
