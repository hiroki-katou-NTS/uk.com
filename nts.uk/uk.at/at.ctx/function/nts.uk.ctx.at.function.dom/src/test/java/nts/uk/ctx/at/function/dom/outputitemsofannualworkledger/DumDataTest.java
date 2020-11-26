package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetClosureDateEmploymentDomainServiceTest;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.FormOutputItemName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import java.util.*;

public class DumDataTest {
    public static Map<String, EmployeeBasicInfoImport> lstEmployee() {
        val rs = new HashMap<String, EmployeeBasicInfoImport>();

        rs.put("eplId01", new EmployeeBasicInfoImport("eplId01", "employeeCode1", "employeeName1"));
        rs.put("eplId02", new EmployeeBasicInfoImport("eplId02", "employeeCode2", "employeeName2"));
        return rs;

    }

    public static Map<String, WorkplaceInfor> lstWorkplaceInfor() {
        val rs = new HashMap<String, WorkplaceInfor>();

        rs.put("eplId01", new WorkplaceInfor("wplId1", "wplIdHCode1", "wplIdCode1", "wplIdName1", "wplIdDName1"
                , "wplIGName1", "wplIECode1"));
        rs.put("eplId02", new WorkplaceInfor("wplId2", "wplIdHCode2", "wplIdCode2", "wplIdName2", "wplIdDName2"
                , "wplIGName2", "wplIECode2"));
        return rs;
    }

    public static Map<String, WorkplaceInfor> lstWorkplaceInforFail() {
        val rs = new HashMap<String, WorkplaceInfor>();

        rs.put("eplId011", new WorkplaceInfor("wplId1", "wplIdHCode1", "wplIdCode1", "wplIdName1", "wplIdDName1"
                , "wplIGName1", "wplIECode1"));
        rs.put("eplId021", new WorkplaceInfor("wplId2", "wplIdHCode2", "wplIdCode2", "wplIdName2", "wplIdDName2"
                , "wplIGName2", "wplIECode2"));
        return rs;
    }

    public static Map<String, EmployeeBasicInfoImport> lstEmployeeFail() {
        val rs = new HashMap<String, EmployeeBasicInfoImport>();

        rs.put("eplId013", new EmployeeBasicInfoImport("eplId01", "employeeCode1", "employeeName1"));
        rs.put("eplId024", new EmployeeBasicInfoImport("eplId02", "employeeCode2", "employeeName2"));
        return rs;

    }

    public static Map<String, ClosureDateEmployment> lstClosureDateEmployment()

    {
        val closure = GetClosureDateEmploymentDomainServiceTest.createClosure();
        val rs = new HashMap<String, ClosureDateEmployment>();

        rs.put("eplId01", new ClosureDateEmployment("eplId01", "employeeCode1", "employeeName1", closure));
        rs.put("sid2", new ClosureDateEmployment("sid2", "employeeCode2", "employeeName2", closure));

        return rs;
    }

