package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 年月期間の汎用履歴項目
 */
@Getter
public class GenericHistYMPeriod extends DomainObject {

    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 期間
     */
    private DateHistoryItem datePeriod;

    public GenericHistYMPeriod(String histId, DateHistoryItem datePeriod) {
        this.histId = histId;
        this.datePeriod = datePeriod;
    }
}
