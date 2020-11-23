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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class DuplicateAnualWorkLedgerDSTest {

    @Injectable
    private DuplicateAnualWorkLedgerDomainService.Require require;

    private final List<OutputItem> outputItems = DumData.outputItems;
    private final List<DailyOutputItemsAnnualWorkLedger> dailyoutputItems = DumData.dailyOutputItemList;

    private final OutputItemSettingCode code = new OutputItemSettingCode("code");
    private final OutputItemSettingName name = new OutputItemSettingName("name");

    private final String settingId = "settingId";

    private final WorkStatusOutputSettings domain = DumData.dum(code,name,"empId",settingId,SettingClassificationCommon.STANDARD_SELECTION);

    @Test
    public void test_01() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";
            }
        };
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.empty();
            }
        };
        NtsAssert.businessException("Msg_1898", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code,name);
        });
    }

    @Test
    public void test_02() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";
            }
        };
        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code,name,"empId",settingId,SettingClassificationCommon.STANDARD_SELECTION));

                require.checkTheStandard(code,"cid");
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, code,name);
        });
    }

    @Test
    public void test_03() {
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
                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code,name,"empId",settingId,SettingClassificationCommon.FREE_SETTING));

                require.checkFreedom(code,"cid","sid");
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code,name);
        });
    }

    @Test
    public void test_04() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                AppContexts.user().companyId();
                result = "cid";
            }
        };
        new Expectations(IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = "id";
            }
        };

        new Expectations() {
            {
                require.getSetting(settingId);
                result = Optional.of(domain);
            }
        };
        NtsAssert.atomTask(() ->
                DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code,name),
            any -> require.duplicate(any.get(), any.get(),any.get(), any.get(),any.get())
        );

    }


}
