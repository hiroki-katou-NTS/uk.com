package nts.uk.ctx.alarm.dom.byemployee.check.aggregate;

import lombok.Getter;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodWeekly;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonAndWeekStatutoryTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly.MonthlyStatutoryWorkingHours;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.*;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * 日別勤怠を集計する
 */
@Getter
public class AggregateIntegrationOfDaily {
    private final String employeeId;
    private final ClosureMonth closureMonth;
    private final DatePeriod aggregationPeriod;


    public AggregateIntegrationOfDaily(String employeeId, ClosureMonth closureMonth) {
        this.employeeId = employeeId;
        this.closureMonth = closureMonth;
        this.aggregationPeriod = CheckingPeriodMonthly.getDatePeriod(closureMonth);
    }

    public Double aggregate(AggregationRequire require,Function<IntegrationOfDaily, Double> calcOneEmployeeOfDay) {
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDailyProspect(this.employeeId, this.aggregationPeriod)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        return aggregationPeriod.stream()
                .collect(Collectors.summingDouble(date -> calcOneEmployeeOfDay.apply(integrationOfDaily.get(date))
                ));
    }

    public interface AggregationRequire {
        List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period);
    }
}
