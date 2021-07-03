package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AggregateTotalTimeSpentAtWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.AggregateTotalWorkingTimeDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.RegularAndIrregularTimeOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.MonthlyCalculationByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.TotalWorkingTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の月の計算 */
public class MonthlyCalculationByPeriodDto implements ItemConst, AttendanceItemDataGate {

    /** 集計時間: 期間別の総労働時間 */
    @AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_A)
    private AggregateTotalWorkingTimeDto aggregateTime = new AggregateTotalWorkingTimeDto();

    /** フレックス時間: 期間別のフレックス時間 */
    @AttendanceItemLayout(jpPropertyName = FLEX, layout = LAYOUT_B)
    private FlexTimeByPeriodDto flexTime = new FlexTimeByPeriodDto();

    /** 総労働時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = TOTAL_LABOR, layout = LAYOUT_C)
    private int totalWorkingTime;

    /** 総拘束時間: 期間別の総拘束時間 */
    @AttendanceItemLayout(jpPropertyName = RESTRAINT, layout = LAYOUT_D)
    private AggregateTotalTimeSpentAtWorkDto totalTimeSpentAtWork = new AggregateTotalTimeSpentAtWorkDto();

    public MonthlyCalculationByPeriod toDomain() {
        return MonthlyCalculationByPeriod.of(
                TotalWorkingTimeByPeriod.of(
                        aggregateTime.getWorkTime().toDomain(),
                        aggregateTime.getOverTime().toDomain(),
                        aggregateTime.getHolidayWorkTime().toDomain(),
                        aggregateTime.getVacationUseTime().toDomain(),
                        aggregateTime.getPrescribedWorkingTime().toDomain()),
                flexTime.toDomain(),
                new AttendanceTimeMonth(totalWorkingTime),
                totalTimeSpentAtWork.toDomain()
        );
    }

    public static MonthlyCalculationByPeriodDto from(MonthlyCalculationByPeriod domain) {
        MonthlyCalculationByPeriodDto dto = new MonthlyCalculationByPeriodDto();
        if(domain != null) {
            dto.setFlexTime(FlexTimeByPeriodDto.from(domain.getFlexTime()));
            dto.setAggregateTime(AggregateTotalWorkingTimeDto.from(AggregateTotalWorkingTime.of(
                    domain.getAggregateTime().getWorkTime(),
                    domain.getAggregateTime().getOverTime(),
                    domain.getAggregateTime().getHolidayWorkTime(),
                    domain.getAggregateTime().getVacationUseTime(),
                    domain.getAggregateTime().getPrescribedWorkingTime()
            )));
            dto.setTotalTimeSpentAtWork(AggregateTotalTimeSpentAtWorkDto.from(domain.getTotalSpentTime()));
            dto.setTotalWorkingTime(domain.getTotalWorkingTime() == null ? 0 : domain.getTotalWorkingTime().valueAsMinutes());
        }
        return dto;
    }

    @Override
    public Optional<ItemValue> valueOf(String path) {
        switch (path) {
            case TOTAL_LABOR:
                return Optional.of(ItemValue.builder().value(totalWorkingTime).valueType(ValueType.TIME));
            default:
                return AttendanceItemDataGate.super.valueOf(path);
        }
    }

    @Override
    public AttendanceItemDataGate newInstanceOf(String path) {
        switch (path) {
            case FLEX:
                return new FlexTimeByPeriodDto();
            case AGGREGATE:
                return new AggregateTotalWorkingTimeDto();
            case RESTRAINT:
                return new AggregateTotalTimeSpentAtWorkDto();
            default:
                return null;
        }
    }

    @Override
    public Optional<AttendanceItemDataGate> get(String path) {
        switch (path) {
            case FLEX:
                return Optional.ofNullable(flexTime);
            case AGGREGATE:
                return Optional.ofNullable(aggregateTime);
            case RESTRAINT:
                return Optional.ofNullable(totalTimeSpentAtWork);
            default:
                return Optional.empty();
        }
    }

    @Override
    public PropType typeOf(String path) {
        switch (path) {
            case TOTAL_LABOR:
                return PropType.VALUE;
            default:
                return AttendanceItemDataGate.super.typeOf(path);
        }
    }

    @Override
    public void set(String path, ItemValue value) {
        switch (path) {
            case TOTAL_LABOR:
                totalWorkingTime = value.valueOrDefault(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void set(String path, AttendanceItemDataGate value) {
        switch (path) {
            case FLEX:
                flexTime = ( FlexTimeByPeriodDto) value;
                break;
            case AGGREGATE:
                aggregateTime= ( AggregateTotalWorkingTimeDto) value;
                break;
            case RESTRAINT:
                totalTimeSpentAtWork = ( AggregateTotalTimeSpentAtWorkDto) value;
                break;
            default:
        }
    }
}
