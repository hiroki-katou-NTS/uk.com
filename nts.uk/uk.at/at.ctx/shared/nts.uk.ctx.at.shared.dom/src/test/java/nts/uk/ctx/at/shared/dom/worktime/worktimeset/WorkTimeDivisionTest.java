package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingTest.Helper;

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

	/**
	 * Target	: isFlexWorkDay
	 * Pattern	: フレックス勤務日（true）
	 */
	@Test
	public void testIsFlexWorkDay_True1(){
		
		// 就業時間帯勤務区分(フレックス勤務)
		WorkTimeDivision instance = new WorkTimeDivision(WorkTimeDailyAtr.FLEX_WORK, WorkTimeMethodSet.FLOW_WORK);

		// 労働条件項目
		WorkingConditionItem conditionItem = Helper.getDummyWorkConditionItem(WorkingSystem.FLEX_TIME_WORK);
		
		// Execute
		boolean result = instance.isFlexWorkDay(conditionItem);
		
		// Assertion
		assertThat(result).isTrue();
	}

	/**
	 * Target	: isFlexWorkDay
	 * Pattern	: フレックス勤務日でない（false、固定勤務）
	 */
	@Test
	public void testIsFlexWorkDay_False1(){
		
		// 就業時間帯勤務区分(固定勤務)
		WorkTimeDivision instance = new WorkTimeDivision(WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FIXED_WORK);

		// 労働条件項目
		WorkingConditionItem conditionItem = Helper.getDummyWorkConditionItem(WorkingSystem.REGULAR_WORK);
		
		// Execute
		boolean result = instance.isFlexWorkDay(conditionItem);
		
		// Assertion
		assertThat(result).isFalse();
	}

	/**
	 * Target	: isFlexWorkDay
	 * Pattern	: フレックス勤務日でない（false、流動勤務）
	 */
	@Test
	public void testIsFlexWorkDay_False2(){
		
		// 就業時間帯勤務区分(流動勤務)
		WorkTimeDivision instance = new WorkTimeDivision(WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FLOW_WORK);

		// 労働条件項目
		WorkingConditionItem conditionItem = Helper.getDummyWorkConditionItem(WorkingSystem.REGULAR_WORK);
		
		// Execute
		boolean result = instance.isFlexWorkDay(conditionItem);
		
		// Assertion
		assertThat(result).isFalse();
	}
}
