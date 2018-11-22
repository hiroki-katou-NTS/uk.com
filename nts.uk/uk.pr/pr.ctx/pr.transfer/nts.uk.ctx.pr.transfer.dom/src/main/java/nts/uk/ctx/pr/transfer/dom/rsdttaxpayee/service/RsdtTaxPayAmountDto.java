package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmountImport;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class RsdtTaxPayAmountDto {

    /**
     * 社員ID
     */
    @Setter
    private String sid;

    /**
     * 年度
     */
    @Setter
    private int year;

    /**
     * 住民税納付先.名称
     */
    @Setter
    private String rsdtTaxPayeeName;

    /**
     * 社員住民税納付額情報.入力区分
     */
    @Setter
    private int inputAtr;

    /**
     * 社員住民税納付額情報.月次納付額.1月納付額
     */
    private BigDecimal amountJanuary;

    /**
     * 社員住民税納付額情報.月次納付額.2月納付額
     */
    private BigDecimal amountFebruary;

    /**
     * 社員住民税納付額情報.月次納付額.3月納付額
     */
    private BigDecimal amountMarch;

    /**
     * 社員住民税納付額情報.月次納付額.4月納付額
     */
    private BigDecimal amountApril;

    /**
     * 社員住民税納付額情報.月次納付額.5月納付額
     */
    private BigDecimal amountMay;

    /**
     * 社員住民税納付額情報.月次納付額.6月納付額
     */
    private BigDecimal amountJune;

    /**
     * 社員住民税納付額情報.月次納付額.7月納付額
     */
    private BigDecimal amountJuly;

    /**
     * 社員住民税納付額情報.月次納付額.8月納付額
     */
    private BigDecimal amountAugust;

    /**
     * 社員住民税納付額情報.月次納付額.9月納付額
     */
    private BigDecimal amountSeptember;

    /**
     * 社員住民税納付額情報.月次納付額.10月納付額
     */
    private BigDecimal amountOctober;

    /**
     * 社員住民税納付額情報.月次納付額.11月納付額
     */
    private BigDecimal amountNovember;

    /**
     * 社員住民税納付額情報.月次納付額.12月納付額
     */
    private BigDecimal amountDecember;

    /**
     * setMonthlyPaymentAmount
     *
     * @param monthlyPaymentAmount 社員住民税納付額情報.月次納付額
     */
    public void setMonthlyPaymentAmount(MonthlyResidentTaxPayAmountImport monthlyPaymentAmount) {
        this.amountJanuary = monthlyPaymentAmount.getAmountJanuary();
        this.amountFebruary = monthlyPaymentAmount.getAmountFebruary();
        this.amountMarch = monthlyPaymentAmount.getAmountMarch();
        this.amountApril = monthlyPaymentAmount.getAmountApril();
        this.amountMay = monthlyPaymentAmount.getAmountMay();
        this.amountJune = monthlyPaymentAmount.getAmountJune();
        this.amountJuly = monthlyPaymentAmount.getAmountJuly();
        this.amountAugust = monthlyPaymentAmount.getAmountAugust();
        this.amountSeptember = monthlyPaymentAmount.getAmountSeptember();
        this.amountOctober = monthlyPaymentAmount.getAmountOctober();
        this.amountNovember = monthlyPaymentAmount.getAmountNovember();
        this.amountDecember = monthlyPaymentAmount.getAmountDecember();
    }
}
