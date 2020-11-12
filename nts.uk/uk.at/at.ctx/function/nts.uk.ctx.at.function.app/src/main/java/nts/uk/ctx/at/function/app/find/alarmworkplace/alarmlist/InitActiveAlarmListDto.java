package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InitActiveAlarmListDto {
    public  InitActiveAlarmListDto(String employmentCode, List<AlarmPatternSettingWorkPlace> domains, Integer processingYm){
        this.employmentCode = employmentCode;
        this.alarmPatterns = domains.stream().map(AlarmPatternSettingWorkPlaceDto::new).collect(Collectors.toList());
        this.processingYm = processingYm;
    }

    /**
     * 雇用コード
     */
    private String employmentCode;

    /**
     * アラームリストパターン設定(職場別)
     */
    private List<AlarmPatternSettingWorkPlaceDto> alarmPatterns;

    /**
     * 当月の年月

     */
    private Integer processingYm;
}
