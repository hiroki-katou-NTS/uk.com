package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.Year;

import java.math.BigDecimal;

/**
 * 社員住民税納付額情報
 */
@Getter
@Setter
public class EmployeeResidentTaxPayAmountInfo extends AggregateRoot {

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 年度
     */
    private Year year;

    /**
     * 入力区分
     */
    private ResidentTaxInputAtr inputAtr;

    /**
     * 月次納付額
     */
    private MonthlyResidentTaxPayAmount monthlyPaymentAmount;

    public EmployeeResidentTaxPayAmountInfo(String sid, int year, int inputAtr, BigDecimal amountJanuary, BigDecimal amountFebruary,
                                            BigDecimal amountMarch, BigDecimal amountApril, BigDecimal amountMay, BigDecimal amountJune,
                                            BigDecimal amountJuly, BigDecimal amountAugust, BigDecimal amountSeptember, BigDecimal amountOctober,
                                            BigDecimal amountNovember, BigDecimal amountDecember) {
        this.sid = sid;
        this.year = new Year(year);
        this.inputAtr = EnumAdaptor.valueOf(inputAtr, ResidentTaxInputAtr.class);
        this.monthlyPaymentAmount = new MonthlyResidentTaxPayAmount(amountJanuary, amountFebruary, amountMarch,
                amountApril, amountMay, amountJune, amountJuly, amountAugust, amountSeptember, amountOctober,
                amountNovember, amountDecember);
    }

}
