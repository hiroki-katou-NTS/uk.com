/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.multimonth;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYM;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTime;

/**
 *
 * @author raiki_asada
 */
@RequiredArgsConstructor
public enum ConditionValueAgree36MultiMonthCheckerByEmployee {
    複数月平均(1, "複数月平均",  averageTime -> {
        return (double)averageTime.getAverageTime().v();
    });

    public final int value;

    /**
     * 項目名
     */
    @Getter
    private final String name;

    private final Function<AgreMaxAverageTime, Double> getValue;

    public List<ValueEachMonth> getValue(Context context) {
        return AggregateAgreementTimeByYM.aggregate(context.require, context.employeeId, GeneralDate.today(), context.yearMonth, new HashMap<>()).getAverageTimes()
                .stream().map(averageTime -> new ValueEachMonth(averageTime.getPeriod(), this.getValue.apply(averageTime)))
                .collect(Collectors.toList());
    }

    public interface Require extends AggregateAgreementTimeByYM.RequireM1 {
    }

    @Value
    public static class Context implements ConditionValueContext {

        private Require require;
        private String employeeId;
        private YearMonth yearMonth;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.AGREE36_MONTHLY;
        }

        @Override
        public String getEmployeeId() {
            return this.employeeId;
        }

        @Override
        public DateInfo getDateInfo() {
            return new DateInfo(this.yearMonth);
        }
    }
    
    @Value
    public static class ValueEachMonth {
	private YearMonthPeriod period;
	private double value;
    }
}
