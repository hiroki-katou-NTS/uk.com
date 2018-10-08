package entity.wageprovision.companyuniformamount;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与会社単価設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PAY_UNIT_PRI_SET")
public class QpbmtPayUnitPriSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPayUnitPriSetPk payUnitPriSetPk;
    
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
    @Column(name = "TARGET_CLASS")
    public Integer targetClass;
    
    /**
    * 日給月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTH_SALARY_PER_DAY")
    public Integer monthSalaryPerDay;
    
    /**
    * 日給者
    */
    @Basic(optional = true)
    @Column(name = "A_DAY_PAYEE")
    public Integer aDayPayee;
    
    /**
    * 時給者
    */
    @Basic(optional = true)
    @Column(name = "HOURLY_PAY")
    public Integer hourlyPay;
    
    /**
    * 月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTH_SALARY")
    public Integer monthSalary;
    
    /**
    * 設定区分
    */
    @Basic(optional = false)
    @Column(name = "SET_CLASSIFICATION")
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
        return payUnitPriSetPk;
    }

    public PayrollUnitPriceSetting toDomain() {
        return new PayrollUnitPriceSetting(this.payUnitPriSetPk.hisId, this.amountOfMoney, this.targetClass, this.monthSalaryPerDay, this.aDayPayee, this.hourlyPay, this.monthSalary, this.setClassification, this.notes);
    }
    public static QpbmtPayUnitPriSet toEntity(PayrollUnitPriceSetting domain) {
        return new QpbmtPayUnitPriSet(new QpbmtPayUnitPriSetPk(domain.getHistoryId()),
                domain.getAmountOfMoney().v(),
                domain.getFixedWage().getFlatAllEmployees().get().getTargetClass().value,
                domain.getFixedWage().getPerSalaryConType().get().getMonthSalaryPerDay().value,
                domain.getFixedWage().getPerSalaryConType().get().getADayPayee().value,
                domain.getFixedWage().getPerSalaryConType().get().getHourlyPay().value,
                domain.getFixedWage().getPerSalaryConType().get().getMonthlySalary().value,
                domain.getFixedWage().getSetClassification().value,
                domain.getNotes().map(i->i.v()).orElse(null));
    }

}
