package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.math.BigDecimal;

/**
 * 月次住民税納付額
 */
@Getter
public class MonthlyResidentTaxPayAmount extends DomainObject{

    /**
     * 1月納付額
     */
    private ResidentTax amountJanuary;

    /**
     * 2月納付額
     */
    private ResidentTax amountFebruary;

    /**
     * 3月納付額
     */
    private ResidentTax amountMarch;

    /**
     * 4月納付額
     */
    private ResidentTax amountApril;

    /**
     * 5月納付額
     */
    private ResidentTax amountMay;

    /**
     * 6月納付額
     */
    private ResidentTax amountJune;

    /**
     * 7月納付額
     */
    private ResidentTax amountJuly;

    /**
     * 8月納付額
     */
    private ResidentTax amountAugust;

    /**
     * 9月納付額
     */
    private ResidentTax amountSeptember;

    /**
     * 10月納付額
     */
    private ResidentTax amountOctober;

    /**
     * 11月納付額
     */
    private ResidentTax amountNovember;

    /**
     * 12月納付額
     */
    private ResidentTax amountDecember;

    public MonthlyResidentTaxPayAmount(BigDecimal amountJanuary, BigDecimal amountFebruary, BigDecimal amountMarch, BigDecimal amountApril,
                                       BigDecimal amountMay, BigDecimal amountJune, BigDecimal amountJuly, BigDecimal amountAugust,
                                       BigDecimal amountSeptember, BigDecimal amountOctober, BigDecimal amountNovember,
                                       BigDecimal amountDecember) {
        this.amountJanuary = new ResidentTax(amountJanuary);
        this.amountFebruary = new ResidentTax(amountFebruary);
        this.amountMarch = new ResidentTax(amountMarch);
        this.amountApril = new ResidentTax(amountApril);
        this.amountMay = new ResidentTax(amountMay);
        this.amountJune = new ResidentTax(amountJune);
        this.amountJuly = new ResidentTax(amountJuly);
        this.amountAugust = new ResidentTax(amountAugust);
        this.amountSeptember = new ResidentTax(amountSeptember);
        this.amountOctober = new ResidentTax(amountOctober);
        this.amountNovember = new ResidentTax(amountNovember);
        this.amountDecember = new ResidentTax(amountDecember);
    }
}
