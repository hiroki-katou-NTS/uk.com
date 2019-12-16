package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 社員社会保険等級履歴
 */
@Setter
@Getter
public class EmpSocialInsGradeHis extends AggregateRoot implements UnduplicatableHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> period;

    public EmpSocialInsGradeHis() {
    }

    public EmpSocialInsGradeHis(String employeeId, List<YearMonthHistoryItem> period) {
        this.employeeId = employeeId;
        this.period = period;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return period;
    }
}
