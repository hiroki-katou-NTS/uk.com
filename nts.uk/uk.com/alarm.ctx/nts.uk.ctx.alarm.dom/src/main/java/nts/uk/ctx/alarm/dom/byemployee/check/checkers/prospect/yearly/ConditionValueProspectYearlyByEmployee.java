package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.yearly;

import static java.util.Comparator.comparing;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfMonthly;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;

import java.util.function.Function;
import java.util.stream.Collectors;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.aggregate.AggregateIntegrationOfDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.AbsenceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.AttendanceDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.HolidayWorkDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.HolidaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.SpecialVacationDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.WorkDaysProspector;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.worktype.WorkTypeCountProspectorBase;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * 条件値チェック（社員別・見込み年次）
 */
@RequiredArgsConstructor
public enum ConditionValueProspectYearlyByEmployee implements ConditionValueLogic<ConditionValueProspectYearlyByEmployee.Context> {
    支給額(1, "（予定時間＋総労働時間）×基準単価", c -> {
        return aggregateOld(c,
            data -> {
                // 就業時間金額
                double withinWorkTimeAmount = data.getAttendanceTime()
                        .get()
                        .getVerticalTotal()
                        .getWorkAmount()
                        .getWorkTimeAmount()
                        .v();
                // 割増時間金額
                double totalAmount = data.getAttendanceTime()
                        .get()
                        .getVerticalTotal()
                        .getWorkTime()
                        .getPremiumTime()
                        .getPremiumAmountTotal()
                        .v();
                return withinWorkTimeAmount + totalAmount;
            },
            aggregator -> {
                return aggregator.aggregate(c.require, data -> {
                    // 就業時間金額
                    double withinWorkTimeAmount = data.getAttendanceTimeOfDailyPerformance()
                            .get()
                            .getActualWorkingTimeOfDaily()
                            .getTotalWorkingTime()
                            .getWithinStatutoryTimeOfDaily()
                            .getWithinWorkTimeAmount()
                            .v();
                    // 割増時間金額
                    double totalAmount = data.getAttendanceTimeOfDailyPerformance()
                            .get()
                            .getActualWorkingTimeOfDaily()
                            .getPremiumTimeOfDailyPerformance()
                            .getTotalAmount()
                            .v();

                    return withinWorkTimeAmount + totalAmount;
                });
            }
        );
    }),
    
    総労働時間(1, "予定時間＋総労働時間", c -> {
        return aggregateOld(c,
            (data) ->
                // 総労働時間
                 data.getAttendanceTime()
                        .get()
                        .getVerticalTotal()
                        .getWorkAmount()
                        .getWorkTimeAmount()
                        .v().doubleValue()
            ,
            (aggregator) ->
                aggregator.aggregate(c.require, data ->
                    data.getAttendanceTimeOfDailyPerformance()
                            .get()
                            .getActualWorkingTimeOfDaily()
                            .getTotalWorkingTime()
                            .getTotalTime()
                            .v().doubleValue())
        );
    }),
    
    勤務日数(5, "日数：予定勤務日数＋勤務日数", c -> {
        return aggregateOld(c,
                (iom) ->  getWorkDays(iom, (workDays) -> workDays.getWorkDays().getDays().v()),
                (aggregator) -> {
                    WorkDaysProspector prospector = new WorkDaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });
    }),
    
    出勤日数(2, "日数：出勤日数", c -> {
        return aggregateOld(c,
                (iom) -> getWorkDays(iom, (workDays) -> workDays.getAttendanceDays().getDays().v()),
                (aggregator) -> {
                    AttendanceDaysProspector prospector = new AttendanceDaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });

    }),
    休日日数(2, "日数：休日日数", c -> {
        return aggregateOld(c,
                (iom) ->  getWorkDays(iom, (workDays) -> workDays.getHolidayDays().getDays().v()),
                (aggregator) -> {
                    HolidaysProspector prospector = new HolidaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });
    }),
    
    
    休出日数(3, "日数：休出日数", c -> {
        return aggregateOld(c,
                (iom) ->  getWorkDays(iom, (workDays) -> workDays.getHolidayWorkDays().getDays().v()),
                (aggregator) -> {
                    HolidayWorkDaysProspector prospector = new HolidayWorkDaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });
    }),
    
    公休日数(3, "日数：公休日数", c -> {
        return aggregate(c, (context) -> {
            return aggregateIntegrationOfMontly(context, (iom) -> iom.getPublicHolidayLeaveRemain().map(pubHoliday -> pubHoliday.getPublicHolidayday().v()).orElse(0.0));
        }, (context) -> {
            return 0.0;
        });
    }),
    
