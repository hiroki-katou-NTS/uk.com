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
import java.util.Optional;


@RunWith(JMockit.class)
public class DuplicateWorkStatusSettingDomainServiceTest {
    @Injectable
    private DuplicateWorkStatusSettingDomainService.Require require;

    /**
     * Test: DuplicateWorkStatusSettingDomainService
     * Throw exception : Msg_1903
     */
    @Test
    public void testDuplicateWorkStatusSettingFail_01() {
        OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
        OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
        String settingId = "settingId";
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1903", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }
    /**
     * Test: DuplicateWorkStatusSettingDomainService
     * Throw exception : Msg_1753
     * SettingClassificationCommon.STANDARD_SELECTION
     */
    @Test
    public void testDuplicateWorkStatusSettingFail_02() {
        OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
        OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
        String iD = "iD";
        String employeeId = "employeeId";
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(outputItemSettingCode, outputItemSettingName,
                employeeId, iD, SettingClassificationCommon.STANDARD_SELECTION);
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.of(domain);

                require.exist(outputItemSettingCode.v());
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }
    /**
     * Test: DuplicateWorkStatusSettingDomainService
     * Throw exception : Msg_1753
     * SettingClassificationCommon.FREE_SETTING
     */
    @Test
    public void testDuplicateWorkStatusSettingFail_03() {
        OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
        OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
        String iD = "iD";
        String employeeId = "employeeId";
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(outputItemSettingCode, outputItemSettingName,
                employeeId, iD, SettingClassificationCommon.FREE_SETTING);
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.of(domain);

                require.exist(outputItemSettingCode.v(), employeeId);
                result = true;

            }
        };

        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }
    /**
     * Test: DuplicateWorkStatusSettingDomainService
     * SettingClassificationCommon.FREE_SETTING
     * DUPLICATE : success
     */
    @Test
    public void testDuplicateWorkStatusSettingSuccess_04() {

        OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
        OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
        String iD = "iD";
        String employeeId = "employeeId";
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(outputItemSettingCode, outputItemSettingName,
                employeeId, iD, SettingClassificationCommon.FREE_SETTING);
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";

                IdentifierUtil.randomUniqueId();
                result = iD;

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.of(domain);

                require.exist(outputItemSettingCode.v(), employeeId);
                result = false;
            }
        };

        NtsAssert.atomTask(() ->
                        DuplicateWorkStatusSettingDomainService.duplicate(require, SettingClassificationCommon.FREE_SETTING, settingId, outputItemSettingCode,
                                outputItemSettingName),
                any -> require.duplicateConfigurationDetails(settingId, iD, outputItemSettingCode, outputItemSettingName)
        );
    }
    /**
     * Test: DuplicateWorkStatusSettingDomainService
     * SettingClassificationCommon.STANDARD_SELECTION
     * DUPLICATE : success
     */
    @Test
    public void testDuplicateWorkStatusSettingSuccess_05() {

        OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
        OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
        String iD = "iD";
        String employeeId = "employeeId";
        String settingId = "settingId";
        WorkStatusOutputSettings domain = DumData.dum(outputItemSettingCode, outputItemSettingName,
                employeeId, iD, SettingClassificationCommon.STANDARD_SELECTION);
        new Expectations(AppContexts.class, IdentifierUtil.class) {
            {
                AppContexts.user().employeeId();
                result = employeeId;

                IdentifierUtil.randomUniqueId();
                result = iD;

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.of(domain);

                require.exist(outputItemSettingCode.v());
                result = false;

                require.getWorkStatusOutputSettings(settingId);
                result = Optional.of(domain);

                require.exist(outputItemSettingCode.v());
                result = false;
            }
        };

        NtsAssert.atomTask(() ->
                        DuplicateWorkStatusSettingDomainService.duplicate(require, SettingClassificationCommon.STANDARD_SELECTION, settingId, outputItemSettingCode,
                                outputItemSettingName),
                any -> require.duplicateConfigurationDetails(settingId, iD, outputItemSettingCode, outputItemSettingName)
        );

    }

}