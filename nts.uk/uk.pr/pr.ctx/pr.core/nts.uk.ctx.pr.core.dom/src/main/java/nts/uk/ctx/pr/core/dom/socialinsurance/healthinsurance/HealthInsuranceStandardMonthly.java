package nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 健康保険標準月額
 */
@Getter
public class HealthInsuranceStandardMonthly extends AggregateRoot {

    /**
     * 対象期間
     */
    private YearMonthHistoryItem targetPeriod;

    /**
     * 等級毎標準月額
     */
    private List<HealthInsuranceStandardGradePerMonth> standardGradePerMonth;

    /**
     * @param historyId             履歴ID
     * @param targetStartYm         対象期間
     * @param targetEndYm           対象期間
     * @param standardGradePerMonth 等級毎標準月額
     */
    public HealthInsuranceStandardMonthly(String historyId, int targetStartYm, int targetEndYm, List<HealthInsuranceStandardGradePerMonth> standardGradePerMonth) {
        this.targetPeriod          = new YearMonthHistoryItem(historyId, new YearMonthPeriod(new YearMonth(targetStartYm), new YearMonth(targetEndYm)));
        this.standardGradePerMonth = standardGradePerMonth;
    }
}