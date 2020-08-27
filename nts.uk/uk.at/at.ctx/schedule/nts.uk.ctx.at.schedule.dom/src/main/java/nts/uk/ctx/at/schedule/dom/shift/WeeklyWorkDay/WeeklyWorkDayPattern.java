package nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import java.util.List;

/**
 * The Class WeeklyWorkDay.
 */
// 週間勤務設定
@Getter
@NoArgsConstructor
public class WeeklyWorkDayPattern extends AggregateRoot {

    /** The company id. */
    // 会社ID
    private CompanyId companyId;

    /** The list Workday */
    //曜日勤務設定リスト
    private List<WorkdayPatternItem> listWorkdayPatternItem;

    public WeeklyWorkDayPattern(CompanyId companyId, List<WorkdayPatternItem> listWorkdayPatternItem) {
        this.companyId = companyId;
        this.listWorkdayPatternItem = listWorkdayPatternItem;
    }
}
