package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class WorkStatusOutputSettingTest {

    @Injectable
    WorkStatusOutputSettings.Require require;
    private final OutputItemSettingCode code = new OutputItemSettingCode("12");
    private final OutputItemSettingName name = new OutputItemSettingName("全角");
    private final String eplId = "employeeId";
    private final String opCode = "code";
    private final SettingClassificationCommon settingCommon = SettingClassificationCommon.FREE_SETTING;
    @Test
    public void test_01() {
        new Expectations() {
            {
                require.checkTheStandard(opCode);
                result = true;
            }
        };
        val actual = WorkStatusOutputSettings.checkDuplicateStandardSelections(require, opCode);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_02() {
        new Expectations() {
            {
                require.checkFreedom(opCode, eplId);
                result = true;
            }
        };
        val actual = WorkStatusOutputSettings.checkDuplicateFreeSettings(require, opCode, eplId);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_03() {
        String settingId = "settingId";
        val outputSettings = DumData.dum(code,name,eplId, settingId,settingCommon);
        NtsAssert.invokeGetters(outputSettings);
    }


}
