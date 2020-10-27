package nts.uk.screen.at.infra.shift.businesscalendar.day;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.businesscalendar.day.ClassificationCalendarRepository;
import nts.uk.screen.at.app.shift.businesscalendar.day.dto.SixMonthsCalendarClassScreenDto;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ClassificationCalendarRepositoryImpl extends JpaRepository implements ClassificationCalendarRepository {

    private static final String SELECT_SIXMONTHS_CLASSIFICATION;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarClassScreenDto.class.getName());
        stringBuilder.append(
                "(c.kscmtCalendarClsPK.companyId, c.kscmtCalendarClsPK.classId, c.kscmtCalendarClsPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KscmtCalendarCls c ");
        stringBuilder.append("WHERE c.kscmtCalendarClsPK.classId = :classId ");
        stringBuilder.append("AND c.kscmtCalendarClsPK.companyId = :companyId ");
        stringBuilder.append("AND c.kscmtCalendarClsPK.date >= :startDate AND c.kscmtCalendarClsPK.date <= :endDate ");
        SELECT_SIXMONTHS_CLASSIFICATION = stringBuilder.toString();
    }

    @Override
    public List<SixMonthsCalendarClassScreenDto> getNonWorkingDaysClassification(String conpanyId, String classId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_CLASSIFICATION, SixMonthsCalendarClassScreenDto.class)
                .setParameter("companyId", conpanyId)
                .setParameter("classId", classId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }
}
