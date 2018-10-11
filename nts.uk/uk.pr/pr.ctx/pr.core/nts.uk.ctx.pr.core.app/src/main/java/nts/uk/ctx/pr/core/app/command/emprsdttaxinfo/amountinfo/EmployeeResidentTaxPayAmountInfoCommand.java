package nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfo;

import java.math.BigDecimal;

@Data
public class EmployeeResidentTaxPayAmountInfoCommand {
    /**
     * 社員ID
     */
    private String sid;

    /**
     * 年度
     */
    private int year;

    /**
     * 住民税納付先.名称
     */
    private String rsdtTaxPayeeName;

    /**
     * 社員住民税納付額情報.入力区分
     */
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

    public EmployeeResidentTaxPayAmountInfo toDomain() {
        return new EmployeeResidentTaxPayAmountInfo(this.sid, this.year, this.inputAtr,
                this.amountJanuary, this.amountFebruary, this.amountMarch, this.amountApril, this.amountMay,
                this.amountJune, this.amountJuly, this.amountAugust, this.amountSeptember, this.amountOctober,
                this.amountNovember, this.amountDecember);
    }
}
