package nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset.AverageWageCalculationSet;

/**
 * 平均賃金計算設定: DTO
 */

@Value
public class AverageWageCalculationSetDto {

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


    public static AverageWageCalculationSetDto fromDomain(AverageWageCalculationSet domain) {
        return new AverageWageCalculationSetDto(
                domain.getExceptionFormula().v(),
                domain.getDaysAttendance().getObtainAttendanceDays().value,
                domain.getDaysAttendance().getDaysFractionProcessing().map(i -> i.value).orElse(null),
                domain.getDecimalPointCutoffSegment().value);
    }

}
