package nts.uk.ctx.at.shared.dom.adapter.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 汎用履歴項目 Import
 */
@Getter
@AllArgsConstructor
public class DateHistoryItemImport {

    private String historyId;
    private GeneralDate startDate;
    private GeneralDate endDate;
}
