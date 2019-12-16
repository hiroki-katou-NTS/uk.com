package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 社員社会保険等級履歴
 */
@Setter
@Getter
public class EmpSocialInsGradeHis extends AggregateRoot implements UnduplicatableHistory<GenericHistYMPeriod, YearMonthPeriod, YearMonth> {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    private List<GenericHistYMPeriod> period;

    public EmpSocialInsGradeHis() {
    }

    public EmpSocialInsGradeHis(String employeeId, List<GenericHistYMPeriod> period) {
        this.employeeId = employeeId;
        this.period = period;
    }

    @Override
    public List<GenericHistYMPeriod> items() {
        return period;
    }
}
