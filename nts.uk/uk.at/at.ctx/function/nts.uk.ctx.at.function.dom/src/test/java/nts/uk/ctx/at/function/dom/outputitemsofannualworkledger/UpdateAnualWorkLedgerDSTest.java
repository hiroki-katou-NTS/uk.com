package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DumData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class UpdateAnualWorkLedgerDSTest {

    @Injectable
    private UpdateAnualWorkLedgerDomainService.Require require;

    private final List<OutputItem> outputItems = DumData.outputItems;
    private final List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;

    private final OutputItemSettingCode code = new OutputItemSettingCode("code");
    private final OutputItemSettingName name = new OutputItemSettingName("name");

    private final String settingId = "settingId";

    private final WorkStatusOutputSettings domain = DumData.dum(code,name,"empId",settingId,SettingClassificationCommon.STANDARD_SELECTION);

    @Test
    public void test_01() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1898", () -> {
            UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name, settingCategory,dailyoutputItems,outputItems);
        });
    }

    @Test
    public void test_02() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;

        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code,name,"empId",settingId,settingCategory));
            }
        };

        NtsAssert.atomTask(() ->
                UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name, settingCategory,
                    dailyoutputItems,outputItems),

            any -> require.updateSetting(any.get())
        );

    }

    @Test
    public void test_03() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";
            }
        };

        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code,name,"empId",settingId,SettingClassificationCommon.FREE_SETTING));
            }
        };

        NtsAssert.atomTask(() ->
                UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name, settingCategory,
                    dailyoutputItems,outputItems),

            any -> require.updateSetting(any.get())
        );

    }

}
