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
    * 本人確認を利用する
    */
    private int useConfirmByYourself;
    
    /**
    * 月の本人確認を利用する
    */
    private int useIdentityOfMonth;
    
    /**
    * エラーがある場合の本人確認
    */
    private int yourselfConfirmError;
    
    
    public static IdentityProcessDto fromDomain(IdentityProcess domain)
    {
        return new IdentityProcessDto(domain.getCid(), domain.getUseConfirmByYourself(), domain.getUseIdentityOfMonth(), domain.getYourselfConfirmError().value);
    }
    
}
