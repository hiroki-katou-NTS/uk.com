package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 社保勤務形態情報
*/
@Getter
@AllArgsConstructor
public class CorWorkFormInfo extends AggregateRoot {

    /**
    * 履歴ID
    */
    private String historyId;

    /**
    * 被保険者区分
    */
    private InsPerCls insPerCls;

}
