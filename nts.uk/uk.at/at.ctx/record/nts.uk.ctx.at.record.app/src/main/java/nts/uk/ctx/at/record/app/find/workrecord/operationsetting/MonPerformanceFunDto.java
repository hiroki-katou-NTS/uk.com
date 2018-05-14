package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;

/**
* 月別実績の修正の機能
*/
@AllArgsConstructor
@Value
public class MonPerformanceFunDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コメント
    */
    private String comment;
    
    /**
    * 日別の本人確認を表示する
    */
    private int dailySelfChkDispAtr;
    
    
    public static MonPerformanceFunDto fromDomain(MonPerformanceFun domain)
    {
        return new MonPerformanceFunDto(domain.getCid(), domain.getComment().toString(), domain.getDailySelfChkDispAtr());
    }
    
}
