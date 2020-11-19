package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class AnnualWorkLedgerOutputSettingTest {
    @Injectable
    AnnualWorkLedgerOutputSetting.Require require;
    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");
    private final String cid = "companyId";
    private final String eplId = "employeeId";

    @Test
    public void test_01() {

        new Expectations() {
            {
                require.checkTheStandard(code, cid);
                result = true;
            }
        };
        val actual = AnnualWorkLedgerOutputSetting.checkDuplicateStandardSelection(require, code, cid);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_02() {
        new Expectations() {
            {
                require.checkFreedom(code, cid, eplId);
                result = true;
            }
        };
        val actual = AnnualWorkLedgerOutputSetting.checkDuplicateFreeSettings(require, code, cid, eplId);
        assertThat(actual).isEqualTo(true);
    }

    @Test
    public void test_03() {

        val outputSettings = createDomain();
        NtsAssert.invokeGetters(outputSettings);
    }

    private AnnualWorkLedgerOutputSetting createDomain() {
        String iD = "id";
        return new AnnualWorkLedgerOutputSetting(
                iD,
                code,
                name,
                SettingClassificationCommon.FREE_SETTING,
                Arrays.asList(
                        new DailyOutputItemsAnnualWorkLedger(
                                1,
                                new OutputItemNameOfAnnualWorkLedger("name"),
                                true,
                                IndependentCalcClassic.ALONE,
                                DailyMonthlyClassification.DAILY,
                                CommonAttributesOfForms.NUMBER_OF_TIMES,
                                Arrays.asList(new OutputItemDetailAttItem(
                                        OperatorsCommonToForms.ADDITION,
                                        1
                                ))

                        )

                )
                , eplId,
                Arrays.asList(
                        new OutputItem(
                                1,
                                new FormOutputItemName("name"),
                                true,
                                IndependentCalcClassic.ALONE,
                                DailyMonthlyClassification.MONTHLY,
                                CommonAttributesOfForms.NUMBER_OF_TIMES,
                                Arrays.asList(new OutputItemDetailAttItem(
                                        OperatorsCommonToForms.ADDITION,
                                        1
                                ))

                        )

                )
        );
    }
}
