package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;

/**
* 給与個人単価設定: DTO
*/
@AllArgsConstructor
@Value
public class SalaryPerUnitPriceSettingDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String code;
    
    /**
    * 単価種類
    */
    private int unitPriceType;
    
    /**
    * 設定区分
    */
    private int settingAtr;
    
    /**
    * 対象区分
    */
    private Integer targetClassification;
    
    /**
    * 月給者
    */
    private Integer monthlySalary;
    
    /**
    * 日給月給者
    */
    private Integer monthlySalaryPerday;
    
    /**
    * 日給者
    */
    private Integer dayPayee;
    
    /**
    * 時給者
    */
    private Integer hourlyPay;
    
    
    public SalaryPerUnitPriceSettingDto (SalaryPerUnitPriceSetting domain)
    {
        this.cid = domain.getCid();
        this.code = domain.getCode().v();
        this.unitPriceType = domain.getUnitPriceType().value;
        this.settingAtr = domain.getFixedWage().getSettingAtr().value;

        this.targetClassification = domain.getFixedWage().getEveryoneEqualSet().map(x -> Integer.valueOf(x.getTargetClassification().value)).orElse(null);
        this.monthlySalary = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getMonthlySalary().value)).orElse(null);
        this.monthlySalaryPerday = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getMonthlySalaryPerday().value)).orElse(null);
        this.dayPayee = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getDayPayee().value)).orElse(null);
        this.hourlyPay = domain.getFixedWage().getPerSalaryContractType().map(x -> Integer.valueOf(x.getHourlyPay().value)).orElse(null);
    }
    
}
