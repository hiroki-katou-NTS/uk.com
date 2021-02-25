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
                "(c.ksmmtCalendarClassPK.companyId, c.ksmmtCalendarClassPK.classId, c.ksmmtCalendarClassPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KsmmtCalendarClass c ");
        stringBuilder.append("WHERE c.ksmmtCalendarClassPK.classId = :classId ");
        stringBuilder.append("AND c.ksmmtCalendarClassPK.companyId = :companyId ");
        stringBuilder.append("AND c.ksmmtCalendarClassPK.date >= :startDate AND c.ksmmtCalendarClassPK.date <= :endDate ");
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
