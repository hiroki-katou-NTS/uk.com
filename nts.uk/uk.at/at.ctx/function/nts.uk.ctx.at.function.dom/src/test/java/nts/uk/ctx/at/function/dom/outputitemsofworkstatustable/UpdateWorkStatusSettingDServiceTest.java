package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(JMockit.class)
public class UpdateWorkStatusSettingDServiceTest {
    @Injectable
    UpdateWorkStatusSettingDomainService.Require require;

    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");

    private final String iD = "iD";
    private final String empId = "employeeId";

    private final String settingId = "settingId";
    private final SettingClassificationCommon settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
    private final List<OutputItem> outputItems = DumData.outputItems;
    private final WorkStatusOutputSettings domain = DumData.dum(code,name,empId,iD,settingCategory);

    @Test
    public void test_01() {

        new Expectations() {
            {
                require.getWorkStatusOutputSettings(settingId);
                result = null;
            }
        };
        NtsAssert.businessException("Msg_1903", () -> {
            UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
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
                require.getWorkStatusOutputSettings(settingId);
                result = domain;
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(any.get()));
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
                require.getWorkStatusOutputSettings(settingId);
                result = domain;
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(any.get()));
    }
}
