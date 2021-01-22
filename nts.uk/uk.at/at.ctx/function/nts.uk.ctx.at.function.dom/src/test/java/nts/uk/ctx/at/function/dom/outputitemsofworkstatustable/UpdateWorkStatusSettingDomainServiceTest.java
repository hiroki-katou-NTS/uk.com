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
public class UpdateWorkStatusSettingDomainServiceTest {
    @Injectable
    UpdateWorkStatusSettingDomainService.Require require;


    /**
     * Test: UpdateWorkStatusSettingDomainService
     * Throw exception : Msg_1903
     */
    @Test
    public void testUpdateWorkStatusSettingFail_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String settingId = "settingId";
        SettingClassificationCommon settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        List<OutputItem> outputItems = DumData.outputItems;
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

    /**
     * Test: UpdateWorkStatusSettingDomainService : update success
     * SettingClassificationCommon.FREE_SETTING
     */
    @Test
    public void testUpdateWorkStatusSettingSuccess_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String iD = "iD";
        String empId = "employeeId";
        String settingId = "settingId";
        List<OutputItem> outputItems = DumData.outputItems;
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        WorkStatusOutputSettings domain = DumData.dum(code, name, empId, iD, settingCategory);
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;

                require.getWorkStatusOutputSettings(settingId);
                result = domain;

            }
        };


        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(settingId, any.get()));
    }

    /**
     * Test: UpdateWorkStatusSettingDomainService : update success
     * SettingClassificationCommon.STANDARD_SELECTION
     */
    @Test
    public void testUpdateWorkStatusSettingSuccess_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String iD = "iD";
        String empId = "employeeId";
        String settingId = "settingId";
        SettingClassificationCommon settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        List<OutputItem> outputItems = DumData.outputItems;
        WorkStatusOutputSettings domain = DumData.dum(code, name, empId, iD, settingCategory);
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = empId;
                require.getWorkStatusOutputSettings(settingId);
                result = domain;
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(settingId, any.get()));
    }
}
