package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PayrollUnitPriceSettingCommand
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
    

}
