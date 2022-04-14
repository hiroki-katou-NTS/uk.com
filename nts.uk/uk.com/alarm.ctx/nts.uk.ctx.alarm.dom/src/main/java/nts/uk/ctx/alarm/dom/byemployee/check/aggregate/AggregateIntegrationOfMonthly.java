package nts.uk.ctx.alarm.dom.byemployee.check.aggregate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.shr.com.time.closure.ClosureMonth;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 月別勤怠を集計する
 */
@Getter
public class AggregateIntegrationOfMonthly {
    private final String employeeId;
    private final List<ClosureMonth> closureMonths;

    public AggregateIntegrationOfMonthly(String employeeId, List<ClosureMonth> closureMonth) {
        this.employeeId = employeeId;
        this.closureMonths = closureMonth;
    }

    public Double aggregate(AggregationRequire require,Function<IntegrationOfMonthly, Double> calcOneEmployeeOfDay) {
        List<IntegrationOfMonthly> integrationOfMonthly = require.getIntegrationOfMonthlyProspect(this.employeeId, this.closureMonths);
        return integrationOfMonthly.stream()
            .collect(Collectors.summingDouble(data -> calcOneEmployeeOfDay.apply(data)
            ));
    }

    public Double calcRatioValue(AggregationRequire require,
                                 Function<IntegrationOfMonthly, Double> calcOneEmployeeOfDayForNumerator,
                                 Function<IntegrationOfMonthly, Double> calcOneEmployeeOfDayForDenominator){
        List<IntegrationOfMonthly> integrationOfMonthly =
            require.getIntegrationOfMonthlyProspect(this.employeeId, this.closureMonths);
        Double numerator = integrationOfMonthly.stream()
                .collect(Collectors.summingDouble(data ->
                        calcOneEmployeeOfDayForNumerator.apply(data)
                ));
        Double denominator = integrationOfMonthly.stream()
                .collect(Collectors.summingDouble(data ->
                        calcOneEmployeeOfDayForDenominator.apply(data)
                ));

        return numerator / denominator;
    }

    public interface AggregationRequire {
        List<IntegrationOfMonthly> getIntegrationOfMonthlyProspect(String employeeId, List<ClosureMonth> closureMonths);
    }
}
