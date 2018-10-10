package nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与個人単価設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PER_UNIT_PRICE_SET")
public class QpbmtPerUnitPriceSet extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtPerUnitPriceSetPk perUnitPriceSetPk;
    
    /**
    * 単価種類
    */
    @Basic(optional = false)
    @Column(name = "UNIT_PRICE_TYPE")
    public int unitPriceType;
    
    /**
    * 設定区分
    */
    @Basic(optional = false)
    @Column(name = "SETTING_ATR")
    public int settingAtr;
    
    /**
    * 対象区分
    */
    @Basic(optional = true)
    @Column(name = "TARGET_CLASSIFICATION")
    public Integer targetClassification;
    
    /**
    * 月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTHLY_SALARY")
    public Integer monthlySalary;
    
    /**
    * 日給月給者
    */
    @Basic(optional = true)
    @Column(name = "MONTHLY_SALARY_PERDAY")
    public Integer monthlySalaryPerday;
    
    /**
    * 日給者
    */
    @Basic(optional = true)
    @Column(name = "DAY_PAYEE")
    public Integer dayPayee;
    
    /**
    * 時給者
    */
    @Basic(optional = true)
    @Column(name = "HOURLY_PAY")
    public Integer hourlyPay;
    
    @Override
    protected Object getKey()
    {
        return perUnitPriceSetPk;
    }

    public SalaryPerUnitPriceSetting toDomain() {
        return new SalaryPerUnitPriceSetting(this.perUnitPriceSetPk.cid, this.perUnitPriceSetPk.code, this.unitPriceType, this.settingAtr, this.targetClassification, this.monthlySalary, this.monthlySalaryPerday, this.dayPayee, this.hourlyPay);
    }
    public static QpbmtPerUnitPriceSet toEntity(SalaryPerUnitPriceSetting domain) {
        return new QpbmtPerUnitPriceSet(domain);
    }

    public QpbmtPerUnitPriceSet (SalaryPerUnitPriceSetting domain)
    {
        this.perUnitPriceSetPk = new QpbmtPerUnitPriceSetPk(domain.getCid(), domain.getCode().v());
        this.unitPriceType = domain.getUnitPriceType().value;
        this.settingAtr = domain.getFixedWage().getSettingAtr().value;

        this.targetClassification = domain.getFixedWage().getEveryoneEqualSet().map(x -> Integer.valueOf(x.getTargetClassification().value)).orElse(null);
        this.monthlySalary = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getMonthlySalary().value)).orElse(null);
        this.monthlySalaryPerday = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getMonthlySalaryPerday().value)).orElse(null);
        this.dayPayee = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getDayPayee().value)).orElse(null);
        this.hourlyPay = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getHourlyPay().value)).orElse(null);
    }

}
