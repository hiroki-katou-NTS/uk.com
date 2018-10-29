package nts.uk.ctx.pr.core.app.command.wageprovision.salaryindividualamountname;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SalIndAmountNameCommand
{
    /**
    * 個人金額コード
    */
    private String individualPriceCode;
    
    /**
    * カテゴリ区分
    */
    private int cateIndicator;
    
    /**
    * 個人金額名称
    */
    private String individualPriceName;

}
