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
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Optional;

@RunWith(JMockit.class)
public class UpdateArbitraryScheduleDomainServiceTest {

    @Injectable
    UpdateArbitraryScheduleDomainService.Require require;


    /**
     * Test UpdateArbitraryScheduleDomainService.
     * Method: updateSchedule
     * Condition:
     * - Input: SettingClassificationCommon = SettingClassificationCommon.FREE_SETTING
     * - require.getOutputSettingDetail returns empty item
     * Expect:
     * BusinessException: "Msg_1914"
     */

    @Test
    public void testUpdateSchedule_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations() {
            {
                require.getOutputSetting("uid");
                result = Optional.empty();
            }
        };
        Runnable runnable = () -> UpdateArbitraryScheduleDomainService.updateSchedule(
                require,
                "uid",
                code,
                name,
                SettingClassificationCommon.FREE_SETTING,
                Arrays.asList(new AttendanceItemToPrint(1, 1)));
        NtsAssert.businessException("Msg_1914", runnable);
    }

    /**
     * Test UpdateArbitraryScheduleDomainService.
     * Method: updateSchedule
     * Condition:
     * - Input: SettingClassificationCommon = SettingClassificationCommon.STANDARD_SELECTION
     * - require.getOutputSettingDetail returns empty item
     * Expect:
     * BusinessException: "Msg_1914"
     */
    @Test
    public void testUpdateSchedule_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations() {
            {
                require.getOutputSetting("uid");
                result = Optional.empty();
            }
        };
        Runnable runnable = () -> UpdateArbitraryScheduleDomainService.updateSchedule(
                require,
                "uid",
                code,
                name,
                SettingClassificationCommon.STANDARD_SELECTION,
                Arrays.asList(new AttendanceItemToPrint(1, 1)));
        NtsAssert.businessException("Msg_1914", runnable);
    }

    /**
     * Test updateSetting method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * require.getOutputSetting returns non empty item
     * Expect:
     * returns AtomTask with invocation to require.updateSchedule
     */
    @Test
    public void testUpdateSetting_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {{
            require.getOutputSetting("uid");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId03"),
                    SettingClassificationCommon.STANDARD_SELECTION,
                    Arrays.asList(new AttendanceItemToPrint(1, 1)))
            );
            AppContexts.user().employeeId();
            result = "employeeId03";
        }};

        val actual = UpdateArbitraryScheduleDomainService.updateSchedule(
                require,
                "uid",
                code,
                name,
                SettingClassificationCommon.STANDARD_SELECTION,
                Arrays.asList(new AttendanceItemToPrint(1, 1)));


        NtsAssert.atomTask(
                () -> actual,
                any -> require.updateSchedule("uid", any.get())
        );
    }

    /**
     * Test updateSetting method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * require.getOutputSetting returns non empty item
     * Expect:
     * returns AtomTask with invocation to require.updateSchedule
     */
    @Test
    public void testUpdateSetting_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {{
            require.getOutputSetting("uid");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId03"),
                    SettingClassificationCommon.FREE_SETTING,
                    Arrays.asList(new AttendanceItemToPrint(1, 1)))
            );
            AppContexts.user().employeeId();
            result = "employeeId03";

        }};

        val actual = UpdateArbitraryScheduleDomainService.updateSchedule(
                require,
                "uid",
                code,
                name,
                SettingClassificationCommon.FREE_SETTING,
                Arrays.asList(new AttendanceItemToPrint(1, 1)));


        NtsAssert.atomTask(
                () -> actual,
                any -> require.updateSchedule("uid", any.get())
        );
    }
}
