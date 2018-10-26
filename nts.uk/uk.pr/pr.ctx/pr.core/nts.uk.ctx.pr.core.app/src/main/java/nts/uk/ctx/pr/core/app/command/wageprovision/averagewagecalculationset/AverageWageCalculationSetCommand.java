package nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class AverageWageCalculationSetCommand
{
    
    /**
    * 例外計算式割合
    */
    private int exceptionFormula;
    
    /**
    * 出勤日数の取得方法
    */
    private int obtainAttendanceDays;
    
    /**
    * 日数端数処理方法
    */
    private Integer daysFractionProcessing;
    
    /**
    * 小数点切捨区分
    */
    private int decimalPointCutoffSegment;
    

}
