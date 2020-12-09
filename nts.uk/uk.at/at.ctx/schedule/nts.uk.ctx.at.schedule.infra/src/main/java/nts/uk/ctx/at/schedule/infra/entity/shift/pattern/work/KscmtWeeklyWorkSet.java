/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WorkdayPatternItem;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtWeeklyWorkSet.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KSCMT_WEEKLY_WORKINGDAYS")
public class KscmtWeeklyWorkSet extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt weekly work set PK. */
    @EmbeddedId
    protected KscmtWeeklyWorkSetPK kscmtWeeklyWorkSetPK;
    
    /** The work day div. */
    @Column(name = "WORK_DAY_ATR")
    private int workDayAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtWeeklyWorkSetPK;
	}

    public static List<KscmtWeeklyWorkSet> toEntity(WeeklyWorkDayPattern domain) {
        return domain.getListWorkdayPatternItem().stream().map(item ->
            new KscmtWeeklyWorkSet(
                    new KscmtWeeklyWorkSetPK(domain.getCompanyId().v(), item.getDayOfWeek().value),
                    item.getWorkdayDivision().value
            )
        ).collect(Collectors.toList());
    }

    public static WeeklyWorkDayPattern listEntitytoDomain(List<KscmtWeeklyWorkSet> entities) {
	    if (entities.size() > 0) {
            List<WorkdayPatternItem> workdayPatternItems =  entities.stream().map(item -> new WorkdayPatternItem(
                    DayOfWeek.valueOf(item.getKscmtWeeklyWorkSetPK().getDayOfWeek()),
                    WorkdayDivision.valuesOf(item.getWorkDayAtr())
            )).collect(Collectors.toList());
            return new WeeklyWorkDayPattern(
                    new CompanyId(entities.get(0).kscmtWeeklyWorkSetPK.getCid()),
                    workdayPatternItems
            );
        }
        return null;
    }
    
}
