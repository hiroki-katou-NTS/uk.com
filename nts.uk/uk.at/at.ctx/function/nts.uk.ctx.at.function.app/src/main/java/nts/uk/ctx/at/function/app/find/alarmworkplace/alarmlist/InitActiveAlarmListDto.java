package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InitActiveAlarmListDto {
    public  InitActiveAlarmListDto(String employmentCode, List<AlarmPatternSettingWorkPlace> domains){
        this.employmentCode = employmentCode;
        this.alarmPatterns = domains.stream().map(AlarmPatternSettingWorkPlaceDto::new).collect(Collectors.toList());
    }

    /**
     * 雇用コード
     */
    private String employmentCode;

    private List<AlarmPatternSettingWorkPlaceDto> alarmPatterns;
}
