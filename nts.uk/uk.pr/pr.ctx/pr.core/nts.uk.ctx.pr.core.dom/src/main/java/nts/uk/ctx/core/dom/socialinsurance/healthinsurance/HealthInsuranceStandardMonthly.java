package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
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
    private YearMonthPeriod targetPeriod;

    /**
     * 等級毎標準月額
     */
    private List<HealthInsuranceStandardGradePerMonth> standardGradePerMonth;

    public HealthInsuranceStandardMonthly(int targetStartYm, int targetEndYm, List<HealthInsuranceStandardGradePerMonth> standardGradePerMonth) {
        this.targetPeriod = new YearMonthPeriod(new YearMonth(targetStartYm), new YearMonth(targetEndYm));
        this.standardGradePerMonth = standardGradePerMonth;
    }
}