    特休日数合計(4, "日数：特休日数合計", c -> {
        return aggregateOld(c,
                (iom) ->  getWorkDays(iom, (workDays) -> workDays.getSpecialVacationDays().getTotalSpcVacationDays().v()),
                (aggregator) -> {
                    SpecialVacationDaysProspector prospector = new SpecialVacationDaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });
    }),
    
    欠勤日数合計(4, "日数：欠勤日数合計", c -> {
        return aggregateOld(c,
                (iom) ->  getWorkDays(iom, (workDays) -> workDays.getAbsenceDays().getTotalAbsenceDays().v()),
                (aggregator) -> {
                    AbsenceDaysProspector prospector = new AbsenceDaysProspector(c.require, c.companyId, aggregator);
                    return prospector.prospect(c.require, c.companyId, c.getEmployeeId());
                });
    }),
    
    年休使用数(3, "日数：年休使用数", c -> {
        return aggregateOld(c, (iom) -> {
            return 0.0;
        }, (aggregator) -> {
            return 0.0;
        });
    }),
   
    積立年休使用数(3, "日数：積立年休使用数", c -> {
        return aggregateOld(c, (iom) -> {
            return 0.0;
        }, (aggregator) -> {
            return 0.0;
        });
    }),
    ;
    public final int value;

    /**
     * 項目名
     */
    @Getter
    private final String name;
    private final Function<Context, Double> getValue;

    private static double aggregate(
            Context context,
            Function<Context, Double> closedFunction,
            Function<Context, Double> prospectFunction) {
        
        return closedFunction.apply(context) + prospectFunction.apply(context);
    }
    
    private static double aggregateIntegrationOfMontly(Context context, Function<IntegrationOfMonthly, Double> closedFunction) {
        return context.getClosedAggregator().aggregate(context.require, iom -> closedFunction.apply(iom));
    }
    
    private static double aggregateOld(
            Context context,
            Function<IntegrationOfMonthly, Double> closedFunction,
            Function<AggregateIntegrationOfDaily, Double> prospectFunction) {

        double closedRecord = context.getClosedAggregator().aggregate(context.require, iom -> closedFunction.apply(iom));
        double prospect = context.getProspectAggregator().stream().mapToDouble(aggregator -> {
            return prospectFunction.apply(aggregator);
        }).sum();

        return closedRecord + prospect;
    }

    private static double getWorkDays(IntegrationOfMonthly iom, Function<WorkDaysOfMonthly, Double> function) {
        return iom.getAttendanceTime()
                .map(attendanceTime -> function.apply(attendanceTime.getVerticalTotal().getWorkDays()))
                .orElse(0.0);
    }

    @Override
    public Double getValue(Context context) {
        return getValue.apply(context);
    }

    public interface Require extends
            AggregateIntegrationOfMonthly.AggregationRequire,
            AggregateIntegrationOfDaily.AggregationRequire,
            WorkTypeCountProspectorBase.RequireOfCreate,
            AttendanceDaysProspector.Require,
            HolidaysProspector.Require,
            HolidayWorkDaysProspector.Require,
            SpecialVacationDaysProspector.Require,
            AbsenceDaysProspector.Require,
            WorkDaysProspector.Require{
    }

    @Value
    public static class Context implements ConditionValueContext {

        Require require;

        String companyId;

        String employeeId;

        List<ClosureMonth> period;

        @Override
        public AlarmListCategoryByEmployee getCategory() {
            return AlarmListCategoryByEmployee.PROSPECT_YEARLY;
        }

        @Override
        public String getEmployeeId() {
            return employeeId;
        }

        @Override
        public DateInfo getDateInfo() {
            YearMonth start = this.period.get(0).getYearMonth();
            YearMonth end = this.period.get(this.period.size() - 1).getYearMonth();
            return new DateInfo(new YearMonthPeriod(start, end));
        }

        public AggregateIntegrationOfMonthly getClosedAggregator() {
            // TODO: 当月より前のものだけにする
            List<ClosureMonth> closedMonths = null;
            return new AggregateIntegrationOfMonthly(this.employeeId, closedMonths);
        }

        private List<ClosureMonth> getProspectMonth() {
            // TODO: 当月以降のものだけにする
            List<ClosureMonth> prospectMonths = null;
            return prospectMonths;
        }
        
        private DatePeriod getProspectPeriod() {
            ClosureMonth start = this.getProspectMonth().stream().min(comparing(ClosureMonth::getYearMonth)).get();
            ClosureMonth end = this.getProspectMonth().stream().max(comparing(ClosureMonth::getYearMonth)).get();
            return new DatePeriod(start.defaultPeriod().start(), end.defaultPeriod().end());
        }
        
        public List<AggregateIntegrationOfDaily> getProspectAggregator() {
            return this.getProspectMonth().stream().map(month -> new AggregateIntegrationOfDaily(this.employeeId, month))
                    .collect(Collectors.toList());
        }
    }
}
