package nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author datnk
 *
 */
public interface WeeklyWorkDayRepository {
    /**
     * get team by workplaceId
     *
     * @param CompanyId
     * @return
     */
    WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId);

    /**
     * insert team
     */
    void addWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);

    /**
     * update team
     */
    void updateWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);
}
