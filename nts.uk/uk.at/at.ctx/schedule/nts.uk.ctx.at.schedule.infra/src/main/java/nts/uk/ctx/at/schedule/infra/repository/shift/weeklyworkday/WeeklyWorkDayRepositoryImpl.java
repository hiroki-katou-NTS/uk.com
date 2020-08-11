package nts.uk.ctx.at.schedule.infra.repository.shift.weeklyworkday;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.weeklyworkday.KscmtWeeklyWorkingdays;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class WeeklyWorkDayRepositoryImpl extends JpaRepository implements WeeklyWorkDayRepository {

    /**
     * select a kscmtWeeklyWorkingdays ALL
     */
    private static final String SELECT_ALL = "SELECT w FROM KscmtWeeklyWorkingdays w";

    private static final String GET_BY_COMPANY_ID = SELECT_ALL + " WHERE w.kscmtWeeklyWorkingdaysPK.companyId = :companyId";

    @Override
    public WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId) {
        System.out.println("companyId "+ companyId);
        System.out.println("query "+ GET_BY_COMPANY_ID);
        List<KscmtWeeklyWorkingdays> kscmtWeeklyWorkingdays = this.queryProxy().query(
                GET_BY_COMPANY_ID, KscmtWeeklyWorkingdays.class)
                .setParameter("companyId", companyId)
                .getList();
        System.out.println("kscmtWeeklyWorkingdays:  " + kscmtWeeklyWorkingdays);
        WeeklyWorkDayPattern weeklyWorkDayPattern = KscmtWeeklyWorkingdays.listEntitytoDomain(kscmtWeeklyWorkingdays);
        System.out.println("weeklyWorkDayPattern "+ weeklyWorkDayPattern);
        return weeklyWorkDayPattern;
    }

    @Override
    public void addWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern) {
        KscmtWeeklyWorkingdays.toEntity(weeklyWorkDayPattern).forEach(item -> this.commandProxy().insert(item));
    }

    @Override
    public void updateWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern) {
        KscmtWeeklyWorkingdays.toEntity(weeklyWorkDayPattern).forEach(item -> this.commandProxy().update(item));
    }
}
