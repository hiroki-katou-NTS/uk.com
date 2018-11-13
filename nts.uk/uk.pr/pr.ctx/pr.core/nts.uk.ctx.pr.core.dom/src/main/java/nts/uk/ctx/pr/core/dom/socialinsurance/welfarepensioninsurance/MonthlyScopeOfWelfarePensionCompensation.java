package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 厚生年金報酬月額範囲
 */
@Getter
public class MonthlyScopeOfWelfarePensionCompensation extends AggregateRoot {

    /**
     * 対象期間
     */
    private YearMonthHistoryItem targetPeriod;

    /**
     * 等級毎報酬月額範囲
     */
    private List<WelfarePensionGradePerRewardMonthlyRange> welfarePensionGradePerRewardMonthlyRange;

    /**
     * 厚生年金報酬月額範囲
     *
     * @param historyId                                履歴ID
     * @param targetStartYm                            対象期間
     * @param targetEndYm                              対象期間
     * @param welfarePensionGradePerRewardMonthlyRange 等級毎報酬月額範囲
     */
    public MonthlyScopeOfWelfarePensionCompensation(String historyId, int targetStartYm, int targetEndYm, List<WelfarePensionGradePerRewardMonthlyRange> welfarePensionGradePerRewardMonthlyRange) {
        this.targetPeriod                             = new YearMonthHistoryItem(historyId, new YearMonthPeriod(new YearMonth(targetStartYm), new YearMonth(targetEndYm)));
        this.welfarePensionGradePerRewardMonthlyRange = welfarePensionGradePerRewardMonthlyRange;
    }
}
