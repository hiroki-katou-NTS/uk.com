package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeItemSet;

@AllArgsConstructor
@Value
public class AttendanceItemSetDto {

    private int timeCountAtr;

    private DetailTimeErrorAlarmRangeSetDto errorRangeSetting;

    private DetailTimeErrorAlarmRangeSetDto alarmRangeSetting;

    public AttendanceItemSetDto(TimeItemSet domain) {
        this.timeCountAtr = domain.getTimeCountAtr().value;
        this.errorRangeSetting = new DetailTimeErrorAlarmRangeSetDto(domain.getErrorRangeSetting());
        this.alarmRangeSetting = new DetailTimeErrorAlarmRangeSetDto(domain.getAlarmRangeSetting());
    }
}
