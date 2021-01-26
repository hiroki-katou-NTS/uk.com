package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class WorkStatusOutputSettingsTest {

    @Injectable
    WorkStatusOutputSettings.Require require;

    /**
     * Test:
     * - method checkDuplicateStandardSelections: is true
     */

    @Test
    public void testCheckDuplicateStandardSelections() {
        String opCode = "code";
        new Expectations() {
            {
                require.checkTheStandard(opCode);
                result = true;
            }
        };
        val actual = WorkStatusOutputSettings.checkDuplicateStandardSelections(require, opCode);
        assertThat(actual).isTrue();
    }

    /**
     * Test:
     * - method checkDuplicateFreeSettings: is true
     */
    @Test
    public void testCheckDuplicateFreeSettings() {
        String eplId = "employeeId";
        String opCode = "code";
        new Expectations() {
            {
                require.checkFreedom(opCode, eplId);
                result = true;
            }
        };
        val actual = WorkStatusOutputSettings.checkDuplicateFreeSettings(require, opCode, eplId);
        assertThat(actual).isTrue();
    }

    /**
     * Test:
     * - Getter: WorkStatusOutputSettings
     * - SettingClassificationCommon: FREE_SETTING
     */
    @Test
    public void testGetterOutputSettings_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("12");
        OutputItemSettingName name = new OutputItemSettingName("全角");
        String eplId = "employeeId01";
        String settingId = "settingId";
        val outputSettings = DumData.dum(code, name, eplId, settingId, SettingClassificationCommon.FREE_SETTING);
        NtsAssert.invokeGetters(outputSettings);
    }

    /**
     * Test:
     * - Getter: WorkStatusOutputSettings
     * - SettingClassificationCommon: STANDARD_SELECTION
     */
    @Test
    public void testGetterOutputSettings_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("123");
        OutputItemSettingName name = new OutputItemSettingName("全角");
        String eplId = "employeeId";
        String settingId = "settingId";
        val outputSettings = DumData.dum(code, name, eplId, settingId, SettingClassificationCommon.STANDARD_SELECTION);
        NtsAssert.invokeGetters(outputSettings);
    }

}
