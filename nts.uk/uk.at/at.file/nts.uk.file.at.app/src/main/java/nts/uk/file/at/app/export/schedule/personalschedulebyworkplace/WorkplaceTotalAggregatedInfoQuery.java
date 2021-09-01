package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.WorkplaceCounterCategory;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 職場計の集計情報を取得する
 */
@Stateless
public class WorkplaceTotalAggregatedInfoQuery {
    @Inject
    private AggregateWorkplaceTotalQuery totalQuery;

    @Inject
    private AggregateNumberOfPeopleByWorkQuery aggregateNumberOfPeopleByWorkQuery;

    /**
     * 集計する
     * @param scheduleTableAttendanceItems
     * @param workplaceCounterCategories
     * @param integrationOfDailyMap
     * @param dailyDataDisplayAtr
     * @param period
     * @param targetOrg
     * @param <T>
     * @return
     */
    public <T> Map<WorkplaceCounterCategory, Map<GeneralDate, T>> get(List<ScheduleTableAttendanceItem> scheduleTableAttendanceItems, List<WorkplaceCounterCategory> workplaceCounterCategories, Map<ScheRecGettingAtr, List<IntegrationOfDaily>> integrationOfDailyMap, NotUseAtr dailyDataDisplayAtr, DatePeriod period, TargetOrgIdenInfor targetOrg) {
        Map<WorkplaceCounterCategory, Map<GeneralDate, T>> result = new HashMap<>();
        result.putAll(totalQuery.get(targetOrg, workplaceCounterCategories, integrationOfDailyMap, period));
        if (workplaceCounterCategories.contains(WorkplaceCounterCategory.WORKTIME_PEOPLE)) {
            result.put(
                    WorkplaceCounterCategory.WORKTIME_PEOPLE,
                    (Map<GeneralDate, T>) aggregateNumberOfPeopleByWorkQuery.get(
                            targetOrg,
                            period,
                            integrationOfDailyMap.getOrDefault(ScheRecGettingAtr.ONLY_SCHEDULE, new ArrayList<>()),
                            integrationOfDailyMap.getOrDefault(ScheRecGettingAtr.ONLY_RECORD, new ArrayList<>()),
                            scheduleTableAttendanceItems.contains(ScheduleTableAttendanceItem.SHIFT)
                    )
            );
        }
        return result;
    }
}
