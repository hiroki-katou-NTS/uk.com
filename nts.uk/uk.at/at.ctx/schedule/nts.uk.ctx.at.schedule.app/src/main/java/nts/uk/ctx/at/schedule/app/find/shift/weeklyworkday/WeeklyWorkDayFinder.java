package nts.uk.ctx.at.schedule.app.find.shift.weeklyworkday;



import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class WeeklyWorkDayFinder {
    @Inject
    private WeeklyWorkDayRepository weeklyWorkDayRepository;

    /**
     * Find all weekly work day
     * @param CompanyId
     * @return
     */

    public WeeklyWorkDayDto getWeeklyWorkDay(String cid) {
        WeeklyWorkDayDto weeklyWorkDayDto = new WeeklyWorkDayDto();
        WeeklyWorkDayPattern weeklyWorkDayPattern = weeklyWorkDayRepository.getWeeklyWorkDayPatternByCompanyId(cid);
        return weeklyWorkDayDto.toDto(weeklyWorkDayPattern);
    }
}
