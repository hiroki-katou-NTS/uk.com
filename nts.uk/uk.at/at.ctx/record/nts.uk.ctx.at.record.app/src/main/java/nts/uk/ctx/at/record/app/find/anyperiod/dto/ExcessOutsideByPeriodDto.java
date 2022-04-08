package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.ExcessOutsideItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.SuperHD60HConTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@AllArgsConstructor
/** 期間別の時間外超過 */
public class ExcessOutsideByPeriodDto implements ItemConst, AttendanceItemDataGate {

    /** 時間: 時間外超過 */
    @AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
    private List<ExcessOutsideWorkDto> excessOutsideTimes = new ArrayList<>();

    public ExcessOutsideByPeriod toDomain() {
        return ExcessOutsideByPeriod.of(this.excessOutsideTimes.stream().map(ExcessOutsideWorkDto::toDomain).collect(Collectors.toList()));
    }

    public static ExcessOutsideByPeriodDto from(ExcessOutsideByPeriod domain) {
        ExcessOutsideByPeriodDto dto = new ExcessOutsideByPeriodDto();
        if(domain != null) {
            dto.excessOutsideTimes = domain.getExcessOutsideItems().values().stream().map(ExcessOutsideWorkDto::from).collect(Collectors.toList());
        }
        return dto;
    }

    @Override
    public AttendanceItemDataGate newInstanceOf(String path) {
        if (TIME.equals(path)) {
            return new ExcessOutsideWorkDto();
        }
        return AttendanceItemDataGate.super.newInstanceOf(path);
    }

    @Override
    public PropType typeOf(String path) {
        switch (path) {
            case TIME:
                return PropType.IDX_LIST;
            default:
                return AttendanceItemDataGate.super.typeOf(path);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AttendanceItemDataGate> List<T> gets(String path) {
        if (TIME.equals(path)){
            return (List<T>) excessOutsideTimes;
        }
        return AttendanceItemDataGate.super.gets(path);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
        if (TIME.equals(path)){
            excessOutsideTimes = (List<ExcessOutsideWorkDto>) value;
        }
    }

    @Override
    public int size(String path) {
        if (TIME.equals(path)){
            return 10;
        }
        return AttendanceItemDataGate.super.size(path);
    }


}