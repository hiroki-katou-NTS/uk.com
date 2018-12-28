package nts.uk.ctx.pr.core.dom.adapter.employee.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassificationHistoryExport {
    private DatePeriod period;
    private String employeeId;
    /** The classification code. */
    private String classificationCode; // 分類コード
    private String classificationName;

}
