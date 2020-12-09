package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;

import java.util.Arrays;
import java.util.List;

public class DumDataLedger {
    public static List<WorkLedgerDisplayContent> expected = Arrays.asList(new WorkLedgerDisplayContent(
                    Arrays.asList(new MonthlyOutputLine(
                                    Arrays.asList(new MonthlyValue(
                                            1,
                                            20d,
                                            new YearMonth(202012),
                                            null
                                    )),
                                    "attendanceItemName",
                                    1,
                                    20D,
                                    CommonAttributesOfForms.TIME
                            )
                    ),
                    "eplCode01",
                    "eplName01",
                    "wplCode01",
                    "wplName01"
            )

    );
}
