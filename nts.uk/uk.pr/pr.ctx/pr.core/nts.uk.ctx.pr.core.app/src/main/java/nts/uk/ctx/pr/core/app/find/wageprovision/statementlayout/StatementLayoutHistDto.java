package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;

import java.util.List;
import java.util.stream.Collectors;

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
    
    
    public static List<StatementLayoutHistDto> fromDomain(StatementLayoutHist domain)
    {
        List<StatementLayoutHistDto> statementLayoutHistDtoList = domain.getHistory().stream().map(item -> {
            return new StatementLayoutHistDto(domain.getCid(),domain.getStatementCode().toString(),item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
        return statementLayoutHistDtoList;
    }
    
}
