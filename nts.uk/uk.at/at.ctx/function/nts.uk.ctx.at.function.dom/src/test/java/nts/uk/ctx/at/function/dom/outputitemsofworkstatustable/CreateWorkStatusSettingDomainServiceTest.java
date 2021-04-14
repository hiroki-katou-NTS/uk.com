package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(JMockit.class)
public class CreateWorkStatusSettingDomainServiceTest {
    @Injectable
    private CreateWorkStatusSettingDomainService.Require require;

    /**
     * Test: CreateWorkStatusSettingDomainService
     * Throw exception : Msg_1753
     * SettingClassificationCommon.STANDARD_SELECTION
     */
    @Test
    public void testCreateWorkStatusSettingFail_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String empId = "employeeId";
        List<OutputItem> outputItems = DumData.outputItems;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;

                require.checkTheStandard(code.v());
                result = true;

            }
        };

        NtsAssert.businessException("Msg_1753", () -> {
            CreateWorkStatusSettingDomainService.createSetting(require, code, name, SettingClassificationCommon.STANDARD_SELECTION,
                    outputItems);
        });
    }

    /**
     * Test: CreateWorkStatusSettingDomainService
     * Throw exception : Msg_1753
     * SettingClassificationCommon.FREE_SETTING
     */
    @Test
    public void testCreateWorkStatusSettingFail_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String empId = "employeeId";
        List<OutputItem> outputItems = DumData.outputItems;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;

                require.checkFreedom(code.v(), empId);
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            CreateWorkStatusSettingDomainService.createSetting(require, code, name, SettingClassificationCommon.FREE_SETTING,
                    outputItems);
        });
    }

    /**
     * Test: CreateWorkStatusSettingDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * CREATE : success
     */
    @Test
    public void testCreateWorkStatusSettingSuccess_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String empId = "employeeId";
        String iD = "id";
        List<OutputItem> outputItems = DumData.outputItems;

        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {

                AppContexts.user().employeeId();
                result = empId;

                require.checkTheStandard(code.v());
                result = false;

                IdentifierUtil.randomUniqueId();
                result = iD;
            }
        };

        NtsAssert.atomTask(() ->
                        CreateWorkStatusSettingDomainService.createSetting(require, code, name,
                                SettingClassificationCommon.STANDARD_SELECTION,
                                outputItems),

                any -> require.createNewFixedPhrase(any.get())
        );

    }

    /**
     * Test: CreateWorkStatusSettingDomainService
     * SettingClassificationCommon.FREE_SETTING
     * CREATE : success
     */
    @Test
    public void testCreateWorkStatusSettingSuccess_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String empId = "employeeId";
        String iD = "id";
        List<OutputItem> outputItems = DumData.outputItems;
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                AppContexts.user().employeeId();
                result = empId;

                IdentifierUtil.randomUniqueId();
                result = iD;

                require.checkFreedom(code.v(), empId);
                result = false;
            }
        };

        NtsAssert.atomTask(() ->
                        CreateWorkStatusSettingDomainService.createSetting(require, code, name, SettingClassificationCommon.FREE_SETTING,
                                outputItems),

                any -> require.createNewFixedPhrase(any.get())
        );

    }

}
