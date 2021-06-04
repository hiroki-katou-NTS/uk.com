package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;

@Data
/** 積休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class RsvLeaveRemainingNumberDto implements AttendanceItemDataGate, ItemConst {

	/** 合計残日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double totalRemainingDays;

	/** 明細 */
	// @AttendanceItemLayout(jpPropertyName = "明細", layout = "C", listMaxLength = ??)
	private List<CommonlLeaveRemainingDetailDto> details;

	public static RsvLeaveRemainingNumberDto from(ReserveLeaveRemainingNumber domain) {

		List<CommonlLeaveRemainingDetailDto> detailDtoList = new ArrayList<CommonlLeaveRemainingDetailDto>();
		domain.getDetails().forEach(c->{
			detailDtoList.add(CommonlLeaveRemainingDetailDto.from(c));
		});
		return domain == null ? null : new RsvLeaveRemainingNumberDto(
				domain.getTotalRemainingDays().v(), detailDtoList);
	}

	public ReserveLeaveRemainingNumber toReserveDomain() {
		return ReserveLeaveRemainingNumber.of(
				new ReserveLeaveRemainingDayNumber(totalRemainingDays),
				ConvertHelper.mapTo(details, c -> c == null ? null : c.toReserveDomain()));
	}
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(totalRemainingDays).valueType(ValueType.DAYS));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
			return PropType.VALUE;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			totalRemainingDays = value.valueOrDefault(0d); break;
		default:
			break;
		}
	}
}
