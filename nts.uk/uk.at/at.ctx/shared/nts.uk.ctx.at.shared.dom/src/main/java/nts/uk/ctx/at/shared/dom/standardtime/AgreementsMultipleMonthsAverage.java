package nts.uk.ctx.at.shared.dom.standardtime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;

/**
 * ３６協定複数月平均
 */
@Getter
public class AgreementsMultipleMonthsAverage {
    //複数月平均
    private ErrorTimeInMonth errorTimeInMonth;

    /**
     * 	[C-0] ３６協定複数月平均 (複数月平均)
     * @param errorTimeInMonth
     */
    public AgreementsMultipleMonthsAverage(ErrorTimeInMonth errorTimeInMonth){
        this.errorTimeInMonth = errorTimeInMonth;
    }
}
