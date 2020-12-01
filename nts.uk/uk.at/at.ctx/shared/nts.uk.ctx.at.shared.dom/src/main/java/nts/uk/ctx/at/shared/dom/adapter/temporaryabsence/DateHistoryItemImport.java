package nts.uk.ctx.at.shared.dom.adapter.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class DateHistoryItemImport {

    public String historyId;
    public GeneralDate startDate;
    public GeneralDate endDate;
    TempAbsenceHisItemImport historyItem;
}
