package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DumData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class UpdateAnualWorkLedgerDomainServiceTest {

    @Injectable
    private UpdateAnualWorkLedgerDomainService.Require require;

    /**
     * Test: UpdateAnualWorkLedgerDomainService
     * Throw exception : Msg_1898
     */
    @Test
    public void testUpdateAnualWorkLedgerFail_01() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1898", () -> {
            UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name,
                    SettingClassificationCommon.STANDARD_SELECTION, dailyoutputItems, outputItems);
        });
    }

    /**
     * Test: UpdateAnualWorkLedgerDomainService
     * Throw exception : Msg_1898
     */
    @Test
    public void testUpdateAnualWorkLedgerFail_02() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1898", () -> {
            UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name,
                    SettingClassificationCommon.FREE_SETTING, dailyoutputItems, outputItems);
        });
    }

    /**
     * Test: UpdateAnualWorkLedgerDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * CREATE : SUCCESS
     */
    @Test
    public void testUpdateAnualWorkLedgerSuccess_03() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code, name, "empId", settingId,
                        SettingClassificationCommon.STANDARD_SELECTION));
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name,
                                SettingClassificationCommon.STANDARD_SELECTION,
                                dailyoutputItems, outputItems),

                any -> require.updateSetting(any.get(), any.get())
        );

    }

    /**
     * Test: UpdateAnualWorkLedgerDomainService
     * SettingClassificationCommon.FREE_SETTING
     * CREATE : SUCCESS
     */
    @Test
    public void testUpdateAnualWorkLedgerSuccess_04() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code, name, "empId", settingId,
                        SettingClassificationCommon.FREE_SETTING));
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateAnualWorkLedgerDomainService.updateSetting(require, settingId, code, name,
                                SettingClassificationCommon.FREE_SETTING,
                                dailyoutputItems, outputItems),

                any -> require.updateSetting(any.get(), any.get())
        );

    }

}
