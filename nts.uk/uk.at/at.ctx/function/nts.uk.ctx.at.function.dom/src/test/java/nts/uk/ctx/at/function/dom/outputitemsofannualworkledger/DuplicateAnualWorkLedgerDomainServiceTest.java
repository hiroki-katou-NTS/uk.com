package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DumData;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JMockit.class)
public class DuplicateAnualWorkLedgerDomainServiceTest {

    @Injectable
    private DuplicateAnualWorkLedgerDomainService.Require require;

    /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * Throw exception : Msg_1898
     * SettingClassificationCommon.STANDARD_SELECTION
     */
    @Test
    public void testDuplicateAnualWorkLedgerFail() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "eplId";

                require.getSetting(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1898", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, code, name);
        });
    }  /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * Throw exception : Msg_1898
     * SettingClassificationCommon.FREE_SETTING
     */
    @Test
    public void testDuplicateAnualWorkLedgerFail_01() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                require.getSetting(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1898", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code, name);
        });
    }
    /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * Throw exception : Msg_1859
     * SettingClassificationCommon.STANDARD_SELECTION
     */
    @Test
    public void testDuplicateAnualWorkLedgerFail_02() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code, name, "empId", settingId, SettingClassificationCommon.STANDARD_SELECTION));

                require.exist(code);
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1859", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, code, name);
        });
    }
    /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * Throw exception : Msg_1859
     * SettingClassificationCommon.FREE_SETTING
     */
    @Test
    public void testDuplicateAnualWorkLedgerFail_03() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                require.getSetting(settingId);
                result = Optional.of(DumData.dum(code, name, "empId", settingId, SettingClassificationCommon.FREE_SETTING));

                require.exist(code, "sid");
                result = true;
            }
        };

        NtsAssert.businessException("Msg_1859", () -> {
            DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code, name);
        });
    }
    /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * DUPLICATE : success
     */
    @Test
    public void testDuplicateAnualWorkLedgerSuccess_04() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(code, name, "empId", settingId, SettingClassificationCommon.STANDARD_SELECTION);
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                AppContexts.user().employeeId();
                result = "sid";

                IdentifierUtil.randomUniqueId();
                result = "id";

                require.getSetting(settingId);
                result = Optional.of(domain);
            }
        };
        NtsAssert.atomTask(() ->
                        DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, code, name),
                any -> require.duplicate(any.get(), any.get(), any.get(), any.get())
        );

    }
    /**
     * Test: DuplicateAnualWorkLedgerDomainService
     * SettingClassificationCommon.FREE_SETTING
     * DUPLICATE : success
     */
    @Test
    public void testDuplicateAnualWorkLedgerSuccess_05() {
        OutputItemSettingCode code = new OutputItemSettingCode("code");
        OutputItemSettingName name = new OutputItemSettingName("name");
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(code, name, "empId", settingId, SettingClassificationCommon.FREE_SETTING);
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                IdentifierUtil.randomUniqueId();
                result = "id";

                AppContexts.user().employeeId();
                result = "sid";

                require.getSetting(settingId);
                result = Optional.of(domain);
            }
        };
        NtsAssert.atomTask(() ->
                        DuplicateAnualWorkLedgerDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, code, name),
                any -> require.duplicate(any.get(), any.get(), any.get(), any.get())
        );

    }


}
