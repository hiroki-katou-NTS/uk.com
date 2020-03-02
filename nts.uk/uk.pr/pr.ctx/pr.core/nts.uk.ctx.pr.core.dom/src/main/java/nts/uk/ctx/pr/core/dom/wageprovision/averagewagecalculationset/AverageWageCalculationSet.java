package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

/**
* 平均賃金計算設定
*/
@Getter
public class AverageWageCalculationSet extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cId;
    
    /**
    * 例外計算式割合
    */
    private ExceptionFormula exceptionFormula;
    
    /**
    * 小数点切捨区分
    */
    private DecimalPointCutoffSegment decimalPointCutoffSegment;
    
    /**
    * 出勤日数
    */
    private SettingOfAttendanceDays daysAttendance;
    
    public AverageWageCalculationSet(String cid, int exceptionFormula, int obtainAttendanceDays, Integer daysFractionProcessing, int decimalPointCutoffSegment) {
        this.cId = cid;
        this.exceptionFormula = new ExceptionFormula(exceptionFormula);
        this.daysAttendance = new SettingOfAttendanceDays(obtainAttendanceDays, daysFractionProcessing);
        this.decimalPointCutoffSegment = EnumAdaptor.valueOf(decimalPointCutoffSegment, DecimalPointCutoffSegment.class);
    }
    
}
