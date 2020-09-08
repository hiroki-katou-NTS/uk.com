package nts.uk.ctx.at.schedule.app.find.shift.weeklyworkday;



import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class WeeklyWorkDayFinder {
    @Inject
    private WeeklyWorkSettingRepository getWeeklyWorkDayPatternByCompanyId;

    /**
     * Find all weekly work day
     * @param cid
     * @return
     */

    public WeeklyWorkDayDto getWeeklyWorkDay(String cid) {
        WeeklyWorkDayDto weeklyWorkDayDto = new WeeklyWorkDayDto();
        WeeklyWorkDayPattern weeklyWorkDayPattern = getWeeklyWorkDayPatternByCompanyId.getWeeklyWorkDayPatternByCompanyId(cid);
        return weeklyWorkDayDto.toDto(weeklyWorkDayPattern);
    }
}
