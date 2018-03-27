package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 月別実績の修正の機能
*/
@AllArgsConstructor
@Getter
public class MonPerformanceFun extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コメント
    */
    private Comment comment;
    
    /**
    * 日別の本人確認を表示する
    */
    private int dailySelfChkDispAtr;
    
    public static MonPerformanceFun createFromJavaType(String cid, String comment, int dailySelfChkDispAtr)
    {
        MonPerformanceFun  monPerformanceFun =  new MonPerformanceFun(cid, new Comment(comment),  dailySelfChkDispAtr);
        return monPerformanceFun;
    }
    
}
