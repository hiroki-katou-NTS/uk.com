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


    /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * Expect:
     * BusinessException: "Msg_1914"
     */
    @Test
    public void testDuplicateSchedule() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations() {{
            require.getOutputSetting("dupSrcId02");
            result = Optional.empty();
        }};
        NtsAssert.businessException("Msg_1914", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    SettingClassificationCommon.STANDARD_SELECTION,
                    "dupSrcId02",
                    code,
                    name
            );
        });
    } /**
     * Test DuplicateSchedule method
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * Expect:
     * BusinessException: "Msg_1914"
     */
    @Test
    public void testDuplicateSchedule_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations() {{
            require.getOutputSetting("dupSrcId02");
            result = Optional.empty();
        }};
        NtsAssert.businessException("Msg_1914", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    SettingClassificationCommon.FREE_SETTING,
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
    public void testDuplicateSchedule_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId";

            require.getOutputSetting("dupSrcId02");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId"),
                    SettingClassificationCommon.FREE_SETTING,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));

            require.freeCheck(code, "employeeId");
            result = true;

        }};


        NtsAssert.businessException("Msg_1893", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    SettingClassificationCommon.FREE_SETTING,
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
    public void testDuplicateSchedule_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId02";

            require.getOutputSetting("dupSrcId02");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId"),
                    SettingClassificationCommon.STANDARD_SELECTION,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));

            require.standardCheck(code);
            result = true;
        }};

        NtsAssert.businessException("Msg_1893", () -> {
            DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                    require,
                    SettingClassificationCommon.STANDARD_SELECTION,
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
     * require.getOutputSetting returns non empty item
     * require.standardCheck : false
     * Expect:
     * returns AtomTask with invocation to require.duplicateArbitrarySchedule
     */
    @Test
    public void testDuplicateSchedule_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class, IdentifierUtil.class) {{
            AppContexts.user().employeeId();
            result = "employeeId04";

            require.getOutputSetting("dupSrcId04");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId"),
                    SettingClassificationCommon.STANDARD_SELECTION,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));

            require.standardCheck(code);
            result = false;

            IdentifierUtil.randomUniqueId();
            result = "uid04";
        }};


        val actual = DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                require,
                SettingClassificationCommon.STANDARD_SELECTION,
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
     * require.freeCheck: false
     * Expect:
     * returns AtomTask with invocation to require.duplicateArbitrarySchedule
     */
    @Test
    public void testDuplicateSchedule_05() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class, IdentifierUtil.class) {{
            AppContexts.user().employeeId();
            result = "employeeId05";

            require.getOutputSetting("dupSrcId05");
            result = Optional.of(new OutputSettingOfArbitrary(
                    "uid",
                    code,
                    name,
                    Optional.of("employeeId"),
                    SettingClassificationCommon.FREE_SETTING,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))));

            require.freeCheck(code, "employeeId05");
            result = false;

            IdentifierUtil.randomUniqueId();
            result = "uid05";
        }};


        val actual = DuplicateArbitraryScheduleDomainService.duplicateSchedule(
                require,
                SettingClassificationCommon.FREE_SETTING,
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
