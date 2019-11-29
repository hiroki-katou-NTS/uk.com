package nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与会社単価履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAY_UNIT_PRICE_HIS")
public class QpbmtPayUnitPriceHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPayUnitPriceHisPk payUnitPriceHisPk;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYearMonth;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYearMonth;
    
    /**
    * 金額
    */
    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_MONEY")
    public BigDecimal amountOfMoney;
    
    /**
    * 対象区分
    */
    @Basic(optional = true)
    @Column(name = "TARGET_CLASS_ATR")
    public Integer targetClass;
    
    /**
    * 日給月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTH_SALARY_PER_DAY_ATR")
    public Integer monthSalaryPerDay;
    
    /**
    * 日給者
    */
    @Basic(optional = true)
    @Column(name = "A_DAY_PAYEE_ATR")
    public Integer aDayPayee;
    
    /**
    * 時給者
    */
    @Basic(optional = true)
    @Column(name = "HOURLY_PAY_ATR")
    public Integer hourlyPay;
    
    /**
    * 月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTH_SALARY_ATR")
    public Integer monthSalary;
    
    /**
    * 設定区分
    */
    @Basic(optional = false)
    @Column(name = "SET_CLASSIFICATION_ATR")
    public int setClassification;
    
    /**
    * メモ
    */
    @Basic(optional = true)
    @Column(name = "NOTES")
    public String notes;
    
    @Override
    protected Object getKey()
    {
        return payUnitPriceHisPk;
    }

    public Object[] toDomain(List<YearMonthHistoryItem> item) {
        PayrollUnitPriceHistory payrollUnitPriceHistory = new PayrollUnitPriceHistory(this.payUnitPriceHisPk.code, this.payUnitPriceHisPk.cid, item);
        PayrollUnitPriceSetting payrollUnitPriceSetting = new PayrollUnitPriceSetting(this.payUnitPriceHisPk.hisId, this.amountOfMoney, this.targetClass, this.monthSalaryPerDay, this.aDayPayee, this.hourlyPay, this.monthSalary, this.setClassification, this.notes);
        return new Object[]{ payrollUnitPriceHistory, payrollUnitPriceSetting };
    }

    public static QpbmtPayUnitPriceHis toEntity(String code, String cId, YearMonthHistoryItem item, PayrollUnitPriceSetting payrollUnitPriceSet) {
        return new QpbmtPayUnitPriceHis(
                new QpbmtPayUnitPriceHisPk(cId, code, item.identifier()),
                item.start().v(),
                item.end().v(),
                payrollUnitPriceSet.getAmountOfMoney().v(),
                payrollUnitPriceSet.getFixedWage().getFlatAllEmployees().isPresent() && payrollUnitPriceSet.getFixedWage().getFlatAllEmployees().get().getTargetClass() != null ? payrollUnitPriceSet.getFixedWage().getFlatAllEmployees().get().getTargetClass().value : 0,
                payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() && payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthSalaryPerDay() != null ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthSalaryPerDay().value : 0,
                payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() && payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getADayPayee() != null ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getADayPayee().value : 0,
                payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() && payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getHourlyPay() != null ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getHourlyPay().value : 0,
                payrollUnitPriceSet.getFixedWage().getPerSalaryConType().isPresent() && payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthlySalary() != null ? payrollUnitPriceSet.getFixedWage().getPerSalaryConType().get().getMonthlySalary().value : 0,
                payrollUnitPriceSet.getFixedWage().getSetClassification().value,
                payrollUnitPriceSet.getNotes().isPresent() ? payrollUnitPriceSet.getNotes().get().v() : null
                );
    }

}
