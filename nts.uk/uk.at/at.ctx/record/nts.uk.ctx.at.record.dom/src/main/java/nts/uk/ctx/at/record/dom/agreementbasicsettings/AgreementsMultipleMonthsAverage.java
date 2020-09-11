package nts.uk.ctx.at.record.dom.agreementbasicsettings;

import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;

/**
 * ３６協定複数月平均
 */
public class AgreementsMultipleMonthsAverage {
    //複数月平均
    private ErrorTimeInMonth multiMonthAverage;

    /**
     * 	[C-0] ３６協定複数月平均 (複数月平均)
     * @param multiMonthAverage
     */
    public AgreementsMultipleMonthsAverage(ErrorTimeInMonth multiMonthAverage){
        this.multiMonthAverage = multiMonthAverage;
    }
}
