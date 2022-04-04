package nts.uk.ctx.alarm.dom.byemployee.check.aggregate;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 日別勤怠を集計する
 */
@AllArgsConstructor
public class AggregateIntegrationOfDaily {
    private DatePeriod period;
    private String employeeId;

    public Double aggregate(AggregationRequire require,Function<IntegrationOfDaily, Double> calcOneEmployeeOfDay) {
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDaily(this.period,this.employeeId)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        return period.datesBetween().stream()
            .collect(Collectors.summingDouble(date -> calcOneEmployeeOfDay.apply(integrationOfDaily.get(date))
            ));
    }

    public Double calcRatioValue(AggregationRequire require,
                                 Function<IntegrationOfDaily, Double> calcOneEmployeeOfDayForNumerator,
                                 Function<IntegrationOfDaily, Double> calcOneEmployeeOfDayForDenominator){
        Map<GeneralDate, IntegrationOfDaily> integrationOfDaily = require.getIntegrationOfDaily(this.period,this.employeeId)
                .stream()
                .collect(Collectors.toMap(IntegrationOfDaily::getYmd, v -> v));
        Double numerator = period.datesBetween().stream()
                .collect(Collectors.summingDouble(date ->
                        calcOneEmployeeOfDayForNumerator.apply(integrationOfDaily.get(date))
                ));
        Double denominator = period.datesBetween().stream()
                .collect(Collectors.summingDouble(date ->
                        calcOneEmployeeOfDayForDenominator.apply(integrationOfDaily.get(date))
                ));

        return numerator / denominator;
    }

    public interface AggregationRequire {
        List<IntegrationOfDaily> getIntegrationOfDaily(DatePeriod period, String employeeId);
    }
}
