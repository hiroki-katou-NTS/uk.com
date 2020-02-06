package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.arc.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 社員社会保険等級履歴
 */
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmpSocialInsGradeHis extends AggregateRoot
        implements UnduplicatableHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    private List<YearMonthHistoryItem> yearMonthHistoryItems;

    @Override
    public List<YearMonthHistoryItem> items() {
        return yearMonthHistoryItems;
    }

    public List<String> getHistoryIds() {
        return yearMonthHistoryItems.stream().map(x -> x.identifier()).collect(Collectors.toList());
    }
}
