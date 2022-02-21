package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;

/**
 * 応援情報DTO
 */
@Data
@AllArgsConstructor
public class SupportInfoDto {
    private String employeeId;
    private DatePeriod datePeriod;
    private String employeeCode;
    private String employeeName;
    private String supportOrgName;
    private String supportOrgId;
    private int supportOrgUnit;
    private int supportType;
    private TimeSpanForCalcDto timeSpan;
}
