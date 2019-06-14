package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalaryPerUnitPriceNameCommand
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
    * 名称
    */
    private String name;
    
    /**
    * 廃止区分
    */
    private int abolition;
    
    /**
    * 略名
    */
    private String shortName;
    
    /**
    * 統合コード
    */
    private String integrationCode;
    
    /**
    * メモ
    */
    private String note;
    

}
