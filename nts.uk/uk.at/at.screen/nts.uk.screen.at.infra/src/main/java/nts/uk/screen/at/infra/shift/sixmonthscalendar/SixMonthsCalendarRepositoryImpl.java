package nts.uk.screen.at.infra.shift.sixmonthscalendar;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.screen.at.app.shift.sixmonthscalendar.SixMonthsCalendarScreenRepository;
import nts.uk.screen.at.app.shift.sixmonthscalendar.dto.SixMonthsCalendarScreenDto;

import java.util.List;

public class SixMonthsCalendarRepositoryImpl extends JpaRepository implements SixMonthsCalendarScreenRepository {

    private static final String SELECT_SIXMONTHS_COMPANY;
    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT NEW " + SixMonthsCalendarScreenDto.class.getName());
        stringBuilder.append(
                "(c.ksmmtCalendarCompanyPK.companyId, c.ksmmtCalendarCompanyPK.date , c.workingDayAtr) ");
        stringBuilder.append("FROM KsmmtCalendarCompany c");
        stringBuilder.append("WHERE c.companyId = : companyId");
        stringBuilder.append("AND c.ksmmtCalendarCompanyPK.date >= :startDate AND c.ksmmtCalendarCompanyPK.date <= :endDate");
        SELECT_SIXMONTHS_COMPANY = stringBuilder.toString();
    }

//    stringBuilder.append("SELECT NEW " + WorkTypeDto.class.getName());
//		stringBuilder.append(
//                "(c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.deprecateAtr, c.memo, c.worktypeAtr, c.oneDayAtr, c.morningAtr, c.afternoonAtr, c.calculatorMethod, o.dispOrder) ");
//		stringBuilder.append("FROM KshmtWorkType c LEFT JOIN KshmtWorkTypeOrder o ");
//		stringBuilder.append(
//                "ON c.kshmtWorkTypePK.companyId = o.kshmtWorkTypeDispOrderPk.companyId AND c.kshmtWorkTypePK.workTypeCode = o.kshmtWorkTypeDispOrderPk.workTypeCode ");
//		stringBuilder.append("WHERE c.kshmtWorkTypePK.companyId = :companyId ");
//		stringBuilder.append(" AND c.deprecateAtr = 0 ");
//		stringBuilder.append(" AND ((c.worktypeAtr = 0 AND c.oneDayAtr IN :oneDayAtr) OR (c.worktypeAtr = 1 AND c.morningAtr IN :halfDay) OR (c.worktypeAtr = 1 AND c.afternoonAtr IN :halfDay))");
//		stringBuilder.append(" ORDER BY c.kshmtWorkTypePK.workTypeCode ASC");

    @Override
    public List<SixMonthsCalendarScreenDto> getSixCalendarCompanyByYearMonth(String companyId, Period Period) {
        return this.queryProxy().query(SELECT_SIXMONTHS_COMPANY, SixMonthsCalendarScreenDto.class)
                .setParameter("companyId", companyId)
                .setParameter("startDate", Period.getStartDate())
                .setParameter("endDate", Period.getEndDate())
                .getList();
    }
}
