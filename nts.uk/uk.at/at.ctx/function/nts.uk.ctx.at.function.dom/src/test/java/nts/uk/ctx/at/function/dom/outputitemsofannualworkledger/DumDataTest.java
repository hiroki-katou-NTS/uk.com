package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;

import java.util.Arrays;

public class DumDataTest {

    public static AnnualWorkLedgerOutputSetting dumFree() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String eplId = "employeeId";
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

    public static AnnualWorkLedgerOutputSetting dumStandard() {
        OutputItemSettingCode code = new OutputItemSettingCode("ABC");
        OutputItemSettingName name = new OutputItemSettingName("CBA");
        String eplId = "employeeId";
        String iD = "id";
        return new AnnualWorkLedgerOutputSetting(
                iD,
                code,
                name,
                SettingClassificationCommon.STANDARD_SELECTION,
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
