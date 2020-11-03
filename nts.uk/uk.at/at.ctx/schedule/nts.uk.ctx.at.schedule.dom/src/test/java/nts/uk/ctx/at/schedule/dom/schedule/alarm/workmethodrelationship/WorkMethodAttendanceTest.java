package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship.WorkMethod.Require;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * UnitTest: 勤務方法(出勤)
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class WorkMethodAttendanceTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		val workMethodAt = WorkMethodHelper.WORK_INFO_DUMMY;
		NtsAssert.invokeGetters(workMethodAt);
	}
	
	/**
	 * 勤務方法(出勤)を作成する：成功(success)
	 * 
	 */
	@Test
	public void create_WorkMethodAttendance_success() {
		val workMethodAt = new WorkMethodAttendance(new WorkTimeCode("001"));
		assertThat(workMethodAt.getWorkTimeCode().v()).isEqualTo("001");
	}

	/**
	 * 勤務方法 = 勤務方法(出勤)
	 * 勤務方法の種類を取得する
	 * check WorkMethodClassification	
	 * excepted： ATTENDANCE
	 */
	@Test
	public void check_WorkMethodClassification_ATTENDANCE() {
		val workMethodAt = new WorkMethodAttendance(new WorkTimeCode("001"));
		assertThat(workMethodAt.getWorkMethodClassification()).isEqualTo(WorkMethodClassfication.ATTENDANCE);
	}
	
	/**
	 * 勤務方法 = 勤務方法(出勤)
	 * 該当するか判定する
	 * 「勤務方法(出勤)」の「就業時間帯コード」 != 「勤務情報」の「就業時間帯コード」
	 * excepted：FALSE
	 */
	@Test
	public void checkInclude_FALSE() {
		//就業時間帯コード == 002
		val workInfo =  new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("002"));
		//就業時間帯コード == 001
		val workMethodAt = new WorkMethodAttendance(new WorkTimeCode("001"));
		
		assertThat(workMethodAt.includes(require, workInfo)).isFalse();
		
	}
	
	/**
	 * 勤務方法 = 勤務方法(出勤)
	 * 該当するか判定する
	 * 「勤務方法(出勤)」の「就業時間帯コード」 = 「勤務情報」の「就業時間帯コード」
	 * excepted：TRUE
	 */
	@Test
	public void checkInclude_TRUE() {
		//就業時間帯コード == 002
		val workInfo =  new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("002"));
		val workMethodAt = new WorkMethodAttendance(new WorkTimeCode("002"));
		
		assertThat(workMethodAt.includes(require, workInfo)).isTrue();
		
	}
}
