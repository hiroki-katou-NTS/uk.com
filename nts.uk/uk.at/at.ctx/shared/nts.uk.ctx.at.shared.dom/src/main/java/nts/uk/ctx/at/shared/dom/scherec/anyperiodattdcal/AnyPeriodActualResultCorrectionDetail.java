package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import java.util.ArrayList;
import java.util.List;

/**
 * 任意期間実績の修正詳細
 */
@AllArgsConstructor
public class AnyPeriodActualResultCorrectionDetail {
    private AnyPeriodRecordToAttendanceItemConverter converter;

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
    public CalculatedItemDetail getCalculatedItems() {
        List<ItemValue> correctedItems = converter.withAttendanceTime(this.afterCorrection).convert(new ArrayList<>());
        List<ItemValue> calculatedItems = converter.withAttendanceTime(this.afterCalculation).convert(new ArrayList<>());
        List<Integer> calculatedItemIds = new ArrayList<>();
        calculatedItems.forEach(item -> {
            correctedItems.stream().filter(crt -> crt.getItemId() == item.getItemId()).findFirst().ifPresent(i -> {
                if (!i.getValue().equals(item.getValue())) {
                    calculatedItemIds.add(item.getItemId());
                }
            });
        });
        return new CalculatedItemDetail(afterCalculation.getEmployeeId(), calculatedItemIds);
    }
}
