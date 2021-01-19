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
import java.util.Optional;

@RunWith(JMockit.class)
public class DuplicateArbitraryScheduleDomainServiceTest {
    @Injectable
    DuplicateArbitraryScheduleDomainService.Require require;
    private static final OutputItemSettingCode code = new OutputItemSettingCode("code");
    private static final OutputItemSettingName name = new OutputItemSettingName("name");
    private static final SettingClassificationCommon freeSetting = SettingClassificationCommon.FREE_SETTING;
    private static final SettingClassificationCommon standardSelection = SettingClassificationCommon.STANDARD_SELECTION;

    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testDuplicateSchedule_00() {
        new Expectations() {{
            require.getOutputSetting("dupSrcId02");
            result = Optional.empty();
        }};
        NtsAssert.businessException("Msg_1914", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    freeSetting,
                    "dupSrcId02",
                    code,
                    name
            );
        });
    }
    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testDuplicateSchedule_01() {
        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId";
        }};

        new Expectations() {{
            require.getOutputSetting("dupSrcId02");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    "employeeId",
                    freeSetting,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));
        }};
        new Expectations() {{
            require.freeCheck(code, "employeeId");
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    freeSetting,
                    "dupSrcId02",
                    code,
                    name
            );
        });
    }

    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testDuplicateSchedule_02() {
        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId02";
        }};

        new Expectations() {{
            require.getOutputSetting("dupSrcId02");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    "employeeId03",
                    standardSelection,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));
        }};
        new Expectations() {{
            require.standardCheck(code);
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    standardSelection,
                    "dupSrcId02",
                    code,
                    name
            );
        });
    }

    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * require.getOutputSetting returns non empty item
     * Expect:
     * BusinessException: "Msg_1893"
     */
    @Test
    public void testDuplicateSchedule_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode03");
        OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName03");

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId03";
        }};

        new Expectations() {{
            require.getOutputSetting("dupSrcId03");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    "employeeId03",
                    standardSelection,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));
        }};

        new Expectations() {{
            require.freeCheck(code, "employeeId03");
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    SettingClassificationCommon.FREE_SETTING,
                    "dupSrcId03",
                    code,
                    name
            );
        });
    }

    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * require.getOutputSetting returns non empty item
     * Expect:
     * returns AtomTask with invocation to require.duplicateArbitrarySchedule
     */
    @Test
    public void testDuplicateSchedule_04() {
        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId04";
        }};

        new Expectations() {{
            require.getOutputSetting("dupSrcId04");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    "employeeId03",
                    standardSelection,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));
        }};

        new Expectations() {{
            require.standardCheck(code);
            result = false;
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid04";
        }};

        val actual = DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                require,
                standardSelection,
                "dupSrcId04",
                code,
                name
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.duplicateArbitrarySchedule("dupSrcId04", "uid04", code, name)
        );
    }

    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * require.getOutputSetting returns non empty item
     * Expect:
     * returns AtomTask with invocation to require.duplicateArbitrarySchedule
     */
    @Test
    public void testDuplicateSchedule_05() {

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId05";
        }};

        new Expectations() {{
            require.getOutputSetting("dupSrcId05");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    "employeeId03",
                    standardSelection,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));
        }};

        new Expectations() {{
            require.freeCheck(code, "employeeId05");
            result = false;
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid05";
        }};

        val actual = DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                require,
                freeSetting,
                "dupSrcId05",
                code,
                name
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.duplicateArbitrarySchedule("dupSrcId05", "uid05", code, name)
        );
    }
}
