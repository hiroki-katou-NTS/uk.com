package nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.shr.com.history.YearMonthHistoryItem;

@AllArgsConstructor
@Value
public class YearMonthHistoryItemDto {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 期間
     */

    private Integer periodStartYm;


    private Integer periodEndYm;


    public static YearMonthHistoryItemDto fromDomaintoDto(YearMonthHistoryItem domain) {
        return new YearMonthHistoryItemDto(domain.identifier(), domain.start().v(), domain.end().v());
    }

}
