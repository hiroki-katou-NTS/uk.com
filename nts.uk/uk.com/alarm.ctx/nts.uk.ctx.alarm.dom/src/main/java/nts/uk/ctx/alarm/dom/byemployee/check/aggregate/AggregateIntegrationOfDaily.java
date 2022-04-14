package nts.uk.ctx.alarm.dom.byemployee.check.aggregate;

import lombok.Getter;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
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

/**
 * 日別勤怠を集計する
 */
@Getter
public class AggregateIntegrationOfDaily {
    private final String employeeId;
    private final DatePeriod aggregationPeriod;

    public AggregateIntegrationOfDaily(String employeeId, ClosureMonth closureMonth) {
        this.employeeId = employeeId;

        // TODO: 本当にこれでいいのか？
        this.aggregationPeriod = closureMonth.defaultPeriod();
    }

    public Double aggregate(AggregationRequire require,Function<IntegrationOfDaily, Double> calcOneEmployeeOfDay) {
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDailyProspect(this.employeeId, this.aggregationPeriod)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        return aggregationPeriod.stream()
            .collect(Collectors.summingDouble(date -> calcOneEmployeeOfDay.apply(integrationOfDaily.get(date))
            ));
    }

    public Double calcRatioValue(AggregationRequire require,
                                 Function<IntegrationOfDaily, Double> calcOneEmployeeOfDayForNumerator,
                                 Function<IntegrationOfDaily, Double> calcOneEmployeeOfDayForDenominator){
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDailyProspect(this.employeeId, this.aggregationPeriod)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        Double numerator = aggregationPeriod.stream()
                .collect(Collectors.summingDouble(date ->
                        calcOneEmployeeOfDayForNumerator.apply(integrationOfDaily.get(date))
                ));
        Double denominator = aggregationPeriod.stream()
                .collect(Collectors.summingDouble(date ->
                        calcOneEmployeeOfDayForDenominator.apply(integrationOfDaily.get(date))
                ));

        return numerator / denominator;
    }

    /**
     * 分母を日別勤怠以外から取得
     * @param require
     * @param calcOneEmployeeOfDayForNumerator
     * @param calcOneEmployeeForDenominator
     * @return
     */
    public Double calcRatioValueDenominatorFromOther(AggregationRequire require,
                     Function<IntegrationOfDaily, Double> calcOneEmployeeOfDayForNumerator,
                     BiFunction<String, DatePeriod, Double> calcOneEmployeeForDenominator){
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDailyProspect(this.employeeId, this.aggregationPeriod)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        Double numerator = aggregationPeriod.stream()
                .collect(Collectors.summingDouble(date ->
                        calcOneEmployeeOfDayForNumerator.apply(integrationOfDaily.get(date))
                ));
        Double denominator = calcOneEmployeeForDenominator.apply(employeeId, aggregationPeriod);

        return numerator / denominator;
    }

    public interface AggregationRequire {
        List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period);
//        Double flexMonAndWeekStatutoryTime(WorkingSystem workingSystem);
//        Double monAndWeekStatutoryTime(WorkingSystem workingSystem);
    }
}
