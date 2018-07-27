package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 本人確認処理の利用設定
*/
@AllArgsConstructor
@Getter
public class IdentityProcess extends AggregateRoot
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
    private YourselfConfirmError yourselfConfirmError;
    
    public static IdentityProcess createFromJavaType(String cid, int useConfirmByYourself, int useIdentityOfMonth, Integer yourselfConfirmError){
        IdentityProcess  identityProcess =  new IdentityProcess(cid, useConfirmByYourself, useIdentityOfMonth, 
        															yourselfConfirmError == null ? null : EnumAdaptor.valueOf(yourselfConfirmError, YourselfConfirmError.class));
        return identityProcess;
    }
    
}
