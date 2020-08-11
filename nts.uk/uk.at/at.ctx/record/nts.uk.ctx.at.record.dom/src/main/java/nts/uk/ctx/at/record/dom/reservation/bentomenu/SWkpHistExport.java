package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@Builder
public class SWkpHistExport {

    /** The date range. */
    // 配属期間
    private DatePeriod dateRange;

    /** The employee id. */
    // 社員ID
    private String employeeId;

    /** The workplace id. */
    // 職場ID
    private String workplaceId;

    /** The workplace code. */
    private String workplaceCode;

    /** The workplace name. */
    private String workplaceName;

    /** The wkp display name. */
    // 職場表示名
    private String wkpDisplayName;

    public SWkpHistExport(DatePeriod dateRange, String employeeId, String workplaceId, String workplaceCode, String workplaceName, String wkpDisplayName) {
        this.dateRange = dateRange;
        this.employeeId = employeeId;
        this.workplaceId = workplaceId;
        this.workplaceCode = workplaceCode;
        this.workplaceName = workplaceName;
        this.wkpDisplayName = wkpDisplayName;
    }
}
