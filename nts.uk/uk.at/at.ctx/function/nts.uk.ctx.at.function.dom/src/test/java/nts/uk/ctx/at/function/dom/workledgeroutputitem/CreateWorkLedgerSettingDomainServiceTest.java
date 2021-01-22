package nts.uk.ctx.at.function.dom.workledgeroutputitem;

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

/**
 * CreateWorkLedgerSettingDomainService UnitTest
 *
 * @author khai.dh
 */
@RunWith(JMockit.class)
public class CreateWorkLedgerSettingDomainServiceTest {
    @Injectable
    CreateWorkLedgerSettingDomainService.Require require;

    /**
     * Test createSetting method
     * <p>
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * WorkLedgerOutputItem.checkDuplicateStandardSelection returns TRUE
     * Expect:
     * BusinessException: "Msg_1927"
     */
    @Test
    public void testCreateSetting_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode01");
        OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName01");

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId01";
        }};

        new Expectations(WorkLedgerOutputItem.class) {{
            WorkLedgerOutputItem.checkDuplicateStandardSelection(require, code);
            result = true;
        }};

        NtsAssert.businessException("Msg_1927", () -> {
            CreateWorkLedgerSettingDomainService.createSetting(
                    require,
                    code,
                    name,
                    SettingClassificationCommon.STANDARD_SELECTION,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))
            );
        });
    }

    /**
     * Test createSetting method
     * <p>
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * WorkLedgerOutputItem.checkDuplicateFreeSettings returns TRUE
     * Expect:
     * BusinessException: "Msg_1927"
     */
    @Test
    public void testCreateSetting_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode02");
        OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName02");

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId02";
        }};

        new Expectations(WorkLedgerOutputItem.class) {{
            WorkLedgerOutputItem.checkDuplicateFreeSettings(require, code, "employeeId02");
            result = true;
        }};

        NtsAssert.businessException("Msg_1927", () -> {
            CreateWorkLedgerSettingDomainService.createSetting(
                    require,
                    code,
                    name,
                    SettingClassificationCommon.FREE_SETTING,
                    Arrays.asList(new AttendanceItemToPrint(1, 1))
            );
        });
    }

    /**
     * Test createSetting method
     * <p>
     * Condition:
     * input param: settingCategory == STANDARD_SELECTION
     * WorkLedgerOutputItem.checkDuplicateStandardSelection returns FALSE
     * Expect:
     * returns AtomTask with invocation to require.createWorkLedgerOutputSetting
     */
    @Test
    public void testCreateSetting_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode03");
        OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName03");
        val attendanceIdList = Arrays.asList(31, 32);

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId03";
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid03";
        }};

        new Expectations(WorkLedgerOutputItem.class) {{
            WorkLedgerOutputItem.checkDuplicateStandardSelection(require, code);
            result = false;
        }};

        val actual = CreateWorkLedgerSettingDomainService.createSetting(
                require,
                code,
                name,
                SettingClassificationCommon.STANDARD_SELECTION,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.createWorkLedgerOutputSetting(any.get())
        );
    }

    /**
     * Test createSetting method
     * <p>
     * Condition:
     * input param: settingCategory == FREE_SETTING
     * WorkLedgerOutputItem.checkDuplicateFreeSettings returns FALSE
     * Expect:
     * returns AtomTask with invocation to require.createWorkLedgerOutputSetting
     */
    @Test
    public void testCreateSetting_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("OutputItemSettingCode04");
        OutputItemSettingName name = new OutputItemSettingName("OutputItemSettingName04");
        val attendanceIdList = Arrays.asList(41, 42);
        val rankingList = Arrays.asList(43, 44);

        new Expectations(AppContexts.class) {{
            AppContexts.user().employeeId();
            result = "employeeId04";
        }};

        new Expectations(IdentifierUtil.class) {{
            IdentifierUtil.randomUniqueId();
            result = "uid04";
        }};

        new Expectations(WorkLedgerOutputItem.class) {{
            WorkLedgerOutputItem.checkDuplicateFreeSettings(require, code, "employeeId04");
            result = false;
        }};

        val actual = CreateWorkLedgerSettingDomainService.createSetting(
                require,
                code,
                name,
                SettingClassificationCommon.FREE_SETTING,
                Arrays.asList(new AttendanceItemToPrint(1, 1))
        );

        NtsAssert.atomTask(
                () -> actual,
                any -> require.createWorkLedgerOutputSetting(any.get())
        );
    }
}