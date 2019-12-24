package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;

/**
* 出勤日数取得方法の設定
*/
@AllArgsConstructor
@Getter
public class SettingOfAttendanceDays extends DomainObject
{
    
    /**
    * 出勤日数の取得方法
    */
    private SelectWorkDays obtainAttendanceDays;
    
    /**
    * 日数端数処理方法
    */
    private Optional<DaysFractionProcessing> daysFractionProcessing;
    
    public SettingOfAttendanceDays(int obtainAttendanceDays, Integer daysFractionProcessing) {
        this.obtainAttendanceDays = EnumAdaptor.valueOf(obtainAttendanceDays, SelectWorkDays.class);
        this.daysFractionProcessing = daysFractionProcessing == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(daysFractionProcessing, DaysFractionProcessing.class));
    }
    
}
