package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
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


@RunWith(JMockit.class)
public class CreateAnualWorkLedgerDomainServiceTest {

    @Injectable
    private CreateAnualWorkLedgerDomainService.Require require;

    /**
     * Test: CreateAnualWorkLedgerDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * Throw exception : Msg_1859
     */
    @Test
    public void testCreateAnualWorkLedgerFail_01() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid1";

                require.checkTheStandard(code);
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1859", () -> {
            CreateAnualWorkLedgerDomainService.createSetting(require, code, name, SettingClassificationCommon.STANDARD_SELECTION, dailyoutputItems, outputItems);
        });
    }

    /**
     * Test: CreateAnualWorkLedgerDomainService
     * SettingClassificationCommon.FREE_SETTING
     * Throw exception : Msg_1859
     */
    @Test
    public void testCreateAnualWorkLedgerFail_02() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;

        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                require.checkFreedom(code, "sid");
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1859", () -> {
            CreateAnualWorkLedgerDomainService.createSetting(require, code, name, SettingClassificationCommon.FREE_SETTING, dailyoutputItems, outputItems);
        });
    }

    /**
     * Test: CreateAnualWorkLedgerDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * CREATE : success
     */
    @Test
    public void testCreateAnualWorkLedgerSucess_03() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                AppContexts.user().employeeId();
                result = "sid1";

                require.checkTheStandard(code);
                result = false;

                IdentifierUtil.randomUniqueId();
                result = "id";
            }
        };
        NtsAssert.atomTask(() ->
                        CreateAnualWorkLedgerDomainService.createSetting(require, code, name,
                                SettingClassificationCommon.STANDARD_SELECTION,
                                dailyoutputItems, outputItems),

                any -> require.createNewSetting(any.get())
        );

    }

    /**
     * Test: CreateAnualWorkLedgerDomainService
     * SettingClassificationCommon.FREE_SETTING
     * CREATE : success
     */
    @Test
    public void testCreateAnualWorkLedgerSuccess_04() {
        List<OutputItemWorkLedger> outputItems = DumDataTest.outputItems;
        List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;

        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");

        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {

                AppContexts.user().employeeId();
                result = "sid";

                require.checkFreedom(code, "sid");
                result = false;

                IdentifierUtil.randomUniqueId();
                result = "id";
            }
        };

        NtsAssert.atomTask(() ->
                        CreateAnualWorkLedgerDomainService.createSetting(require, code, name,
                                SettingClassificationCommon.FREE_SETTING,
                                dailyoutputItems, outputItems),

                any -> require.createNewSetting(any.get())
        );

    }

}
