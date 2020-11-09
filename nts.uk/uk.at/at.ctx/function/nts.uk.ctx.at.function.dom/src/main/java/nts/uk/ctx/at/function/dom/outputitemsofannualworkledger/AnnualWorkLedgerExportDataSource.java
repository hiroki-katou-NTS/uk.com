package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;

@AllArgsConstructor
@Getter
public class AnnualWorkLedgerExportDataSource {
    private int mode;
    private String companyName;
    private AnnualWorkLedgerOutputSetting outputSetting;
    private YearMonthPeriod yearMonthPeriod;
    private boolean isZeroDisplay;
    private List<AnnualWorkLedgerContent> lstAnnualWorkLedgerContent;
}
