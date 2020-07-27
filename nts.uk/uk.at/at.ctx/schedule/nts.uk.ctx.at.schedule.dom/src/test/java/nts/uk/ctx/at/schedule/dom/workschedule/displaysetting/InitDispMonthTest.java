package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

	@RunWith(JMockit.class)
	public class InitDispMonthTest {

		@Test
		public void getter(){
			InitDispMonth monthTest = InitDispMonth.valueOf(InitDispMonth.NEXT_MONTH.value);
			NtsAssert.invokeGetters(monthTest);
		}
		@Test
		public void test() {
			InitDispMonth dispMonth = InitDispMonth.valueOf(0);
			assertThat(dispMonth).isEqualTo(InitDispMonth.CURRENT_MONTH);
			InitDispMonth dispMonth1 = InitDispMonth.valueOf(1);
			assertThat(dispMonth1).isEqualTo(InitDispMonth.NEXT_MONTH);
			
		}
	}
