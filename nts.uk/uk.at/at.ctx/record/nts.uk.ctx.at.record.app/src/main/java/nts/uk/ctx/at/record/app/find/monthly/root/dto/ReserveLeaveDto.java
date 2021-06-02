package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.record.app.find.monthly.root.common.RsvLeaveRemainingNumberInfoDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
//import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveUsedNumber;

@Data
/** 積立年休 */
@NoArgsConstructor
@AllArgsConstructor
public class ReserveLeaveDto implements ItemConst, AttendanceItemDataGate {

	/** 使用数 */
	@AttendanceItemLayout(jpPropertyName = USAGE, layout = LAYOUT_A)
	private DayUsedNumberDto usedNumber;

	/** 残数 */
	@AttendanceItemLayout(jpPropertyName = REMAIN, layout = LAYOUT_B)
	private RsvLeaveRemainingNumberInfoDto remainingNumber;

	public static ReserveLeaveDto from(ReserveLeave domain) {

		return domain == null ? null : new ReserveLeaveDto(
						DayUsedNumberDto.from(domain.getUsedNumber()),
						RsvLeaveRemainingNumberInfoDto.from(domain.getRemainingNumberInfo()));

	}

	public ReserveLeave toDomain() {

		return ReserveLeave.of(usedNumber == null ? new ReserveLeaveUsedNumber() : usedNumber.toDomain(),
							remainingNumber == null ? new ReserveLeaveRemainingInfo() : remainingNumber.toReserveDomain());
	}

//	public static ReserveLeaveDto from(ReserveLeave domain) {
//		return domain == null ? null : new ReserveLeaveDto(
//						DayUsedNumberDto.from(domain.getUsedNumber()),
//						RsvLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
//						RsvLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
//						RsvLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)),
//						0);
//	}

//	public RealReserveLeave toRealDomain() {
//		return RealReserveLeave.of(
//				usedNumber == null ? new ReserveLeaveUsedNumber() : usedNumber.toDomain(),
//				remainingNumber == null ? new ReserveLeaveRemainingNumber() : remainingNumber.toReserveDomain(),
//				remainingNumberBeforeGrant == null ? new ReserveLeaveRemainingNumber() : remainingNumberBeforeGrant.toReserveDomain(),
//				Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toReserveDomain()));
//	}

//	@Override
//	public Optional<ItemValue> valueOf(String path) {
//		if (NOT_DIGESTION.equals(path)) {
//			return Optional.of(ItemValue.builder().value(undigestedNumber).valueType(ValueType.DAYS));
//		}
//		return AttendanceItemDataGate.super.valueOf(path);
//	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case USAGE:
			return new DayUsedNumberDto();
		case REMAIN:
//		case (GRANT + BEFORE):
//		case (GRANT + AFTER):
			return new RsvLeaveRemainingNumberInfoDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case USAGE:
			return Optional.ofNullable(usedNumber);
		case REMAIN:
			return Optional.ofNullable(remainingNumber);
//		case (GRANT + BEFORE):
//			return Optional.ofNullable(remainingNumberBeforeGrant);
//		case (GRANT + AFTER):
//			return Optional.ofNullable(remainingNumberAfterGrant);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (NOT_DIGESTION.equals(path)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

//	@Override
//	public void set(String path, ItemValue value) {
//		if (NOT_DIGESTION.equals(path)) {
//			undigestedNumber = value.valueOrDefault(0d);
//		}
//	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case USAGE:
			usedNumber = (DayUsedNumberDto) value; break;
		case REMAIN:
			remainingNumber = (RsvLeaveRemainingNumberInfoDto) value; break;
//		case (GRANT + BEFORE):
//			remainingNumberBeforeGrant = (CommonLeaveRemainingNumberDto) value; break;
//		case (GRANT + AFTER):
//			remainingNumberAfterGrant = (CommonLeaveRemainingNumberDto) value; break;
		default:
			break;
		}
	}

	
}
