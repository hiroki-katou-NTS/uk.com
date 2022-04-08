package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutSideWorkEachBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWork;

@Data
/** 時間外超過 */
@NoArgsConstructor
public class ExcessOutsideWorkDto implements ItemConst, AttendanceItemDataGate {
    /** 内訳NO: int */
    private int no;

    /** 超過時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
    private int excessTime;

    public ExcessOutsideWorkDto(int breakdownNo, Integer excessTime) {
        super();
        this.no = breakdownNo;
        this.excessTime = excessTime == null ? 0 : excessTime;
    }

    public ExcessOutsideItemByPeriod toDomain() {
        return ExcessOutsideItemByPeriod.of(this.no, new AttendanceTimeMonth(this.excessTime));
    }

    public static ExcessOutsideWorkDto from(ExcessOutsideItemByPeriod domain) {
        return new ExcessOutsideWorkDto(
                domain.getBreakdownNo(),
                domain.getExcessTime() == null ? 0 : domain.getExcessTime().valueAsMinutes()
        );
    }

    @Override
    public Optional<ItemValue> valueOf(String path) {
        if (TIME.equals(path)) {
            return Optional.of(ItemValue.builder().value(excessTime).valueType(ValueType.TIME));
        }
        return AttendanceItemDataGate.super.valueOf(path);
    }

    @Override
    public PropType typeOf(String path) {
        if (TIME.equals(path)) {
            return PropType.VALUE;
        }
        return AttendanceItemDataGate.super.typeOf(path);
    }

    @Override
    public void set(String path, ItemValue value) {
        if (TIME.equals(path)) {
            excessTime = value.valueOrDefault(0);
        }
    }

}
