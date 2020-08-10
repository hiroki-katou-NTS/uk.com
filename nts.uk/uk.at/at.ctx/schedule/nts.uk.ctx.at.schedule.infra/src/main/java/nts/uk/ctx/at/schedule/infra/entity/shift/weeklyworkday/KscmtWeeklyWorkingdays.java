package nts.uk.ctx.at.schedule.infra.entity.shift.weeklyworkday;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WorkdayPatternItem;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * チーム
 *
 * @author datnk
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WEEKLY_WORKINGDAYS")
public class KscmtWeeklyWorkingdays extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtWeeklyWorkingdaysPK kscmtWeeklyWorkingdaysPK;

    @Column(name = "CONTRACT_CD")
    public String contractCode;

    @Column(name = "WORK_DAY_ATR")
    public int workdayDivision;

    @Override
    protected Object getKey() {
        return this.kscmtWeeklyWorkingdaysPK;
    }

    public static List<KscmtWeeklyWorkingdays> toEntity(WeeklyWorkDayPattern domain) {
        return domain.getListWorkdayPatternItem().stream().map(item -> new KscmtWeeklyWorkingdays(
                new KscmtWeeklyWorkingdaysPK(domain.getCompanyId().v(), item.getDayOfWeek().value),
                AppContexts.user().contractCode(),
                item.getWorkdayDivision().value
        )).collect(Collectors.toList());
    }

    public static WeeklyWorkDayPattern listEntitytoDomain(List<KscmtWeeklyWorkingdays> entities) {
        List<WorkdayPatternItem> workdayPatternItems =  entities.stream().map(item -> new WorkdayPatternItem(
                    DayOfWeek.valueOf(item.kscmtWeeklyWorkingdaysPK.dayOfWeek),
                    WorkdayDivision.valuesOf(item.workdayDivision)
                )).collect(Collectors.toList());
        return new WeeklyWorkDayPattern(
                new CompanyId(entities.get(0).kscmtWeeklyWorkingdaysPK.companyId),
                workdayPatternItems
        );
    }

}
