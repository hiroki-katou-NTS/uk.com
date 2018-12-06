package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.YearMonthHistoryItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 賃金テーブル
 */
@Data
@NoArgsConstructor
public class WageTableHistoryCommand {

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
    private List<YearMonthHistoryItemCommand> history;

    private YearMonthHistoryItemCommand yearMonth;

    private WageTableContentCommand wageTableContent;

    private ElementRangeSettingCommand elementRangeSetting;

    public WageTableHistory fromCommandToDomain() {
        return new WageTableHistory(cid, wageTableCode, history.stream().map(YearMonthHistoryItemCommand::fromCommandToDomain).collect(Collectors.toList()));
    }
}
