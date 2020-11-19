package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;


@RunWith(JMockit.class)
public class CreateWorkStatusSettingDServiceTest {
    @Injectable
    private CreateWorkStatusSettingDomainService.Require require;

    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");

    private final String cid = "companyId";
    private final String empId = "employeeId";

    private final String settingId = "settingId";

    private final String iD = "id";

    private final List<OutputItem> outputItems = DumData.outputItems;

    @Test
    public void test_01() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;

            }
        };
        new Expectations() {
            {
                require.checkTheStandard(code.v());
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            CreateWorkStatusSettingDomainService.createSetting(require, code, name, settingCategory,
                    outputItems);
        });
    }

    @Test
    public void test_02() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;
            }
        };
        new Expectations() {
            {
                require.checkFreedom(code.v(), empId);
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            CreateWorkStatusSettingDomainService.createSetting(require, code, name, settingCategory,
                    outputItems);
        });
    }

    @Test
    public void test_03() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations(AppContexts.class) {
            {

                AppContexts.user().employeeId();
                result = empId;
            }
        };
        new Expectations() {
            {
                require.checkTheStandard(code.v());
                result = false;
            }
        };
        new Expectations(IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = iD;
            }
        };
        NtsAssert.atomTask(() ->
                        CreateWorkStatusSettingDomainService.createSetting(require, code, name, settingCategory,
                                outputItems),

                any -> require.createNewFixedPhrase(any.get())
        );

    }
    @Test
    public void test_04() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;
            }
        };
        new Expectations() {
            {
                require.checkFreedom(code.v(), empId);
                result = false;
            }
        };
        new Expectations(IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = iD;
            }
        };
        NtsAssert.atomTask(() ->
                        CreateWorkStatusSettingDomainService.createSetting(require, code, name, settingCategory,
                                outputItems),

                any -> require.createNewFixedPhrase(any.get())
        );

    }

}
