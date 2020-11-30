package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyOutputItemsAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.OutputItemNameOfAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DumData {
    public static final List<DailyValue> listEmpty = Collections.emptyList();
    public static final List<OutputItem> outputItems = Arrays.asList(
            new OutputItem(
                    0,
                    new FormOutputItemName("itemName01"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 22),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 2)
                    )
            ),
            new OutputItem(
                    1,
                    new FormOutputItemName("itemName02"),
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
            new OutputItem(
                    2,
                    new FormOutputItemName("itemName03"),
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
            new OutputItem(
                    3,
                    new FormOutputItemName("itemName04"),
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
            new OutputItem(
                    4,
                    new FormOutputItemName("itemName05"),
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
            new OutputItem(
                    5,
                    new FormOutputItemName("itemName06"),
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
            new OutputItem(
                    6,
                    new FormOutputItemName("itemName07"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
                    )
            ),
            new OutputItem(
                    7,
                    new FormOutputItemName("itemName08"),
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
            new OutputItem(
                    8,
                    new FormOutputItemName("itemName09"),
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
            new OutputItem(
                    9,
                    new FormOutputItemName("itemName10"),
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

    ); public static final List<OutputItem> outputItemsFail = Arrays.asList(
            new OutputItem(
                    0,
                    new FormOutputItemName("itemName01"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                    Collections.emptyList()
            ),
            new OutputItem(
                    1,
                    new FormOutputItemName("itemName02"),
                    false,
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
            new OutputItem(
                    2,
                    new FormOutputItemName("itemName03"),
                    false,
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
            new OutputItem(
                    3,
                    new FormOutputItemName("itemName04"),
                    false,
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
            new OutputItem(
                    4,
                    new FormOutputItemName("itemName05"),
                    false,
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
            new OutputItem(
                    5,
                    new FormOutputItemName("itemName06"),
                    false,
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
            new OutputItem(
                    6,
                    new FormOutputItemName("itemName07"),
                    false,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
                    )
            ),
            new OutputItem(
                    7,
                    new FormOutputItemName("itemName08"),
                    false,
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
            new OutputItem(
                    8,
                    new FormOutputItemName("itemName09"),
                    false,
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
            new OutputItem(
                    9,
                    new FormOutputItemName("itemName10"),
                    false,
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

    public static final List<OutputItem> outputItemsForAnual = Arrays.asList(
        new OutputItem(
            2,
            new FormOutputItemName("itemName03"),
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
        new OutputItem(
            3,
            new FormOutputItemName("itemName04"),
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
        new OutputItem(
            4,
            new FormOutputItemName("itemName05"),
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
        new OutputItem(
            5,
            new FormOutputItemName("itemName06"),
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
        new OutputItem(
            6,
            new FormOutputItemName("itemName07"),
            true,
            EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
            EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
            Arrays.asList(
                new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
            )
        ),
        new OutputItem(
            7,
            new FormOutputItemName("itemName08"),
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
        new OutputItem(
            8,
            new FormOutputItemName("itemName09"),
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
        new OutputItem(
            9,
            new FormOutputItemName("itemName10"),
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

    public static final List<DailyOutputItemsAnnualWorkLedger> dailyOutputItemList = Arrays.asList(
        new DailyOutputItemsAnnualWorkLedger(
            0,
            new OutputItemNameOfAnnualWorkLedger("itemName01"),
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
        new DailyOutputItemsAnnualWorkLedger(
            1,
            new OutputItemNameOfAnnualWorkLedger("itemName02"),
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
        )
    );

    public static WorkStatusOutputSettings dum(OutputItemSettingCode code, OutputItemSettingName name,
                                               String eplId, String settingId, SettingClassificationCommon settingCommon) {
        return new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                eplId,
                settingCommon,
                outputItems
        );
    }
    public static WorkStatusOutputSettings dumFail(OutputItemSettingCode code, OutputItemSettingName name,
                                               String eplId, String settingId, SettingClassificationCommon settingCommon) {
        return new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                eplId,
                settingCommon,
                outputItemsFail
        );
    }

    public static AnnualWorkLedgerOutputSetting dumAnual(OutputItemSettingCode code, OutputItemSettingName name,
                                                    String eplId, String settingId, SettingClassificationCommon settingCommon) {
        return new AnnualWorkLedgerOutputSetting(
            settingId,
            code,
            name,
            settingCommon,
            dailyOutputItemList,
            eplId,
            outputItems
        );
    }

    public static final List<WorkPlaceInfo> workPlaceInfo = Arrays.asList(
            new WorkPlaceInfo(
                    "wplId01"
                    , "wplCode01",
                    "wplName01")
    );
    public static final List<WorkPlaceInfo> workPlaceInfoFail = Arrays.asList(
            new WorkPlaceInfo(
                    "wplId09"
                    , "wplCode09",
                    "wplName09")
    );
    public static final List<EmployeeInfor> employeeInfors = Arrays.asList(new EmployeeInfor(
            "eplId01",
            "eplCode01",
            "eplName01",
            "wplId01"

    ));

    public static final List<EmployeeInfor> employeeInforFail = Arrays.asList(new EmployeeInfor(
            "eplId01",
            "eplCode05",
            "eplName05",
            "wplId05"

    ));

    public static final List<DisplayContentWorkStatus> expected = Arrays.asList(
            new DisplayContentWorkStatus(
                    "eplCode01",
                    "eplName01",
                    "wplCode01",
                    "wplName01",
                    Arrays.asList(
                            new OutputItemOneLine(
                                    0D,
                                    "itemName01",
                                    Arrays.asList(
                                            new DailyValue(
                                                    0.0,
                                                    CommonAttributesOfForms.WORK_TYPE,
                                                    "TEST 02TEST 01",
                                                    GeneralDate.today()
                                            )
                                    )
                            ),
                            new OutputItemOneLine(
                                    1975D,
                                    "itemName03",
                                    Arrays.asList(
                                            new DailyValue(
                                                    1975D,
                                                    CommonAttributesOfForms.NUMBER_OF_TIMES,
                                                    "",
                                                    GeneralDate.today()
                                            )
                                    )
                            ))
            )
    );



    public static WorkStatusOutputSettings dumDisplay(OutputItemSettingCode code, OutputItemSettingName name,
                                               String eplId, String settingId, SettingClassificationCommon settingCommon) {
        return new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                eplId,
                settingCommon,
                outputItemDisplay
        );
    }
    public static WorkStatusOutputSettings dumDomainNolistItem(OutputItemSettingCode code, OutputItemSettingName name,
                                                      String eplId, String settingId, SettingClassificationCommon settingCommon) {
        return new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                eplId,
                settingCommon,
                Collections.emptyList()
        );
    }
    public static final List<OutputItem> outputItemDisplay = Arrays.asList(
            new OutputItem(
                    0,
                    new FormOutputItemName("itemName01"),
                    true,
                    EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 22),
                            new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 2)
                    )
            ),
            new OutputItem(
                    2,
                    new FormOutputItemName("itemName03"),
                    true,
                    EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                    EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                    EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                    Arrays.asList(
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                    1 ),
                            new OutputItemDetailAttItem(
                                    EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                    8)
                    )
            )
    );


}
