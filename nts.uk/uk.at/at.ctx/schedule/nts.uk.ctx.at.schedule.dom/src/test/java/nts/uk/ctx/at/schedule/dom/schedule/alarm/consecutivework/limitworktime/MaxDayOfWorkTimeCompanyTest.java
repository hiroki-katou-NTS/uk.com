package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * UnitTest: 会社の就業時間帯の期間内上限勤務
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class MaxDayOfWorkTimeCompanyTest {
	@Test
	public void getters() {
		val maxNumberDayOfPeriodsCom = MaxDayOfWorkTimeHelper.DUMMY;

		NtsAssert.invokeGetters(maxNumberDayOfPeriodsCom);

	}
	
	@Test
	public void create_maxDayOfWorkTimeCompanyTest_success() {
		val maxDayOfWorkTime = new MaxDayOfWorkTime(
				Arrays.asList(new WorkTimeCode("001"), new WorkTimeCode("002"), new WorkTimeCode("003")),
				new MaxDay(5));
		val maxDayOfWorkTimeCom = new MaxDayOfWorkTimeCompany(new MaxDayOfWorkTimeCode("code"),
				new MaxDayOfWorkTimeName("name"), maxDayOfWorkTime);

		assertThat(maxDayOfWorkTimeCom.getCode().v()).isEqualTo("code");
		assertThat(maxDayOfWorkTimeCom.getName().v()).isEqualTo("name");
		assertThat(maxDayOfWorkTimeCom.getMaxDayOfWorkTime()).isEqualTo(maxDayOfWorkTime);
	}
}
