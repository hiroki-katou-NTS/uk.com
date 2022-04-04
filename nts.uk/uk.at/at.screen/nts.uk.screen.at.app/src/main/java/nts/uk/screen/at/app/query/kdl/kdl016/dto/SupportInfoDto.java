package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.shr.com.i18n.TextResource;
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
    private String supportOrgCode;
    private int supportOrgUnit;
    private int supportType;
    private TimeSpanForCalcDto timeSpan;

    private String supportTypeName;
    private String periodDisplay;
    private String employeeDisplay;
    private String timeSpanDisplay;
    private int displayMode;

    public SupportInfoDto(int id, String employeeId, String periodStart, String periodEnd, String employeeCode, String employeeName,
                          String supportOrgName, String supportOrgId, String supportOrgCode, int supportOrgUnit, int supportType, TimeSpanForCalcDto timeSpan) {
        this.id = id;
        this.employeeId = employeeId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.supportOrgName = supportOrgName;
        this.supportOrgId = supportOrgId;
        this.supportOrgCode = supportOrgCode;
        this.supportOrgUnit = supportOrgUnit;
        this.supportType = supportType;
        this.timeSpan = timeSpan;
        this.supportTypeName = supportType == SupportType.ALLDAY.getValue() ? TextResource.localize("KDL016_37") : TextResource.localize("KDL016_38");
        this.periodDisplay = supportType == SupportType.TIMEZONE.getValue() ? periodStart : StringUtils.isNotEmpty(periodEnd) ? periodStart + "～" + periodEnd : periodStart;
        this.employeeDisplay = employeeCode + "　" + employeeName;
        this.timeSpanDisplay = timeSpan.getStart() == null && timeSpan.getEnd() == null ? null : this.convertNumberToTime(timeSpan.getStart()) + "～" + this.convertNumberToTime(timeSpan.getEnd());
    }

    private String convertNumberToTime(Integer totalMinute) {
        if (totalMinute == null) return "";
        int hour = totalMinute / 60;
        int minute = totalMinute % 60;

        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }
}
