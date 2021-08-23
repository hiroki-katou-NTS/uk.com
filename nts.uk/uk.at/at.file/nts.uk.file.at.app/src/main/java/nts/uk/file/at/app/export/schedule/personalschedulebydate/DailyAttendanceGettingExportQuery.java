package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 予定・実績を取得する
 */
@Stateless
public class DailyAttendanceGettingExportQuery {

    @Inject
    private IntegrationOfDailyGetter integrationOfDailyGetter;

    public Map<ScheRecGettingAtr, List<IntegrationOfDaily>> get(List<String> sortedEmployeeIds, DatePeriod period, boolean isDisplayActual) {
        val require = new RequireImpl(integrationOfDailyGetter);
        return DailyAttendanceGettingService.get(
                require,
                sortedEmployeeIds.stream().map(EmployeeId::new).collect(Collectors.toList()),
                period,
//                ・if 実績も取得するか == false の時に => 取得対象＝予定のみ
//                ・if 実績も取得するか == trueの時に => 取得対象＝予定+実績
                isDisplayActual ? ScheRecGettingAtr.SCHEDULE_WITH_RECORD : ScheRecGettingAtr.ONLY_SCHEDULE
        );
    }

    @AllArgsConstructor
    public static class RequireImpl implements DailyAttendanceGettingService.Require {
        @Inject
        private IntegrationOfDailyGetter integrationOfDailyGetter;

        @Override
        public List<IntegrationOfDaily> getSchduleList(List<EmployeeId> empIds, DatePeriod period) {
            List<IntegrationOfDaily> result = new ArrayList<>();
            empIds.forEach(sid -> {
                List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                result.addAll(tmp);
            });
            return result;
        }

        @Override
        public List<IntegrationOfDaily> getRecordList(List<EmployeeId> empIds, DatePeriod period) {
            List<IntegrationOfDaily> result = new ArrayList<>();
            empIds.forEach(sid -> {
                List<IntegrationOfDaily> tmp = integrationOfDailyGetter.getIntegrationOfDaily(sid.v(), period);
                result.addAll(tmp);
            });
            return result;
        }
    }
}
