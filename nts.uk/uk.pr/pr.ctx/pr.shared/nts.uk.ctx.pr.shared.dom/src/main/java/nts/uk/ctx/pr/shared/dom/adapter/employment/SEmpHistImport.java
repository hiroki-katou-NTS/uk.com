package nts.uk.ctx.pr.shared.dom.adapter.employment;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class SEmpHistExport.
 */
@Data
@Builder
public class SEmpHistImport {

    /** 社員ID */
    private String employeeId;

    /** 雇用コード */
    private String employmentCode;

    /** 雇用名称 */
    private String employmentName;

    /** 配属期間 */
    private DatePeriod period;

}
