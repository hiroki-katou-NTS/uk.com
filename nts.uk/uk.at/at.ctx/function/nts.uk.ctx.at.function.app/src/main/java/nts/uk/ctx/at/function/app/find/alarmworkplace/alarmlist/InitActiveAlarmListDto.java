package nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class InitActiveAlarmListDto {
    public  InitActiveAlarmListDto(String employmentCode, List<AlarmPatternSettingWorkPlace> domains,
                                   Integer processingYm, DatePeriod datePeriodClosure, String workplaceId){
        this.employmentCode = employmentCode;
        this.alarmPatterns = domains.stream().map(AlarmPatternSettingWorkPlaceDto::new).collect(Collectors.toList());
        this.processingYm = processingYm;
        if (datePeriodClosure != null){
            closureStartDate = datePeriodClosure.start();
            closureEndDate = datePeriodClosure.end();
        }
        this.workplaceId = workplaceId;
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

    /**
     * 締め期間．開始日の日
     */
    private GeneralDate closureStartDate;

    /**
     * 締め期間．終了日の日
     */
    private GeneralDate closureEndDate;

    private String workplaceId;
}
