package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

/**
 * UpdateWorkLedgerSettingDomainService UnitTest
 *
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class UpdateWorkLedgerSettingDomainServiceTest {
    @Injectable
	UpdateWorkLedgerSettingDomainService.Require require;

	/**
	 * Test updateSetting method
	 *
	 * Condition:
	 * 		input param: settingCategory == STANDARD_SELECTION
	 * 		require.getOutputSettingDetail returns empty item
	 * Expect:
	 * 		BusinessException: "Msg_1928"
	 */
	@Test
    public void testUpdateSetting_01(){
 		OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode01");
		OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName01");

		new Expectations() {{
			require.getOutputSettingDetail("uid01");
			result = Optional.empty();
		}};

		NtsAssert.businessException("Msg_1928", () -> {
			UpdateWorkLedgerSettingDomainService.updateSetting(
					require,
					"uid01",
					name,
					SettingClassificationCommon.STANDARD_SELECTION,
					Arrays.asList(new AttendanceItemToPrint(1, 1))
			);
		});
    }

	/**
	 * Test updateSetting method
	 *
	 * Condition:
	 * 		input param: settingCategory == STANDARD_SELECTION
	 * 		require.getOutputSettingDetail returns non empty item
	 * Expect:
	 * 		returns AtomTask with invocation to require.updateWorkLedgerOutputItem
	 */
	@Test
	public void testUpdateSetting_02(){
		OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode02");
		OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName02");

		new Expectations() {{
			require.getOutputSettingDetail("uid02");
			result = Optional.of(WorkLedgerOutputItem.create(
					"uid02",
					code,
					name,
					SettingClassificationCommon.STANDARD_SELECTION));
		}};
		new Expectations(AppContexts.class) {{
			AppContexts.user().employeeId();
			result = "employeeId03";
		}};
		val actual = UpdateWorkLedgerSettingDomainService.updateSetting(
				require,
				"uid02",
				name,
				SettingClassificationCommon.STANDARD_SELECTION,
				Arrays.asList(new AttendanceItemToPrint(1, 1))
		);

		NtsAssert.atomTask(
				() -> actual,
				any -> require.updateWorkLedgerOutputItem("uid02", any.get())
		);
	}

	/**
	 * Test updateSetting method
	 *
	 * Condition:
	 * 		input param: settingCategory == FREE_SETTING
	 * Expect:
	 * 		returns AtomTask with invocation to require.updateWorkLedgerOutputItem
	 */
	@Test
	public void testUpdateSetting_03(){
		OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode03");
		OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName03");

		new Expectations(AppContexts.class) {{
			AppContexts.user().employeeId();
			result = "employeeId03";
		}};
		new Expectations() {{
			require.getOutputSettingDetail("uid03");
			result = Optional.of(WorkLedgerOutputItem.create(
					"uid02",
					code,
					name,
					SettingClassificationCommon.STANDARD_SELECTION));
		}};
		val actual = UpdateWorkLedgerSettingDomainService.updateSetting(
				require,
				"uid03",
				name,
				SettingClassificationCommon.FREE_SETTING,
				Arrays.asList(new AttendanceItemToPrint(1, 1))
		);

		NtsAssert.atomTask(
				() -> actual,
				any -> require.updateWorkLedgerOutputItem("uid03", any.get())
		);
	}
}