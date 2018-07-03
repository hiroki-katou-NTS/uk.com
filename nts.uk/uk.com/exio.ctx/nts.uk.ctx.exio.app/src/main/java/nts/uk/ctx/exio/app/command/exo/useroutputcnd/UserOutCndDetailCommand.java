package nts.uk.ctx.exio.app.command.exo.useroutputcnd;

import lombok.Value;


@Value
public class UserOutCndDetailCommand
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
    
    private Long version;

}
