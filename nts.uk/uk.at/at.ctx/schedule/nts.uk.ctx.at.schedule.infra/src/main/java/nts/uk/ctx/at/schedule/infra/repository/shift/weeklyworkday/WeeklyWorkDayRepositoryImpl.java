package nts.uk.ctx.at.schedule.infra.repository.shift.weeklyworkday;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.weeklyworkday.KscmtWeeklyWorkingdays;
import java.util.List;

public class WeeklyWorkDayRepositoryImpl extends JpaRepository implements WeeklyWorkDayRepository {

    /**
     * select a kscmtWeeklyWorkingdays ALL
     */
    private static final String SELECT_ALL = "SELECT w FROM KscmtWeeklyWorkingdays w";

    private static final String GET_BY_COMPANY_ID = SELECT_ALL + " WHERE w.kscmtWeeklyWorkingdaysPK.companyId = :conpanyId";

    @Override
    public WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId) {
        List<KscmtWeeklyWorkingdays> kscmtWeeklyWorkingdays = this.queryProxy().query(
                GET_BY_COMPANY_ID, KscmtWeeklyWorkingdays.class)
                .setParameter("companyId", companyId)
                .getList();
        return KscmtWeeklyWorkingdays.listEntitytoDomain(kscmtWeeklyWorkingdays);
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
