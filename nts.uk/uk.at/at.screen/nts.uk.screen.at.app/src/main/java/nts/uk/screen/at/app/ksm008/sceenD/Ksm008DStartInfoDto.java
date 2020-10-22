package nts.uk.screen.at.app.ksm008.sceenD;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;

import java.util.List;

@Data
@AllArgsConstructor
public class Ksm008DStartInfoDto {

    //TODO covert feilds use to dto
    private AlarmCheckConditionSchedule alarmCheckConditionSchedule;

    private List<WorkingHoursDto> workTimeSettings;

}

