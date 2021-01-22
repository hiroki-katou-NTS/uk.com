package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class AnnualWorkLedgerOutputSettingTest {
    @Injectable
    AnnualWorkLedgerOutputSetting.Require require;

    /**
     * Test:
     * - method checkDuplicateStandardSelection: is true
     */
    @Test
    public void testCheckDuplicateStandardSelections_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");

        new Expectations() {
            {
                require.checkTheStandard(code);
                result = true;
            }
        };
        val actual = AnnualWorkLedgerOutputSetting.checkDuplicateStandardSelection(require, code);
        assertThat(actual).isTrue();
    }

    /**
     * Test:
     * - method checkDuplicateFreeSettings: is true
     */
    @Test
    public void testCheckDuplicateFreeSettings_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        String eplId = "employeeId";
        new Expectations() {
            {
                require.checkFreedom(code, eplId);
                result = true;
            }
        };
        val actual = AnnualWorkLedgerOutputSetting.checkDuplicateFreeSettings(require, code, eplId);
        assertThat(actual).isTrue();
    }

    /**
     * Test:
     * - Getter: AnnualWorkLedgerOutputSetting
     * - SettingClassificationCommon: FREE_SETTING
     */
    @Test
    public void testGetterOutputSettings_03() {
        val outputSettings = DumDataTest.dumFree();
        NtsAssert.invokeGetters(outputSettings);
    }

    /**
     * Test:
     * - Getter: AnnualWorkLedgerOutputSetting
     * - SettingClassificationCommon: STANDARD_SELECTION
     */
    @Test
    public void testGetterOutputSettings_04() {
        val outputSettings = DumDataTest.dumStandard();
        NtsAssert.invokeGetters(outputSettings);
    }


}
