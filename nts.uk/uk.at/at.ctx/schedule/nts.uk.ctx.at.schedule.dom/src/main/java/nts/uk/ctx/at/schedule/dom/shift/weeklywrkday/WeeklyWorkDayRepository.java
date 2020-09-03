package nts.uk.ctx.at.schedule.dom.shift.weeklywrkday;

/**
 *
 * @author datnk
 *
 */

public interface WeeklyWorkDayRepository {

    /**
     * get all weekly work day
     * @param companyId
     * @return
     */
    WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId);

    /**
     * insert weekly work day
     */
    void addWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);

    /**
     * update weekly work day
     */
    void updateWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern);
}
