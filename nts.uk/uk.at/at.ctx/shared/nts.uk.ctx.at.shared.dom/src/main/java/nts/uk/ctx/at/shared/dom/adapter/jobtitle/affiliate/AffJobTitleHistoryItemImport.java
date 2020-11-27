package nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所属職位履歴項目 Import
 */
@Getter
@AllArgsConstructor
public class AffJobTitleHistoryItemImport {
    // 履歴ID
    private String historyId;

    /** The employee id. */
    // 社員ID
    private String employeeId;

    /** The job title id. */
    // 職位ID
    private String jobTitleId;

    /** The note. */
    // 備考
    private String note;
}
