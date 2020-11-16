package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace;

import lombok.val;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.CheckDayItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedAmountValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareRange;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlCompareSingle;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlSingleFixed;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.Optional;

/**
 * @author Thanh.LNP
 */
public class ToCheckConditions {
    public static <V> CheckConditions<V> checkSchedaiExCon(int checkDayItemsType, Optional<KrcstErAlCompareSingle> compareSingle, Optional<KrcstErAlCompareRange> compareRange, Optional<KrcstErAlSingleFixed> singleFixed) {
        if (singleFixed.isPresent() && compareSingle.isPresent()) {
            if (checkDayItemsType == CheckDayItemsType.CONTRAST.value) {
                return setCompareSingleValue((V) new CheckedAmountValue((int) singleFixed.get().fixedValue), compareSingle.get().compareAtr, compareSingle.get().conditionType);
            } else if (checkDayItemsType == CheckDayItemsType.NUMBER_PEOPLE_COMPARISON.value) {
                return setCompareSingleValue((V) new CheckedTimeDuration((int) singleFixed.get().fixedValue), compareSingle.get().compareAtr, compareSingle.get().conditionType);
            } else if (checkDayItemsType == CheckDayItemsType.TIME_COMPARISON.value) {
                return setCompareSingleValue((V) new TimeWithDayAttr((int) singleFixed.get().fixedValue), compareSingle.get().compareAtr, compareSingle.get().conditionType);
            } else if (checkDayItemsType == CheckDayItemsType.AMOUNT_COMPARISON.value) {
                return setCompareSingleValue((V) new CheckedTimesValue((int) singleFixed.get().fixedValue), compareSingle.get().compareAtr, compareSingle.get().conditionType);
            } else if (checkDayItemsType == CheckDayItemsType.RATIO_COMPARISON.value) {
                return setCompareSingleValue((V) new CheckedTimesValueDay(singleFixed.get().fixedValue), compareSingle.get().compareAtr, compareSingle.get().conditionType);
            } else {
                return null;
            }
        } else if (compareRange.isPresent()) {
            if (checkDayItemsType == CheckDayItemsType.CONTRAST.value) {
                return setCompareRange((V) new CheckedAmountValue((int) compareRange.get().startValue), (V) new CheckedAmountValue((int) compareRange.get().endValue), compareRange.get().compareAtr);
            } else if (checkDayItemsType == CheckDayItemsType.NUMBER_PEOPLE_COMPARISON.value) {
                return setCompareRange((V) new CheckedTimeDuration((int) compareRange.get().startValue), (V) new CheckedTimeDuration((int) compareRange.get().endValue), compareRange.get().compareAtr);
            } else if (checkDayItemsType == CheckDayItemsType.TIME_COMPARISON.value) {
                return setCompareRange((V) new TimeWithDayAttr((int) compareRange.get().startValue), (V) new TimeWithDayAttr((int) compareRange.get().endValue), compareRange.get().compareAtr);
            } else if (checkDayItemsType == CheckDayItemsType.AMOUNT_COMPARISON.value) {
                return setCompareRange((V) new CheckedTimesValue((int) compareRange.get().startValue), (V) new CheckedTimesValue((int) compareRange.get().endValue), compareRange.get().compareAtr);
            } else if (checkDayItemsType == CheckDayItemsType.RATIO_COMPARISON.value) {
                return setCompareRange((V) new CheckedTimesValueDay(compareRange.get().startValue), (V) new CheckedTimesValueDay(compareRange.get().endValue), compareRange.get().compareAtr);
            } else {
                return null;
            }
        } else {
            return null;
        }
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
