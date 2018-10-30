package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 明細書レイアウト
*/
@Getter
public class StatementLayout extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 明細書コード
    */
    private StatementCode statementCode;
    
    /**
    * 明細書名称
    */
    private StatementName statementName;
    
    public StatementLayout(String cid, String statementCD, String statementName) {
        this.cid = cid;
        this.statementName = new StatementName(statementName);
        this.statementCode = new StatementCode(statementCD);
    }
    
}
