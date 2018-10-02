package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSetting;

import java.math.BigDecimal;

/**
* 給与会社単価設定: DTO
*/
@AllArgsConstructor
@Value
public class PayrollUnitPriceSettingDto
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 金額
    */
    private BigDecimal amountOfMoney;
    
    /**
    * 対象区分
    */
    private Integer targetClass;
    
    /**
    * 日給月給者
    */
    private Integer monthSalaryPerDay;
    
    /**
    * 日給者
    */
    private Integer monthlySalary;
    
    /**
    * 時給者
    */
    private Integer hourlyPay;
    
    /**
    * 月給者
    */
    private Integer aDayPayee;
    
    /**
    * 設定区分
    */
    private int setClassification;
    
    /**
    * メモ
    */
    private String notes;
    
    
    public static PayrollUnitPriceSettingDto fromDomain(PayrollUnitPriceSetting domain)
    {
        return new PayrollUnitPriceSettingDto(domain.getHistoryId(), domain.getAmountOfMoney().v(),
                domain.getFixedWage().getEveryoneEqual().get().getTargetClass().value,
                domain.getFixedWage().getPerSalaryConType().get().getMonthSalaryPerDay().value,
                domain.getFixedWage().getPerSalaryConType().get().getADayPayee().value,
                domain.getFixedWage().getPerSalaryConType().get().getHourlyPay().value,
                domain.getFixedWage().getPerSalaryConType().get().getMonthlySalary().value,
                domain.getFixedWage().getSetClassification().value,
                domain.getNotes().map(i->i.v()).orElse(null));
    }
    
}
