package nts.uk.screen.at.infra.shift.sixmonthscalendar;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.screen.at.app.shift.sixmonthscalendar.SixMonthsCalendarScreenRepository;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarClassScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarCompanyScreenDto;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarWorkPlaceScreenDto;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class SixMonthsCalendarRepositoryImpl extends JpaRepository implements SixMonthsCalendarScreenRepository {

    private static final String SELECT_SIXMONTHS_COMPANY;
    private static final String SELECT_SIXMONTHS_WORKPLACE;
    private static final String SELECT_SIXMONTHS_CLASSIFICATION;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarCompanyScreenDto.class.getName());
        stringBuilder.append(
                "(c.ksmmtCalendarCompanyPK.companyId, c.ksmmtCalendarCompanyPK.date, c.workingDayAtr) ");
        stringBuilder.append("FROM KsmmtCalendarCompany c ");
        stringBuilder.append("WHERE c.ksmmtCalendarCompanyPK.companyId = :companyId ");
        stringBuilder.append("AND c.ksmmtCalendarCompanyPK.date >= :startDate AND c.ksmmtCalendarCompanyPK.date <= :endDate ");
        stringBuilder.append("AND c.workingDayAtr != 0 ");
        SELECT_SIXMONTHS_COMPANY = stringBuilder.toString();

        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarWorkPlaceScreenDto.class.getName());
        stringBuilder.append(
                "(c.ksmmtCalendarWorkplacePK.workPlaceId, c.ksmmtCalendarCompanyPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KsmmtCalendarWorkplace c ");
        stringBuilder.append("WHERE c.ksmmtCalendarWorkplacePK.workPlaceId = :workPlaceId");
        stringBuilder.append("AND c.ksmmtCalendarCompanyPK.date >= :startDate AND c.ksmmtCalendarCompanyPK.date <= :endDate ");
        stringBuilder.append("AND c.workingDayAtr != 0 ");
        SELECT_SIXMONTHS_WORKPLACE = stringBuilder.toString();

        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarClassScreenDto.class.getName());
        stringBuilder.append(
                "(c.ksmmtCalendarClassPK.companyId, c.ksmmtCalendarClassPK.classId, c.ksmmtCalendarClassPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KsmmtCalendarClass c ");
        stringBuilder.append("WHERE c.ksmmtCalendarClassPK.classId = :classId ");
        stringBuilder.append("AND c.ksmmtCalendarClassPK.date >= :startDate AND c.ksmmtCalendarClassPK.date <= :endDate ");
        stringBuilder.append("AND c.workingDayAtr != 0 ");
        SELECT_SIXMONTHS_CLASSIFICATION = stringBuilder.toString();
    }

    @Override
    public List<SixMonthsCalendarCompanyScreenDto> getSixMonthsCalendarCompanyByYearMonth(String companyId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_COMPANY, SixMonthsCalendarCompanyScreenDto.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }

    @Override
    public List<SixMonthsCalendarWorkPlaceScreenDto> getSixMonthsCalendarWorkPlaceByYearMonth(String workPlaceId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_WORKPLACE, SixMonthsCalendarWorkPlaceScreenDto.class)
                .setParameter("workPlaceId", workPlaceId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }

    @Override
    public List<SixMonthsCalendarClassScreenDto> getSixMonthsCalendarClassByYearMonth(String classId, DatePeriod yearMonth) {
        return this.queryProxy().query(SELECT_SIXMONTHS_CLASSIFICATION, SixMonthsCalendarClassScreenDto.class)
                .setParameter("classId", classId)
                .setParameter("startDate", yearMonth.start())
                .setParameter("endDate", yearMonth.end())
                .getList();
    }
}
