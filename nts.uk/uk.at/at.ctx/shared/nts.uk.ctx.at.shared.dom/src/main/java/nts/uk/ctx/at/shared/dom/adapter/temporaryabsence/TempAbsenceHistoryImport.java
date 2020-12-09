package nts.uk.ctx.at.shared.dom.adapter.temporaryabsence;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TempAbsenceHistoryImport {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    private List<DateHistoryItemImport> dateHistoryItems;

}
