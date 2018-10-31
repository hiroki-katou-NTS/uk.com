package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

@AllArgsConstructor
@Value
public class StatementLayoutAndLastHistDto
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
     * 履歴
     */
    private YearMonthHistoryItemDto history;

    public static StatementLayoutAndLastHistDto fromDomain(StatementLayout statementLayout, Optional<YearMonthHistoryItem> historyItem)
    {
        return new StatementLayoutAndLastHistDto(statementLayout.getCid(), statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(),
                historyItem.isPresent() ? YearMonthHistoryItemDto.fromDomainToDto(historyItem.get()): null);
    }

}

