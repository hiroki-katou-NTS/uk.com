package nts.uk.ctx.pr.core.pub.emprsdttaxinfo.amountinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
/**
 * 月次住民税納付額
 */
public class MonthlyResidentTaxPayAmountExport {

    /**
     * 1月納付額
     */
    private BigDecimal amountJanuary;

    /**
     * 2月納付額
     */
    private BigDecimal amountFebruary;

    /**
     * 3月納付額
     */
    private BigDecimal amountMarch;

    /**
     * 4月納付額
     */
    private BigDecimal amountApril;

    /**
     * 5月納付額
     */
    private BigDecimal amountMay;

    /**
     * 6月納付額
     */
    private BigDecimal amountJune;

    /**
     * 7月納付額
     */
    private BigDecimal amountJuly;

    /**
     * 8月納付額
     */
    private BigDecimal amountAugust;

    /**
     * 9月納付額
     */
    private BigDecimal amountSeptember;

    /**
     * 10月納付額
     */
    private BigDecimal amountOctober;

    /**
     * 11月納付額
     */
    private BigDecimal amountNovember;

    /**
     * 12月納付額
     */
    private BigDecimal amountDecember;
}
