package nts.uk.screen.at.infra.shift.businesscalendar.day;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.CompanyCalendarRepository;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarCompanyScreenDto;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CompanyCalendarRepositoryImpl extends JpaRepository implements CompanyCalendarRepository {
    private static final String SELECT_SIXMONTHS_COMPANY;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarCompanyScreenDto.class.getName());
        stringBuilder.append(
                "(c.kscmtCalendarComPK.companyId, c.kscmtCalendarComPK.date, c.workingDayAtr) ");
        stringBuilder.append("FROM KscmtCalendarCom c ");
        stringBuilder.append("WHERE c.kscmtCalendarComPK.companyId = :companyId ");
        stringBuilder.append("AND c.kscmtCalendarComPK.date >= :startDate AND c.kscmtCalendarComPK.date <= :endDate ");
        SELECT_SIXMONTHS_COMPANY = stringBuilder.toString();
    }
    @Override
    public List<SixMonthsCalendarCompanyScreenDto> getCompanyNonWorkingDays(String companyId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_COMPANY, SixMonthsCalendarCompanyScreenDto.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }
}
