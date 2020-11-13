package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(JMockit.class)
public class UpdateWorkStatusSettingDServiceTest {
    @Injectable
    UpdateWorkStatusSettingDomainService.Require require;

    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");

    private final String cid = "companyId";
    private final String empId = "employeeId";

    private final String settingId = "settingId";

    private final String iD = "id";

    private final List<OutputItem> outputItems = Arrays.asList(
            new OutputItem(
                    1,
                    new FormOutputItemName("itemName01"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalculationClassification.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                            new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
                    )
            ),
            new OutputItem(
                    2,
                    new FormOutputItemName("itemName02"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalculationClassification.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailSelectionAttendanceItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailSelectionAttendanceItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ));
    private final WorkStatusOutputSettings outputSettings = new WorkStatusOutputSettings(
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
    @Test
    public void test_01() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
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
            UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                    outputItems);
        });
    }

    @Test
    public void test_02() {
        val settingCategory = SettingClassificationCommon.FREE_SETTING;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = cid;
                AppContexts.user().employeeId();
                result = empId;
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(cid, settingId);
                result = outputSettings;
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(any.get(), any.get(), any.get(), any.get(), any.get()));
    }
    @Test
    public void test_03() {
        val settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = cid;
                AppContexts.user().employeeId();
                result = empId;
            }
        };
        new Expectations() {
            {
                require.getWorkStatusOutputSettings(cid, settingId);
                result = outputSettings;
            }
        };

        NtsAssert.atomTask(() ->
                        UpdateWorkStatusSettingDomainService.updateSetting(require, settingId, code, name, settingCategory,
                                outputItems),

                any -> require.update(any.get(), any.get(), any.get(), any.get(), any.get()));
    }
}
