package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;

@Getter
public class AlarmPatternSettingWorkPlaceDto {
    public AlarmPatternSettingWorkPlaceDto(AlarmPatternSettingWorkPlace domain) {
        this.alarmPatternCode = domain.getAlarmPatternCD().v();
        this.alarmPatternName = domain.getAlarmPatternName().v();
    }

    /**
     * アラームリストパターンコード
     */
    private String alarmPatternCode;

    /**
     * 名称
     */
    private String alarmPatternName;
 }
