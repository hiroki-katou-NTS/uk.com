package nts.uk.screen.at.infra.shift.businesscalendar.day;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.WorkdayCalendarRepository;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarWorkPlaceScreenDto;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class WorkdayCalendarRepositoryImpl extends JpaRepository implements WorkdayCalendarRepository {
    private static final String SELECT_SIXMONTHS_WORKPLACE;

    static {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("SELECT NEW " + SixMonthsCalendarWorkPlaceScreenDto.class.getName());
        stringBuilder.append(" (c.kscmtCalendarWkpPK.workPlaceId, c.kscmtCalendarWkpPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KscmtCalendarWkp c ");
        stringBuilder.append("WHERE c.kscmtCalendarWkpPK.workPlaceId = :workPlaceId ");
        stringBuilder.append("AND c.kscmtCalendarWkpPK.date >= :startDate AND c.kscmtCalendarWkpPK.date <= :endDate ");
        SELECT_SIXMONTHS_WORKPLACE = stringBuilder.toString();


    }
    @Override
    public List<SixMonthsCalendarWorkPlaceScreenDto> getNonWorkingDaysWorkplace(String workPlaceId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_WORKPLACE, SixMonthsCalendarWorkPlaceScreenDto.class)
                .setParameter("workPlaceId", workPlaceId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }
}
