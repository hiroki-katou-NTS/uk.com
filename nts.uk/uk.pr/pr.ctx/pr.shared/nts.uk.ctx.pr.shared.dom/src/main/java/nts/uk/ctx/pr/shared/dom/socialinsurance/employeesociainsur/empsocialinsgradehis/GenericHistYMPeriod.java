package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 年月期間の汎用履歴項目
 */
@Getter
public class GenericHistYMPeriod extends YearMonthHistoryItem {

    /**
     * 履歴ID
     */
    private String histId;

    /**
     * 期間
     */
    private YearMonthPeriod ymPeriod;


    public GenericHistYMPeriod(String historyId, YearMonthPeriod period) {
        super(historyId, period);
    }
}
