package nts.uk.ctx.at.record.app.find.anyperiod.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AnyItemByPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AggregateAnyItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 期間別の任意項目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnyItemByPeriodDto implements ItemConst, AttendanceItemDataGate {

    /** 任意項目値: 集計任意項目 */
    @AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = OPTIONAL_ITEM_VALUE, listMaxLength = 200, indexField = DEFAULT_INDEX_FIELD_NAME)
    private List<OptionalItemValueDto> anyItemValues = new ArrayList<>();

    public AnyItemByPeriod toDomain() {
        return AnyItemByPeriod.of(anyItemValues.stream().map(i -> AggregateAnyItem.of(
                i.getNo(),
                Optional.ofNullable(i.getMonthlyTime()),
                Optional.ofNullable(i.getMonthlyTimes()),
                Optional.ofNullable(i.getMonthlyAmount())
        )).collect(Collectors.toList()));
    }

    public static AnyItemByPeriodDto fromDomain(AnyItemByPeriod domain) {
        AnyItemByPeriodDto dto = new AnyItemByPeriodDto();
        domain.getAnyItemValues().values().forEach(v -> {
            if (v.getTime().isPresent()) {
                dto.anyItemValues.add(new OptionalItemValueDto(v.getTime().get().toString(), v.getAnyItemNo(), OptionalItemAtr.TIME));
            } else if (v.getTimes().isPresent()) {
                dto.anyItemValues.add(new OptionalItemValueDto(v.getTimes().get().toString(), v.getAnyItemNo(), OptionalItemAtr.NUMBER));
            } else if (v.getAmount().isPresent()) {
                dto.anyItemValues.add(new OptionalItemValueDto(v.getAmount().get().toString(), v.getAnyItemNo(), OptionalItemAtr.AMOUNT));
            }
        });
        return dto;
    }

    @Override
    public AttendanceItemDataGate newInstanceOf(String path) {
        if (OPTIONAL_ITEM_VALUE.equals(path)) {
            return new OptionalItemValueDto();
        }
        return AttendanceItemDataGate.super.newInstanceOf(path);
    }

    @Override
    public int size(String path) {
        if (OPTIONAL_ITEM_VALUE.equals(path)) {
            return 200;
        }
        return AttendanceItemDataGate.super.size(path);
    }

    @Override
    public PropType typeOf(String path) {
        if (OPTIONAL_ITEM_VALUE.equals(path)) {
            return PropType.IDX_LIST;
        }
        return AttendanceItemDataGate.super.typeOf(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AttendanceItemDataGate> List<T> gets(String path) {
        if (OPTIONAL_ITEM_VALUE.equals(path)) {
            return (List<T>) anyItemValues;
        }
        return AttendanceItemDataGate.super.gets(path);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
        if (OPTIONAL_ITEM_VALUE.equals(path)) {
            anyItemValues = (List<OptionalItemValueDto>) value;
        }
    }
}
