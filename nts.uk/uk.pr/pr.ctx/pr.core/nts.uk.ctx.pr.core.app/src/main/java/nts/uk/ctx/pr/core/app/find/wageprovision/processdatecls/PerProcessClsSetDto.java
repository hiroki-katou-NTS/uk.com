package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSet;

/**
* 個人処理区分設定: DTO
*/
@AllArgsConstructor
@Value
public class PerProcessClsSetDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * ユーザID
    */
    private String uid;
    
    
    public static PerProcessClsSetDto fromDomain(PerProcessClsSet domain)
    {
        return new PerProcessClsSetDto(domain.getCid(), domain.getProcessCateNo(), domain.getUid());
    }
    
}
