package nts.uk.ctx.alarm.dom.byemployee.check.aggregate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public interface AggregationRequire {
        List<IntegrationOfDaily> getIntegrationOfDailyProspect(String employeeId, DatePeriod period);
    }
}
