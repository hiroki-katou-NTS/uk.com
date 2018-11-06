package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo.amountinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.EmployeeResidentTaxPayAmountInfo;
import nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo.MonthlyResidentTaxPayAmount;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 社員住民税納付額情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_RSDT_TAX_PAY_AM")
public class QpbmtEmpRsdtTaxPayAm extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpRsdtTaxPayAmPk empRsdtTaxPayAmPk;

    /**
     * 入力区分
     */
    @Basic(optional = false)
    @Column(name = "INPUT_ATR")
    public int inputAtr;

    /**
     * 月次住民税納付額.1月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_JANUARY")
    public BigDecimal amountJanuary;

    /**
     * 月次住民税納付額.2月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_FEBRUARY")
    public BigDecimal amountFebruary;

    /**
     * 月次住民税納付額.3月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_MARCH")
    public BigDecimal amountMarch;

    /**
     * 月次住民税納付額.4月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_APRIL")
    public BigDecimal amountApril;

    /**
     * 月次住民税納付額.5月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_MAY")
    public BigDecimal amountMay;

    /**
     * 月次住民税納付額.6月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_JUNE")
    public BigDecimal amountJune;

    /**
     * 月次住民税納付額.7月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_JULY")
    public BigDecimal amountJuly;

    /**
     * 月次住民税納付額.8月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_AUGUST")
    public BigDecimal amountAugust;

    /**
     * 月次住民税納付額.9月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_SEPTEMBER")
    public BigDecimal amountSeptember;

    /**
     * 月次住民税納付額.10月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_OCTOBER")
    public BigDecimal amountOctober;

    /**
     * 月次住民税納付額.11月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_NOVEMBER")
    public BigDecimal amountNovember;

    /**
     * 月次住民税納付額.12月納付額
     */
    @Basic(optional = false)
    @Column(name = "AMOUNT_DECEMBER")
    public BigDecimal amountDecember;

    @Override
    protected Object getKey() {
        return empRsdtTaxPayAmPk;
    }

    public EmployeeResidentTaxPayAmountInfo toDomain() {
        return new EmployeeResidentTaxPayAmountInfo(this.empRsdtTaxPayAmPk.sid, this.empRsdtTaxPayAmPk.year,
                this.inputAtr, this.amountJanuary, this.amountFebruary, this.amountMarch, this.amountApril,
                this.amountMay, this.amountJune, this.amountJuly, this.amountAugust, this.amountSeptember,
                this.amountOctober, this.amountNovember, this.amountDecember);
    }

    public static QpbmtEmpRsdtTaxPayAm toEntity(EmployeeResidentTaxPayAmountInfo domain) {
        MonthlyResidentTaxPayAmount amount = domain.getMonthlyPaymentAmount();
        return new QpbmtEmpRsdtTaxPayAm(new QpbmtEmpRsdtTaxPayAmPk(domain.getSid(), domain.getYear().v()),
                domain.getInputAtr().value, amount.getAmountJanuary().v(), amount.getAmountFebruary().v(),
                amount.getAmountMarch().v(), amount.getAmountApril().v(), amount.getAmountMay().v(),
                amount.getAmountJune().v(), amount.getAmountJuly().v(), amount.getAmountAugust().v(),
                amount.getAmountSeptember().v(), amount.getAmountOctober().v(), amount.getAmountNovember().v(),
                amount.getAmountDecember().v());
    }

    public static List<QpbmtEmpRsdtTaxPayAm> toEntitys(List<EmployeeResidentTaxPayAmountInfo> domains) {
        List<QpbmtEmpRsdtTaxPayAm> entitys = new ArrayList<>();
        for (EmployeeResidentTaxPayAmountInfo domain : domains) {
            entitys.add(QpbmtEmpRsdtTaxPayAm.toEntity(domain));
        }
        return entitys;
    }
}
