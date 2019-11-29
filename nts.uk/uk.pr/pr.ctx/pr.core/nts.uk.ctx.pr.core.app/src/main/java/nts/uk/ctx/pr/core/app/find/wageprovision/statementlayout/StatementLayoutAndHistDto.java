package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class StatementLayoutAndHistDto
{

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
    private List<StatementLayoutHistDto> history;

    public static StatementLayoutAndHistDto fromDomain(StatementLayout statementLayout, List<YearMonthHistoryItem> history)
    {
        List<StatementLayoutHistDto> statementLayoutHistDto = history.stream().map(item -> StatementLayoutHistDto.fromDomainToDto(item)).collect(Collectors.toList());
        return new StatementLayoutAndHistDto(statementLayout.getStatementCode().v(), statementLayout.getStatementName().v(), statementLayoutHistDto);
    }

}

