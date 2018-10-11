package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;

/**
 * 社員住民税納付先情報
 */
@Setter
@Getter
@NoArgsConstructor
public class EmployeeResidentTaxPayeeInfoImport {

    /**
     * 社員ID
     */
    @Getter
    private String sid;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> historyItems;
}
