package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;

/**
* 本人確認処理の利用設定
*/
@AllArgsConstructor
@Value
public class IdentityProcessDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 日の本人確認を利用する
    */
    private int useDailySelfCk;
    
    /**
    * 月の本人確認を利用する
    */
    private int useMonthSelfCK;
    
    /**
    * エラーがある場合の本人確認
    */
    private Integer yourselfConfirmError;
    
    
    public static IdentityProcessDto fromDomain(IdentityProcess domain)
    {
        return new IdentityProcessDto(domain.getCid(), domain.getUseDailySelfCk(), 
						        		domain.getUseMonthSelfCK(), 
						        		domain.getYourselfConfirmError() == null ? null : domain.getYourselfConfirmError().value);
    }
    
}
