package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;

/**
 * Test for WorkTimeDivision
 * @author kumiko_otake
 *
 */
@RunWith(JMockit.class)
public class WorkTimeDivisionTest {

	@Test
	public void testGetters() {
		val instance = new WorkTimeDivision( WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FLOW_WORK );
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: getWorkTimeForm
	 */
	@Test
	public void testGetWorkTimeForm(@Mocked WorkTimeForm mocked) {

		val instance = new WorkTimeDivision( WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FLOW_WORK );

		// Before execute
		new Verifications() {{
			WorkTimeForm.from( instance.getWorkTimeDailyAtr(), instance.getWorkTimeMethodSet() );
			times = 0;
		}};

		// Execute
		val result = instance.getWorkTimeForm();

		// After execute
		new Verifications() {{
			WorkTimeForm.from( instance.getWorkTimeDailyAtr(), instance.getWorkTimeMethodSet() );
			times = 1;
		}};

		// Assertion
		assertThat( result )
			.isEqualTo( WorkTimeForm.from(instance.getWorkTimeDailyAtr(), instance.getWorkTimeMethodSet()) );

	}

}
