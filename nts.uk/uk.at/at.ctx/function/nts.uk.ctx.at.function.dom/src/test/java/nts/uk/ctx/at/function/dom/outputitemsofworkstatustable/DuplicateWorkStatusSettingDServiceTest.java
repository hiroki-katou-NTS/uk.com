package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.Arrays;


@RunWith(JMockit.class)
public class DuplicateWorkStatusSettingDServiceTest {
    @Injectable
    private DuplicateWorkStatusSettingDomainService.Require require;

    private final WorkStatusOutputSettings settingCategory = new WorkStatusOutputSettings(
            "settingId",
            new OutputItemSettingCode("outPutSettingCode"),
            new OutputItemSettingName("oputSettingName"),
            "employeeId",
            EnumAdaptor.valueOf(1, SettingClassificationCommon.class),
            Arrays.asList(
                    new OutputItem(
                            1,
                            new FormOutputItemName("itemName01"),
                            true,
                            EnumAdaptor.valueOf(1, IndependentCalculationClassification.class),
                            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                                    new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(2, OperatorsCommonToForms.class), 1)
                            )
                    ),
                    new OutputItem(
                            2,
                            new FormOutputItemName("itemName02"),
                            true,
                            EnumAdaptor.valueOf(2, IndependentCalculationClassification.class),
                            EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailSelectionAttendanceItem(
                                            EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                            2),
                                    new OutputItemDetailSelectionAttendanceItem(
                                            EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                            2)
                            )
                    ))

    );
    private final OutputItemSettingCode outputItemSettingCode = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName outputItemSettingName = new OutputItemSettingName("CBA");

    private final String cid = "companyId";

    private final String settingId = "settingId";

    private final String iD = "id";

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
                require.getWorkStatusOutputSettings(cid, settingId);
                result = null;
            }
        };
        NtsAssert.businessException("Msg_1903", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategory, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_02() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "companyId";
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(cid, settingId);
                result = settingCategory;

                require.checkTheStandard(outputItemSettingCode.v(), cid);
                result = true;

                require.checkFreedom(outputItemSettingCode.v(), cid, settingCategory.getEmployeeId());
                result = false;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategory, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_03() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "companyId";
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(cid, settingId);
                result = settingCategory;

                require.checkTheStandard(outputItemSettingCode.v(), cid);
                result = false;

                require.checkFreedom(outputItemSettingCode.v(), cid, settingCategory.getEmployeeId());
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategory, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_04() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "companyId";
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(cid, settingId);
                result = settingCategory;

                require.checkTheStandard(outputItemSettingCode.v(), cid);
                result = true;

                require.checkFreedom(outputItemSettingCode.v(), cid, settingCategory.getEmployeeId());
                result = true;
            }
        };
        NtsAssert.businessException("Msg_1753", () -> {
            DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategory, settingId, outputItemSettingCode,
                    outputItemSettingName);
        });
    }

    @Test
    public void test_05() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "companyId";
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
                require.getWorkStatusOutputSettings(cid, settingId);
                result = settingCategory;

                require.checkTheStandard(outputItemSettingCode.v(), cid);
                result = false;

                require.checkFreedom(outputItemSettingCode.v(), cid, settingCategory.getEmployeeId());
                result = false;
            }
        };
        NtsAssert.atomTask(() ->
                        DuplicateWorkStatusSettingDomainService.duplicate(require, settingCategory, settingId, outputItemSettingCode,
                                outputItemSettingName),
                any -> require.duplicateConfigurationDetails(cid, settingId, iD, outputItemSettingCode, outputItemSettingName)
        );

    }

}

