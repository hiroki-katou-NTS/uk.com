package nts.uk.ctx.exio.dom.exo.useroutputcnd;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 出力条件詳細
*/
@AllArgsConstructor
@Getter
public class UserOutCndDetail extends AggregateRoot
{
    
    /**
    * 条件SQL
    */
    private String cndSql;
    
    /**
    * 条件設定コード
    */
    private String cndSetCd;
    
    /**
    * ユーザID
    */
    private String userId;
    
    
}
