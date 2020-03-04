package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;

import java.math.BigDecimal;

/**
 * 給与単価情報
 */
@Data
public class PayrollInformation extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyID;
    /**
     * 個人単価
     */
    private SalaryUnitPrice individualUnitPrice;

    /**
     * 給与単価情報
     *
     * @param historyID           履歴ID
     * @param individualUnitPrice 個人単価
     */
    public PayrollInformation(String historyID, BigDecimal individualUnitPrice) {
        this.historyID = historyID;
        this.individualUnitPrice = new SalaryUnitPrice(individualUnitPrice);
    }
}