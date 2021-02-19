package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.shr.com.primitive.Memo;

@RunWith(JMockit.class)
public class WorkTimeSettingTest {

	@Test
	public void testGetter() {
		val instance = Helper.getDummyWorkSetting("000", WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FIXED_WORK);
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: getWorkSetting
	 * Pattern	: 固定勤務
	 * @param require
	 */
	@Test
	public void testGetWorkSetting_Fixed(@Injectable WorkTimeSetting.Require require) {

		// 就業時間帯の設定(固定勤務)
		val instance = Helper.getDummyWorkSetting("000", WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FIXED_WORK);

		// Check
		assertThat( instance.getWorkTimeDivision().getWorkTimeForm() ).isEqualTo( WorkTimeForm.FIXED );

		// Before
		new Verifications() {{
			// 固定勤務設定を取得する
			require.getWorkSettingForFixedWork( instance.getWorktimeCode() );
			times = 0;
		}};

		// Execute
		instance.getWorkSetting(require);

		// After
		new Verifications() {{
			// 固定勤務設定を取得する
			require.getWorkSettingForFixedWork( instance.getWorktimeCode() );
			times = 1;
		}};

	}

	/**
	 * Target	: getWorkSetting
	 * Pattern	: 流動勤務
	 * @param require
	 */
	@Test
	public void testGetWorkSetting_Flow(@Injectable WorkTimeSetting.Require require) {

		// 就業時間帯の設定(流動勤務)
		val instance = Helper.getDummyWorkSetting("000", WorkTimeDailyAtr.REGULAR_WORK, WorkTimeMethodSet.FLOW_WORK);

		// Check
		assertThat( instance.getWorkTimeDivision().getWorkTimeForm() ).isEqualTo( WorkTimeForm.FLOW );

		// Before
		new Verifications() {{
			// 流動勤務設定を取得する
			require.getWorkSettingForFlowWork( instance.getWorktimeCode() );
			times = 0;
		}};

		// Execute
		instance.getWorkSetting(require);

		// After
		new Verifications() {{
			// 流動勤務設定を取得する
			require.getWorkSettingForFlowWork( instance.getWorktimeCode() );
			times = 1;
		}};

	}

	/**
	 * Target	: getWorkSetting
	 * Pattern	: フレックス勤務
	 * @param require
	 */
	@Test
	public void testGetWorkSetting_Flex(@Injectable WorkTimeSetting.Require require) {

		// 就業時間帯の設定(フレックス勤務)
		val instance = Helper.getDummyWorkSetting("000", WorkTimeDailyAtr.FLEX_WORK, WorkTimeMethodSet.FIXED_WORK);

		// Check
		assertThat( instance.getWorkTimeDivision().getWorkTimeForm() ).isEqualTo( WorkTimeForm.FLEX );

		// Before
		new Verifications() {{
			// フレックス勤務設定を取得する
			require.getWorkSettingForFlexWork( instance.getWorktimeCode() );
			times = 0;
		}};

		// Execute
		instance.getWorkSetting(require);

		// After
		new Verifications() {{
			// フレックス勤務設定を取得する
			require.getWorkSettingForFlexWork( instance.getWorktimeCode() );
			times = 1;
		}};

	}


	protected static class Helper {

		public static WorkTimeSetting getDummyWorkSetting(String code, WorkTimeDailyAtr workForm, WorkTimeMethodSet method) {
			return new WorkTimeSetting("0000001-001", new WorkTimeCode(code), new WorkTimeDivision(workForm, method)
					, AbolishAtr.NOT_ABOLISH, new ColorCode("#ffffff")
					, new WorkTimeDisplayName(new WorkTimeName("表示名#"), new WorkTimeAbName("略名"), new WorkTimeSymbol("記"))
					, new Memo(""), new WorkTimeNote(""));
		}

	}

}
