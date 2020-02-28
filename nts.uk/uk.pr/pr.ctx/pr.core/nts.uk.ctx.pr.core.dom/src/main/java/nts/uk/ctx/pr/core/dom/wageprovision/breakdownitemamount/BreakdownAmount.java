package nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

/**
 * 給与内訳個人金額
 */
@Getter
public class BreakdownAmount extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 内訳金額一覧
     */
    private List<BreakdownAmountList> breakdownAmountList;

    /**
     * @param historyId           履歴ID
     * @param breakdownAmountList 内訳金額一覧
     */

    public BreakdownAmount(String historyId, List<BreakdownAmountList> breakdownAmountList) {
        this.historyId = historyId;
        this.breakdownAmountList = breakdownAmountList;
    }

}
