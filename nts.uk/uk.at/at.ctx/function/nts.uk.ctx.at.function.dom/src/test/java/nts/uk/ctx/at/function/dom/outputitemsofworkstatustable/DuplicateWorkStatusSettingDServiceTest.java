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


@RunWith(JMockit.class)
public class DuplicateWorkStatusSettingDServiceTest {
    @Injectable
    private DuplicateWorkStatusSettingDomainService.Require require;
    private final SettingClassificationCommon settingCategoryFree = SettingClassificationCommon.FREE_SETTING;
    private final SettingClassificationCommon settingCategoryStandard = SettingClassificationCommon.STANDARD_SELECTION;
    private final OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");
    private final String cid = "companyId";
    private final String employeeId = "employeeId";
    private final String settingId = "settingId";
    private final List<OutputItem> outputItems = CreateDomain.outputItems;

    @Test
    public void test_01() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = cid;
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(settingId);
                result = null;
            }
        };
        NtsAssert.businessException("Msg_1903", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategoryFree, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_02() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(settingId);
                result = outputItems;

                require.checkTheStandard(outputItemSettingCode.v());
                result = true;

            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategoryStandard, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_03() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(settingId);
                result = outputItems;

                require.checkFreedom(outputItemSettingCode.v(), employeeId);
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategoryStandard, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_04() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";
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
                require.getWorkStatusOutputSettings(settingId);
                result = outputItems;

                require.checkFreedom(outputItemSettingCode.v(), employeeId);
                result = false;

            }
        };
        NtsAssert.atomTask(() ->
                        DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategoryFree, settingId, outputItemSettingCode,
                                outputItemSettingName),
                any -> require.duplicateConfigurationDetails(cid, settingId, outputItemSettingCode, outputItemSettingName)
        );
    }

    @Test
    public void test_05() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().employeeId();
                result = "employeeId";
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
                require.getWorkStatusOutputSettings(settingId);
                result = outputItems;

                require.checkTheStandard(outputItemSettingCode.v());
                result = false;

            }
        };
        NtsAssert.atomTask(() ->
                        DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategoryFree, settingId, outputItemSettingCode,
                                outputItemSettingName),
                any -> require.duplicateConfigurationDetails(cid, settingId, outputItemSettingCode, outputItemSettingName)
        );

    }

}