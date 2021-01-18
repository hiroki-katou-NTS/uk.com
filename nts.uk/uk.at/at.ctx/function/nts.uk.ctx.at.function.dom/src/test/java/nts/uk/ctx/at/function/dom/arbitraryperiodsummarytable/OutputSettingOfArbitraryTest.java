package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class OutputSettingOfArbitraryTest {
    @Injectable
    OutputSettingOfArbitrary.Require require;

    @Test
    public void test_01() {
        val code = new OutputItemSettingCode("OutputItemSettingCode01");
        new Expectations() {{
            require.checkTheFixedForm(code);
            result = true;
        }};
        val actual = OutputSettingOfArbitrary.checkDuplicateBoilerplateSelection(require, code);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_02() {
        val code = new OutputItemSettingCode("OutputItemSettingCode02");

        new Expectations() {{
            require.freeDomCheck(code, "emp02");
            result = true;
        }};
        val actual = OutputSettingOfArbitrary.checkDuplicateFreeSettings(require, code, "emp02");
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_03() {
        val code = new OutputItemSettingCode("OutputItemSettingCode03");
        val name = new OutputItemSettingName("OutputItemSettingName03");
        val outputSettings = new OutputSettingOfArbitrary(
                "id03",
                code,
                name,
                "emp03",
                SettingClassificationCommon.STANDARD_SELECTION,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
                );

        NtsAssert.invokeGetters(outputSettings);
    }
}
