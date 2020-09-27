package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod.Require;

@RunWith(JMockit.class)
public class WorkMethodContinuousWorkTest {
	@Injectable
	private Require require;

	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 勤務方法の種類を取得する
	 * check WorkMethodClassification	
	 * excepted： CONTINUOSWORK
	 */
	@Test
	public void check_WorkMethodClassification_CONTINUOSWORK() {
		val workMethodConti = new WorkMethodContinuousWork();
		assertThat(workMethodConti.getWorkMethodClassification()).isEqualTo(WorkMethodClassfication.CONTINUOSWORK);
	}
	
	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 該当するか判定する
	 * 勤務情報.勤務種類 ==　empty
	 * excepted：FALSE
	 */
	@Test
	public void checkDetermineIfApplicable_EMPTY() {
		val workInfo =  WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodConti = new WorkMethodContinuousWork();
				
		new Expectations() {
			{
				require.getWorkType((String)any);
			}
		};
		
		assertThat(workMethodConti.determineIfApplicable(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 該当するか判定する
	 * 1日の勤務 != 1日
	 * DailyWork != ONEDAY 
	 * excepted：FALSE
	 */
	@Test
	public void checkDetermineIfApplicable_NOT_ONE_DAY() {
		val workInfo =  WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodConti = new WorkMethodContinuousWork();
		val workType = WorkMethodHelper.createWorkTypeNotOneDay();
		new Expectations(workType) {
			{
				require.getWorkType((String)any);
				result = Optional.of(workType);
				
			}
		};
		
		assertThat(workMethodConti.determineIfApplicable(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 該当するか判定する
	 * 1日の勤務  != 連続勤務
	 * DailyWork != CONTINUOUS
	 * excepted：FALSE
	 */
	@Test
	public void checkDetermineIfApplicable_NOT_CONTINUOUS() {
		val workInfo =  WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodConti = new WorkMethodContinuousWork();
		val workType = WorkMethodHelper.createWorkTypeNotContinuous();
		new Expectations(workType) {
			{
				require.getWorkType((String)any);
				result = Optional.of(workType);
				
			}
		};
		
	   assertThat(workMethodConti.determineIfApplicable(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 該当するか判定する
	 * 1日の勤務 != 1日 && 1日の勤務 != 連続勤務
	 * DailyWork != ONEDAY && DailyWork != CONTINUOUS
	 * excepted：FALSE
	 */
	@Test
	public void checkDetermineIfApplicable_NOT_ONE_DAY_NOT_CONTINUOUS() {
		val workInfo =  WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodConti = new WorkMethodContinuousWork();
		val workType = WorkMethodHelper.createWorkTypeNotOneDayAndNotContinuous();
		new Expectations(workType) {
			{
				require.getWorkType((String)any);
				result = Optional.of(workType);
				
			}
		};
		
		assertThat(workMethodConti.determineIfApplicable(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(連続勤務)
	 * 該当するか判定する
	 * 1日の勤務  = 1日 && 1日の勤務  = 連続勤務
	 * DailyWork == ONEDAY && DailyWork == CONTINUOUS
	 * excepted：TRUE
	 */
	@Test
	public void checkDetermineIfApplicable_IS_ONE_DAY_IS_CONTINUOUS() {
		val workInfo =  WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodConti = new WorkMethodContinuousWork();
		val workType = WorkMethodHelper.createWorkTypeOneDayAndContinuous();
		new Expectations(workType) {
			{
				require.getWorkType((String)any);
				result = Optional.of(workType);
				
			}
		};
		
		assertThat(workMethodConti.determineIfApplicable(require, workInfo)).isTrue();
		
	}
}
