package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AgreementTimeByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の36協定時間 */
public class AgreementTimeByPeriodDto implements ItemConst, AttendanceItemDataGate {

    /** 36協定時間: 勤怠月間時間 */
    @AttendanceItemValue(type = ValueType.TIME)
    @AttendanceItemLayout(jpPropertyName = AGREEMENT + TIME, layout = LAYOUT_A)
    private int agreementTime;

    public AgreementTimeByPeriod toDomain() {
        return AgreementTimeByPeriod.of(new AttendanceTimeMonth(agreementTime));
    }

    public static AgreementTimeByPeriodDto from(AgreementTimeByPeriod domain) {
        AgreementTimeByPeriodDto dto = new AgreementTimeByPeriodDto();
        if(domain != null) {
            dto.setAgreementTime(domain.getAgreementTime() == null ? 0 : domain.getAgreementTime().valueAsMinutes());
        }
        return dto;
    }

    @Override
    public Optional<ItemValue> valueOf(String path) {
        switch (path) {
            case AGREEMENT + TIME:
                return Optional.of(ItemValue.builder().value(agreementTime).valueType(ValueType.TIME));
            default:
                return AttendanceItemDataGate.super.valueOf(path);
        }
    }

    @Override
    public PropType typeOf(String path) {
        switch (path) {
            case AGREEMENT + TIME:
                return PropType.VALUE;
            default:
                return AttendanceItemDataGate.super.typeOf(path);
        }
    }

    @Override
    public void set(String path, ItemValue value) {
        switch (path) {
            case AGREEMENT + TIME:
                agreementTime = value.valueOrDefault(0);
                break;
            default:
        }
    }
}
