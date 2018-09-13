package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 健康保険報酬月額範囲
 */
@Getter
public class MonthlyHealthInsuranceCompensation extends AggregateRoot {

    /**
     * 対象期間
     */
    private YearMonthPeriod targetPeriod;

    /**
     * 等級毎報酬月額範囲
     */
    private List<HealthInsuranceGradePerRewardMonthlyRange> healthInsuranceGradePerRewardMonthlyRange;

    /**
     * 健康保険報酬月額範囲
     *
     * @param targetStartYm                             対象期間
     * @param targetEndYm                               対象期間
     * @param healthInsuranceGradePerRewardMonthlyRange 等級毎報酬月額範囲
     */
    public MonthlyHealthInsuranceCompensation(int targetStartYm, int targetEndYm, List<HealthInsuranceGradePerRewardMonthlyRange> healthInsuranceGradePerRewardMonthlyRange) {
        this.targetPeriod = new YearMonthPeriod(new YearMonth(targetStartYm), new YearMonth(targetEndYm));
        this.healthInsuranceGradePerRewardMonthlyRange = healthInsuranceGradePerRewardMonthlyRange;
    }
}
