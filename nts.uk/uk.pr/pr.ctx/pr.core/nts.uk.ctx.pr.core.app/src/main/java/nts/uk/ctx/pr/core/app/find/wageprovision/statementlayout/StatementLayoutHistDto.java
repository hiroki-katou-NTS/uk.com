package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.YearMonthHistoryItemCustom;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@Data
@AllArgsConstructor
public class StatementLayoutHistDto {
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public Integer layoutPattern;

    private StatementLayoutHistDto(String historyId, Integer startMonth, Integer endMonth) {
        this.historyId = historyId;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }
    public static StatementLayoutHistDto fromDomainToDto (YearMonthHistoryItem domain){
        StatementLayoutHistDto result = new StatementLayoutHistDto(domain.identifier(), domain.start().v(), domain.end().v());

        if(domain instanceof YearMonthHistoryItemCustom) {
            result.setLayoutPattern(((YearMonthHistoryItemCustom) domain).getLayoutPattern());
        }

        return result;
    }
}
