package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class WorkLedgerExportDataSource {
    private String companyName;
    private String title;
    private YearMonthPeriod yearMonthPeriod;
    private ClosureDate closureDate;
    private boolean isZeroDisplay;
    private List<WorkLedgerDisplayContent> listContent;
}
