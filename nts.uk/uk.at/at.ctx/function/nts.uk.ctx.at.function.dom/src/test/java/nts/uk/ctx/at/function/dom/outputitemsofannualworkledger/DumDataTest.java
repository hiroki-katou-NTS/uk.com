package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;

import java.util.Arrays;
import java.util.List;

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
                                new OutputItemNameOfAnnualWorkLedgerDaily("name"),
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
                        new OutputItemWorkLedger(
                                1,
                                new OutputItemNameOfAnnualWorkLedger("name"),
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
                                new OutputItemNameOfAnnualWorkLedgerDaily("name"),
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
                        new OutputItemWorkLedger(
                                1,
                                new OutputItemNameOfAnnualWorkLedger("name"),
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
    public static final List<OutputItemWorkLedger> outputItems = Arrays.asList(
            new OutputItemWorkLedger(
                    0,
                    new OutputItemNameOfAnnualWorkLedger("itemName01"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 22),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 2)
                    )
            ),
            new OutputItemWorkLedger(
                    1,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    1),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    1)
                    )
            ),
            new OutputItemWorkLedger(
                    2,
                    new OutputItemNameOfAnnualWorkLedger("itemName03"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    3,
                    new OutputItemNameOfAnnualWorkLedger("itemName04"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    4,
                    new OutputItemNameOfAnnualWorkLedger("itemName05"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    5,
                    new OutputItemNameOfAnnualWorkLedger("itemName06"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    6,
                    new OutputItemNameOfAnnualWorkLedger("itemName07"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
                    )
            ),
            new OutputItemWorkLedger(
                    7,
                    new OutputItemNameOfAnnualWorkLedger("itemName08"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    8,
                    new OutputItemNameOfAnnualWorkLedger("itemName09"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    2)
                    )
            ),
            new OutputItemWorkLedger(
                    9,
                    new OutputItemNameOfAnnualWorkLedger("itemName10"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    2),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    5)
                    )
            )

    );
}
