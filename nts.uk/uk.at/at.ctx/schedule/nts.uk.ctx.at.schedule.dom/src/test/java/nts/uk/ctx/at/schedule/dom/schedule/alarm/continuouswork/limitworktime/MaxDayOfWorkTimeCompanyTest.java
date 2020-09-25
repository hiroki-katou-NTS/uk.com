package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
@RunWith(JMockit.class)
public class MaxDayOfWorkTimeCompanyTest {
	@Test
	public void getters() {
		MaxDayOfWorkTimeCompany maxNumberOfWorkingDayOfPeriodsCom = MaxDayOfWorkTimeHelper.DUMMY;

		NtsAssert.invokeGetters(maxNumberOfWorkingDayOfPeriodsCom);

	}
	
	@Test
	public void create_maxNumberOfWorkingDayOfPeriodsCom_success() {
		MaxDayOfWorkTimeCompany maxNumberOfWorkingDayOfPeriodsCom = MaxDayOfWorkTimeHelper.DUMMY;
		
		assertThat(maxNumberOfWorkingDayOfPeriodsCom).extracting(
			 com -> com.getCode().v(),
			 com -> com.getName().v(),
			 com -> com.getMaxDayOfWorkTime().getMaxDay().v()
			 ).containsExactly(
					 "000000000000-0315",
					 "003",
					 "シフト１",
					 3);
	}
}
