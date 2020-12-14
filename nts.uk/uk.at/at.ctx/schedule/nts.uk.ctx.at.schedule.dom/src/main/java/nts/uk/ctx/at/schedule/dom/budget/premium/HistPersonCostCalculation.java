package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * AggregateRoot: 人件費計算設定の履歴
 */
@AllArgsConstructor
public class HistPersonCostCalculation extends AggregateRoot {

    // 会社ID
    private String companyId;

    // 履歴: 年月日期間の汎用履歴項目
    private DateHistoryItem historyItem;


}
