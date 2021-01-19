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
    private static final OutputItemSettingCode code = new OutputItemSettingCode("code");
    private static final OutputItemSettingName name = new OutputItemSettingName("name");
    private static final SettingClassificationCommon freeSetting = SettingClassificationCommon.FREE_SETTING;
    private static final SettingClassificationCommon standardSelection = SettingClassificationCommon.STANDARD_SELECTION;

    /**
     * Test createSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testCreateSchedule_01() {

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId01";
        }};

        new Expectations() {{
            require.standardCheck(code);
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            CreateArbitraryScheduleDomainService.createSchedule(
                    require,
                    code,
                    name,
                    standardSelection,
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


        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId02";
        }};

        new Expectations() {{
            require.freeCheck(code, "employeeId02");
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            CreateArbitraryScheduleDomainService.createSchedule(
                    require,
                    code,
                    name,
                    freeSetting,
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

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId03";
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid03";
        }};

        new Expectations() {{
            require.standardCheck(code);
            result = false;
        }};

        val actual = CreateArbitraryScheduleDomainService.createSchedule(
                require,
                code,
                name,
                standardSelection,
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

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId04";
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid04";
        }};

        new Expectations() {{
            require.freeCheck(code, "employeeId04");
            result = false;
        }};

        val actual = CreateArbitraryScheduleDomainService.createSchedule(
                require,
                code,
                name,
                freeSetting,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.createSchedule(any.get())
        );
    }
}
