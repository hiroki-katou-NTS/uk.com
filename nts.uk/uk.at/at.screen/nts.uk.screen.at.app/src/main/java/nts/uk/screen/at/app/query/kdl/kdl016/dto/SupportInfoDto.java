package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import org.apache.commons.lang3.StringUtils;

/**
 * 応援情報DTO
 */
@Data
public class SupportInfoDto {
    private int id;
    private String employeeId;
    private String periodStart;
    private String periodEnd;
    private String employeeCode;
    private String employeeName;
    private String supportOrgName;
    private String supportOrgId;
    private int supportOrgUnit;
    private int supportType;
    private TimeSpanForCalcDto timeSpan;

    private String supportTypeName;
    private String periodDisplay;
    private String employeeDisplay;
    private String timeSpanDisplay;
    private int displayMode;

    public SupportInfoDto(int id, String employeeId, String periodStart, String periodEnd, String employeeCode, String employeeName,
                          String supportOrgName, String supportOrgId, int supportOrgUnit, int supportType, TimeSpanForCalcDto timeSpan,
                          int displayMode) {
        this.id = id;
        this.employeeId = employeeId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.supportOrgName = supportOrgName;
        this.supportOrgId = supportOrgId;
        this.supportOrgUnit = supportOrgUnit;
        this.supportType = supportType;
        this.timeSpan = timeSpan;
        this.supportTypeName = supportType == SupportType.ALLDAY.getValue() ? SupportType.ALLDAY.name() : SupportType.TIMEZONE.name();
        this.periodDisplay = StringUtils.isNotEmpty(periodEnd) ? periodStart + "～" + periodEnd : periodStart;
        this.employeeDisplay = employeeCode + "　" + employeeName;
        this.timeSpanDisplay = timeSpan.getStart() + "～" + timeSpan.getEnd();
        this.displayMode = displayMode;
    }
}
