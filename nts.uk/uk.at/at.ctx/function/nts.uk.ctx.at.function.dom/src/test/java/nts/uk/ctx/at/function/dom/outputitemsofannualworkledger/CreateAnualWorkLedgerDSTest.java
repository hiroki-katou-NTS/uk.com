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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(JMockit.class)
public class CreateAnualWorkLedgerDSTest {

    @Injectable
    private CreateAnualWorkLedgerDomainService.Require require;

    private final List<OutputItem> outputItems = DumData.outputItems;
    private final List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;

    private final OutputItemSettingCode code = new OutputItemSettingCode("code");
    private final OutputItemSettingName name = new OutputItemSettingName("name");

    @Test
    public void test_01() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";
            }
        };
        new Expectations() {
            {
                require.checkTheStandard(code,"cid");
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1859", () -> {
            CreateAnualWorkLedgerDomainService.createSetting(require, code, name, settingCategory,dailyoutputItems,outputItems);
        });
    }

    @Test
    public void test_02() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                AppContexts.user().companyId();
                result = "cid";
            }
        };
        new Expectations() {
            {
                require.checkFreedom(code,"cid","sid");
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1859", () -> {
            CreateAnualWorkLedgerDomainService.createSetting(require, code, name, settingCategory,dailyoutputItems,outputItems);
        });
    }

    @Test
    public void test_03() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";
            }
        };

        new Expectations() {
            {
                require.checkTheStandard(code,"cid");
                result = false;
            }
        };
        new Expectations(IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = "id";
            }
        };
        NtsAssert.atomTask(() ->
                CreateAnualWorkLedgerDomainService.createSetting(require, code, name, settingCategory,
                    dailyoutputItems,outputItems),

            any -> require.createNewSetting(any.get())
        );

    }

    @Test
    public void test_04() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                AppContexts.user().employeeId();
                result = "sid";
            }
        };

        new Expectations() {
            {
                require.checkFreedom(code,"cid","sid");
                result = false;
            }
        };
        new Expectations(IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = "id";
            }
        };
        NtsAssert.atomTask(() ->
                CreateAnualWorkLedgerDomainService.createSetting(require, code, name, settingCategory,
                    dailyoutputItems,outputItems),

            any -> require.createNewSetting(any.get())
        );

    }

}
