package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;

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

@RunWith(JMockit.class)
public class CreateArbitraryScheduleDomainServiceTest {
    @Injectable
    CreateArbitraryScheduleDomainService.Require require;

    /**
     * Test createSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testCreateSchedule_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId";

            require.standardCheck(code);
            result = true;
        }};


        NtsAssert.businessException("Msg_1893", () -> {
            CreateArbitraryScheduleDomainService.createSchedule(
                    require,
                    code,
                    name,
                    SettingClassificationCommon.STANDARD_SELECTION,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))
            );
        });
    }

    /**
     * Test createSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testCreateSchedule_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId01";

            require.freeCheck(code, "employeeId01");
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            CreateArbitraryScheduleDomainService.createSchedule(
                    require,
                    code,
                    name,
                    SettingClassificationCommon.FREE_SETTING,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))
            );
        });
    }

    /**
     * Test createSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * Expect:
     * returns AtomTask with invocation to require.createSchedule
     */
    @Test
    public void testCreateSchedule_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class, IdentifierUtil.class) {{
            AppContexts.user().employeeId();
            result = "employeeId02";

            IdentifierUtil.randomUniqueId();
            result = "uid03";

            require.standardCheck(code);
            result = false;
        }};

        val actual = CreateArbitraryScheduleDomainService.createSchedule(
                require,
                code,
                name,
                SettingClassificationCommon.STANDARD_SELECTION,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.createSchedule(any.get())
        );
    }

    /**
     * Test createSetting method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * Expect:
     * returns AtomTask with invocation to require.createSchedule
     */
    @Test
    public void testCreateSetting_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class, IdentifierUtil.class) {{
            AppContexts.user().employeeId();
            result = "employeeId03";

            IdentifierUtil.randomUniqueId();
            result = "uid04";

            require.freeCheck(code, "employeeId03");
            result = false;
        }};

        val actual = CreateArbitraryScheduleDomainService.createSchedule(
                require,
                code,
                name,
                SettingClassificationCommon.FREE_SETTING,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.createSchedule(any.get())
        );
    }
}
