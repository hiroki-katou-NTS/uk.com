package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
* 明細書レイアウト履歴: DTO
*/
@AllArgsConstructor
@Value
public class StatementLayoutHistDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 明細書コード
    */
    private String statementCode;
    /**
     * 明細書名称
     */
    private String statementName;
    
    /**
    * 履歴ID
    */
    private String historyId;

    /**
     * 開始年月
     */
    private int startYearMonth;

    /**
     * 終了年月
     */
    private int endYearMonth;



    
    

    
}
