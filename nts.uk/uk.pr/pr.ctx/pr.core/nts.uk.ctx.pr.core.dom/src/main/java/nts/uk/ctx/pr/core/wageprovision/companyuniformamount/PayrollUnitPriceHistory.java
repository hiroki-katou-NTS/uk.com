package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.YearMonthHistoryItem;

/**
 * 給与会社単価履歴
 */

@Getter
public class PayrollUnitPriceHistory extends AggregateRoot {
    /**
     * コード
     */
    private CompanyUnitPriceCode code;

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 履歴
     */
    private YearMonthHistoryItem history;

    public PayrollUnitPriceHistory(String code, String cId,YearMonthHistoryItem history){
        this.code = new CompanyUnitPriceCode(code);
        this.cId = cId;
        this.history = history;
    }
}
