package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemShortName;

@Getter
public class BreakdownItemSetCustom {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 内訳項目コード
     */
    private BreakdownItemCode breakdownItemCode;

    /**
     * 内訳項目名称
     */
    private ItemShortName breakdownItemName;

    public BreakdownItemSetCustom(String historyId, String breakdownItemCode, String breakdownItemName) {
        super();
        this.historyId = historyId;
        this.breakdownItemCode = new BreakdownItemCode(breakdownItemCode);
        this.breakdownItemName = new ItemShortName(breakdownItemName);
    }


}
