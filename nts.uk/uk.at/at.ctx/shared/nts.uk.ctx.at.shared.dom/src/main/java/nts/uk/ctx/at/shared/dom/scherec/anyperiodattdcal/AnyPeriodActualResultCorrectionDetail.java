package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 任意期間実績の修正詳細
 */
@AllArgsConstructor
public class AnyPeriodActualResultCorrectionDetail {
    /** 修正後の勤怠時間 */
    @Getter
    private AttendanceTimeOfAnyPeriod afterCorrection;

    /** 計算後の勤怠時間 */
    @Getter
    private AttendanceTimeOfAnyPeriod afterCalculation;

    /**
     * [1] 計算項目を判定する
     * @return
     */
    public CalculatedItemDetail getCalculatedItems(AnyPeriodRecordToAttendanceItemConverter converter) {
        List<Integer> itemIds = AttendanceItemIdContainer.getIds(AttendanceItemUtil.AttendanceItemType.ANY_PERIOD_ITEM)
                .stream().map(ItemValue::getItemId).collect(Collectors.toList());
        List<ItemValue> correctedItems = converter.withAttendanceTime(this.afterCorrection).convert(itemIds);
        List<ItemValue> calculatedItems = converter.withAttendanceTime(this.afterCalculation).convert(itemIds);
        List<Integer> calculatedItemIds = new ArrayList<>();
        calculatedItems.forEach(item -> {
            correctedItems.stream().filter(crt -> crt.getItemId() == item.getItemId()).findFirst()
                    .ifPresent(i -> {
                        if (StringUtils.compare(i.getValue(), item.getValue()) != 0) {
                            calculatedItemIds.add(item.getItemId());
                        }
                    });
        });
        return new CalculatedItemDetail(afterCalculation.getEmployeeId(), calculatedItemIds);
    }
}