    public static List<DailyOutputItemsAnnualWorkLedger> dailyOutputItemList = Arrays.asList(
            new DailyOutputItemsAnnualWorkLedger(
                    0,
                    new OutputItemNameOfAnnualWorkLedger("itemName01 "),
                    true,
                    IndependentCalcClassic.ALONE,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.WORK_TYPE,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.ADDITION,
                            1
                    ))

            ), new DailyOutputItemsAnnualWorkLedger(
                    1,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.SUBTRACTION,
                            2
                    ))

            ),
            new DailyOutputItemsAnnualWorkLedger(
                    3,
                    new OutputItemNameOfAnnualWorkLedger("itemName01 "),
                    true,
                    IndependentCalcClassic.ALONE,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.WORK_TYPE,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.ADDITION,
                            3
                    ))

            ), new DailyOutputItemsAnnualWorkLedger(
                    4,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.SUBTRACTION,
                            4
                    ))

            )
            , new DailyOutputItemsAnnualWorkLedger(
                    5,
                    new OutputItemNameOfAnnualWorkLedger("itemName01 "),
                    true,
                    IndependentCalcClassic.ALONE,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.WORK_TYPE,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.ADDITION,
                            4
                    ))

            ), new DailyOutputItemsAnnualWorkLedger(
                    6,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.SUBTRACTION,
                            4
                    ))

            )
            , new DailyOutputItemsAnnualWorkLedger(
                    7,
                    new OutputItemNameOfAnnualWorkLedger("itemName01 "),
                    true,
                    IndependentCalcClassic.ALONE,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.WORK_TYPE,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.ADDITION,
                            4
                    ))

            ), new DailyOutputItemsAnnualWorkLedger(
                    8,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.SUBTRACTION,
                            3
                    ))

            )
            , new DailyOutputItemsAnnualWorkLedger(
                    8,
                    new OutputItemNameOfAnnualWorkLedger("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.DAILY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(new OutputItemDetailAttItem(
                            OperatorsCommonToForms.SUBTRACTION,
                            2
                    ))

            )
    );
    public static List<OutputItem> monthlyOutputItemList = Arrays.asList(
            new OutputItem(
                    0,
                    new FormOutputItemName("itemName01"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2)
                    )
            ),
            new OutputItem(
                    1,
                    new FormOutputItemName("itemName02"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 3)
                    )
            ),
            new OutputItem(
                    2,
                    new FormOutputItemName("itemName03"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 5)
                    )
            ),
            new OutputItem(
                    3,
                    new FormOutputItemName("itemName04"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 4)
                    )
            ),
            new OutputItem(
                    4,
                    new FormOutputItemName("itemName04"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 3)
                    )
            ),
            new OutputItem(
                    5,
                    new FormOutputItemName("itemName06"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2)
                    )
            ),
            new OutputItem(
                    6,
                    new FormOutputItemName("itemName07"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 5),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2)
                    )
            ),
            new OutputItem(
                    7,
                    new FormOutputItemName("itemName08"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 5),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2)
                    )
            ),
            new OutputItem(
                    8,
                    new FormOutputItemName("itemName09"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 3),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 1)
                    )
            ),
            new OutputItem(
                    9,
                    new FormOutputItemName("itemName10"),
                    true,
                    IndependentCalcClassic.CACULATION,
                    DailyMonthlyClassification.MONTHLY,
                    CommonAttributesOfForms.TIME,
                    Arrays.asList(
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 2),
                            new OutputItemDetailAttItem(OperatorsCommonToForms.SUBTRACTION, 3)
                    )
            )

    );
    public static AnnualWorkLedgerOutputSetting outputSetting = new AnnualWorkLedgerOutputSetting(
            "id",
            new OutputItemSettingCode("code"),
            new OutputItemSettingName("name"),
            SettingClassificationCommon.FREE_SETTING,
            dailyOutputItemList,
            "sid",
            monthlyOutputItemList

    );
    private static DailyData dailyData = new DailyData(
            Arrays.asList(new DailyValue(-2000D, "", GeneralDate.today().addDays(1))),
            Arrays.asList(new DailyValue(0D, "25", GeneralDate.today().addDays(1))),
            "itemName02",
            CommonAttributesOfForms.TIME,
            "itemName01",
            CommonAttributesOfForms.WORK_TYPE
    );

    private static List<MonthlyData> lstMonthlyData = Arrays.asList(
            new MonthlyData(
                    Collections.emptyList(),
                    "itemname01",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname02",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname03",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname04",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname05",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname06",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname07",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname08",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname09",
                    CommonAttributesOfForms.TIME
            ), new MonthlyData(
                    Collections.emptyList(),
                    "itemname10",
                    CommonAttributesOfForms.TIME
            ));


    public static List<AnnualWorkLedgerContent> expected = Arrays.asList(new AnnualWorkLedgerContent(
                    dailyData,
            lstMonthlyData,
                    "employeeCode1",
                    "employeeName1",
                    "AA",
                    "wplIdCode1",
                    "wplIdName1",
                    "employeeCode1",
                    "employeeName1"
            )

    );


}
