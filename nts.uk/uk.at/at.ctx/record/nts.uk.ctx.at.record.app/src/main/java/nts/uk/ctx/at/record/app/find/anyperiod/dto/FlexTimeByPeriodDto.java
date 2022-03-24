package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.FlexTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

import java.util.Optional;


@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別のフレックス時間 */
public class FlexTimeByPeriodDto implements ItemConst, AttendanceItemDataGate {

    /** フレックス時間: フレックス時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
    private int flexTime;

    /** フレックス超過時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = EXCESS + TIME, layout = LAYOUT_B)
    private int flexExcessTime;

    /** フレックス不足時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = SHORTAGE, layout = LAYOUT_C)
    private int flexShortageTime;

    /** 事前フレックス時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_D)
    private int beforeFlexTime;

    public FlexTimeByPeriod toDomain() {
        return FlexTimeByPeriod.of(
                new AttendanceTimeMonthWithMinus(flexTime),
                new AttendanceTimeMonth(flexExcessTime),
                new AttendanceTimeMonth(flexShortageTime),
                new AttendanceTimeMonth(beforeFlexTime)
        );
    }

    public static FlexTimeByPeriodDto from(FlexTimeByPeriod domain) {
        FlexTimeByPeriodDto dto = new FlexTimeByPeriodDto();
        if(domain != null) {
            dto.setFlexTime(domain.getFlexTime() == null ? 0 : domain.getFlexTime().valueAsMinutes());
            dto.setFlexExcessTime(domain.getFlexExcessTime() == null ? 0 : domain.getFlexExcessTime().valueAsMinutes());
            dto.setFlexShortageTime(domain.getFlexShortageTime() == null ? 0 : domain.getFlexShortageTime().valueAsMinutes());
            dto.setBeforeFlexTime(domain.getBeforeFlexTime() == null ? 0 : domain.getBeforeFlexTime().valueAsMinutes());
        }
        return dto;
    }


    @Override
    public Optional<ItemValue> valueOf(String path) {
        switch (path) {
            case TIME:
                return Optional.of(ItemValue.builder().value(flexTime).valueType(ValueType.TIME));
            case (EXCESS + TIME):
                return Optional.of(ItemValue.builder().value(flexExcessTime).valueType(ValueType.TIME));
            case SHORTAGE:
                return Optional.of(ItemValue.builder().value(flexShortageTime).valueType(ValueType.TIME));
            case BEFORE:
                return Optional.of(ItemValue.builder().value(beforeFlexTime).valueType(ValueType.TIME));
            default:
                return Optional.empty();
        }
    }

    @Override
    public PropType typeOf(String path) {
        return PropType.VALUE;
    }

    @Override
    public void set(String path, ItemValue value) {
        switch (path) {
            case TIME:
                flexTime = value.valueOrDefault(0);
                break;
            case (EXCESS + TIME):
                flexExcessTime = value.valueOrDefault(0);
                break;
            case SHORTAGE:
                flexShortageTime = value.valueOrDefault(0);
                break;
            case BEFORE:
                beforeFlexTime = value.valueOrDefault(0);
                break;
            default:
        }
    }

}
