package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;

/**
 * 賃金テーブル
 */
@Data
@NoArgsConstructor
public class WageTableHistoryDto {

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
        dto.wageTableCode = wageTable.getWageTableCode().v();
        dto.wageTableName = wageTable.getWageTableName().v();
        dto.history = wageTableHistory.map(i -> i.getValidityPeriods().stream().map(YearMonthHistoryItemDto::fromDomainToDto).collect(Collectors.toList())).orElse(Collections.emptyList());
        return dto;
    }
}
