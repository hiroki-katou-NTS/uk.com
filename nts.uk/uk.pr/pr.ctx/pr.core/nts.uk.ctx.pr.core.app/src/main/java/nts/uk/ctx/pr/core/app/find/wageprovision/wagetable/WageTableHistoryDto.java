package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableName;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 賃金テーブル
 */
@Data
@NoArgsConstructor
public class WageTableHistoryDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 賃金テーブルコード
     */
    private String wageTableCode;

    /**
     * 賃金テーブル名
     */
    private String wageTableName;

    /**
     * 有効期間
     */
    private List<YearMonthHistoryItemDto> history;

    public static WageTableHistoryDto fromDomainToDto(WageTable wageTable, Optional<WageTableHistory> wageTableHistory) {
        WageTableHistoryDto dto = new WageTableHistoryDto();
        dto.cid = wageTable.getCid();
        dto.wageTableCode = wageTable.getWageTableCode().v();
        dto.wageTableName = wageTable.getWageTableName().v();
        dto.history = wageTableHistory.map(i -> i.getHistory().stream().map(YearMonthHistoryItemDto::fromDomainToDto).collect(Collectors.toList())).orElse(Collections.emptyList());
        return dto;
    }
}
