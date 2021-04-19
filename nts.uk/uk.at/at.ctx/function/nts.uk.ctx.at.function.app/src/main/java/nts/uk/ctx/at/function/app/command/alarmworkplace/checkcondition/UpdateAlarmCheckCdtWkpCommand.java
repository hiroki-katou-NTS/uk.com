package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command.ExtractionOptionalItemsCmd;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageNumberTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.primitivevalue.AverageTime;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.CheckDayItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.primitivevalue.*;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UpdateAlarmCheckCdtWkpCommand {

    private AlarmCheckWkpCommand alarmCheck;

    private List<AlarmExConditionCommand> alarmCheckCon;

    private List<ExtractionOptionalItemsCmd> opItems;

    public List<ExtractionMonthlyCon> toDomainMon() {
        List<ExtractionMonthlyCon> result = new ArrayList<>();

        if (!this.opItems.isEmpty()) {

            result = this.opItems.stream().map(i -> {
                return ExtractionMonthlyCon.create(
                        IdentifierUtil.randomUniqueId(),
                        i.getNo(),
                        i.getCheckItem(),
                        i.getUseAtr() == 1,
                        IdentifierUtil.randomUniqueId(),
                        this.convertConditionMon(i.getCheckItem(), i.getMinValue() == null ? "0" : i.getMinValue(), i.getMaxValue(), i.getOperator()),
                        i.getAdditionAttendanceItems(),
                        i.getSubstractionAttendanceItems(),
                        i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_RATIO.value ? i.getCheckCondB() : null,
                        i.getName(),
                        i.getMessage()
                );
            }).collect(Collectors.toList());
        }

        return result;
    }

    public List<ExtractionScheduleCon> toDomainSchedule() {
        List<ExtractionScheduleCon> result = new ArrayList<>();

        if (!this.opItems.isEmpty()) {

            result = this.opItems.stream().map(i -> {
                return ExtractionScheduleCon.create(
                        IdentifierUtil.randomUniqueId(),
                        i.getNo(),
                        i.getCheckItem(),
                        i.getUseAtr() == 1,
                        IdentifierUtil.randomUniqueId(),
                        this.convertConditionSchel(i.getCheckItem(), i.getMinValue() == null ? "0" : i.getMinValue() , i.getMaxValue(), i.getOperator()),
                        i.getCheckItem() != CheckDayItemsType.CONTRAST.value ? i.getCheckCond() : null,
                        i.getCheckItem() == CheckDayItemsType.CONTRAST.value ? i.getCheckCondB() : null,
                        i.getName(),
                        i.getMessage()
                );
            }).collect(Collectors.toList());
        }

        return result;
    }

    private <V> CheckConditions<V> convertConditionSchel(int checkItem, String minValue, String maxValue, int operator) {
        CheckConditions result = null;
        CheckDayItemsType check = EnumAdaptor.valueOf(checkItem, CheckDayItemsType.class);
        switch (check) {
            case CONTRAST: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new Comparison(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new Comparison(Integer.valueOf(minValue)), (V) new Comparison(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case NUMBER_PEOPLE_COMPARISON: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new NumberOfPeople(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new NumberOfPeople(Integer.valueOf(minValue)), (V) new NumberOfPeople(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case TIME_COMPARISON: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new Time(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new Time(Integer.valueOf(minValue)), (V) new Time(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case AMOUNT_COMPARISON: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new Amount(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new Amount(Integer.valueOf(minValue)), (V) new Amount(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case RATIO_COMPARISON: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new RatioComparison(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new RatioComparison(Integer.valueOf(minValue)), (V) new RatioComparison(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
        }
        return result;
    }

    private <V> CheckConditions<V> convertConditionMon(int checkItem, String minValue, String maxValue, int operator) {
        CheckConditions result = null;
        CheckMonthlyItemsType check = EnumAdaptor.valueOf(checkItem, CheckMonthlyItemsType.class);
        switch (check) {
            case AVERAGE_TIME: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new AverageTime(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new AverageTime(Integer.valueOf(minValue)), (V) new AverageTime(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case AVERAGE_NUMBER_DAY: {
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new AverageNumberDays(new BigDecimal(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new AverageNumberDays(new BigDecimal(minValue)), (V) new AverageNumberDays(new BigDecimal(maxValue)), operator);
                }
                break;
            }
            case AVERAGE_NUMBER_TIME:{
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new AverageNumberTimes(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new AverageNumberTimes(Integer.valueOf(minValue)), (V) new AverageNumberTimes(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
            case AVERAGE_RATIO:{
                if (maxValue == null) {
                    result = setCompareSingleValue((V) new AverageRatio(Integer.valueOf(minValue)), operator, 0);
                } else {
                    result = setCompareRange((V) new AverageRatio(Integer.valueOf(minValue)), (V) new AverageRatio(Integer.valueOf(maxValue)), operator);
                }
                break;
            }
        }
        return result;
    }

    private static <V> CompareRange<V> setCompareRange(V startValue, V endValue, int compareOperator) {
        val compareRange = new CompareRange<V>(compareOperator);
        compareRange.setValue(startValue, endValue);
        return compareRange;
    }

    private static <V> CompareSingleValue<V> setCompareSingleValue(V value, int compareOpertor, int conditionType) {
        val compareSingleValue = new CompareSingleValue<V>(compareOpertor, conditionType);
        compareSingleValue.setValue(value);
        return compareSingleValue;
    }


}
