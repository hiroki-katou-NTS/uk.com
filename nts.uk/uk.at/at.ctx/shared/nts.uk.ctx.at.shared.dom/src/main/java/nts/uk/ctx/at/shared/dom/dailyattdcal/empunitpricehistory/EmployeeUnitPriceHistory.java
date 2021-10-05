/**
 * 
 */
package nts.uk.ctx.at.shared.dom.dailyattdcal.empunitpricehistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;

/**
 * AR: 社員単価履歴
 * path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.人件費計算.社員単価履歴.社員単価履歴
 * @author laitv
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class EmployeeUnitPriceHistory extends AggregateRoot implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {

    // 社員ID
    private String sid;

    // 履歴: 年月日期間の汎用履歴項目
    private List<DateHistoryItem> historyItems;


    @Override
    public List<DateHistoryItem> items() {
        return historyItems;
    }

    public static EmployeeUnitPriceHistory toDomain(String sid, List<DateHistoryItem> items) {
        return new EmployeeUnitPriceHistory(sid, items);
    }
}
