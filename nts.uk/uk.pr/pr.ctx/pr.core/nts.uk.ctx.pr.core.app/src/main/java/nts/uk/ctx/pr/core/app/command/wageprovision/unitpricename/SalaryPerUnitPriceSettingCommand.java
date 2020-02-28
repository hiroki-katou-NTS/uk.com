package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalaryPerUnitPriceSettingCommand
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
    

}
