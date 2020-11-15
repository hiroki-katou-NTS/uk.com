package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
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
					code,
					name,
					SettingClassificationCommon.STANDARD_SELECTION,
					Arrays.asList(11 , 12),
					Arrays.asList(13 , 14)
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
		val attendanceIdList = Arrays.asList(21 , 22);
		val rankingList = Arrays.asList(23 , 24);

		new Expectations() {{
			require.getOutputSettingDetail("uid02");
			result = Optional.of(WorkLedgerOutputItem.create(
					"uid02",
					code,
					name,
					SettingClassificationCommon.STANDARD_SELECTION));
		}};

		val actual = UpdateWorkLedgerSettingDomainService.updateSetting(
				require,
				"uid02",
				code,
				name,
				SettingClassificationCommon.STANDARD_SELECTION,
				attendanceIdList,
				rankingList
		);

		NtsAssert.atomTask(
				() -> actual,
				any -> require.updateWorkLedgerOutputItem("uid02", any.get(), any.get())
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
		val attendanceIdList = Arrays.asList(31 , 32);
		val rankingList = Arrays.asList(33 , 34);

		new Expectations(AppContexts.class) {{
			AppContexts.user().employeeId();
			result = "employeeId03";
		}};

		val actual = UpdateWorkLedgerSettingDomainService.updateSetting(
				require,
				"uid03",
				code,
				name,
				SettingClassificationCommon.FREE_SETTING,
				attendanceIdList,
				rankingList
		);

		NtsAssert.atomTask(
				() -> actual,
				any -> require.updateWorkLedgerOutputItem("uid03", any.get(), any.get())
		);
	}
}