package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod.Require;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * UnitTest: 勤務方法(休日)
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class WorkMethodHolidayTest {
	@Injectable
	private Require require;

	/**
	 * 勤務方法 = 勤務方法(休日)
	 * 勤務方法の種類を取得する
	 * check WorkMethodClassification	
	 * excepted： HOLIDAY
	 */
	@Test
	public void check_WorkMethodClassification_HOLIDAY() {
		val workMethodHd = new WorkMethodHoliday();
		assertThat(workMethodHd.getWorkMethodClassification()).isEqualTo(WorkMethodClassfication.HOLIDAY);
	}
	
	/**
	 * 勤務方法 = 勤務方法(休日)
	 * 該当するか判定する
	 * 1日休日か = FALSE
	 * excepted：FALSE
	 */
	@Test
	public void checkInclude_FALSE() {
		val workInfo = WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodHd = new WorkMethodHoliday();
				
		new Expectations() {
			{
				require.checkHoliday((WorkTypeCode)any);
				result = false;
			}
		};
		
		assertThat(workMethodHd.includes(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(休日)
	 * 該当するか判定する
	 * 1日休日か = TRUE
	 * excepted：TRUE
	 */
	@Test
	public void checkInclude_NOT_ONE_DAY() {
		val workInfo = WorkMethodHelper.WORK_INFO_DUMMY;
		val workMethodHd = new WorkMethodHoliday();
				
		new Expectations() {
			{
				require.checkHoliday((WorkTypeCode)any);
				result = true;
			}
		};
		
		assertThat(workMethodHd.includes(require, workInfo)).isTrue();
		
	}
}
