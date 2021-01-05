package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonLeaveRemainingNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveUndigestedNumber;
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
	private CommonLeaveRemainingNumberDto remainingNumber;

	/** 残数付与前 */
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_C)
	private CommonLeaveRemainingNumberDto remainingNumberBeforeGrant;

	/** 残数付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_D)
	private CommonLeaveRemainingNumberDto remainingNumberAfterGrant;

	/** 未消化数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = NOT_DIGESTION, layout = LAYOUT_E)
	private double undigestedNumber;

	public static ReserveLeaveDto from(ReserveLeave domain) {
		return domain == null ? null : new ReserveLeaveDto(
						DayUsedNumberDto.from(domain.getUsedNumber()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)),
						domain.getUndigestedNumber().getUndigestedDays().v());
	}

	public ReserveLeave toDomain() {
		return ReserveLeave.of(usedNumber == null ? new ReserveLeaveUsedNumber() : usedNumber.toDomain(),
				remainingNumber == null ? new ReserveLeaveRemainingNumber() : remainingNumber.toReserveDomain(),
				remainingNumberBeforeGrant == null ? new ReserveLeaveRemainingNumber() : remainingNumberBeforeGrant.toReserveDomain(),
				Optional.ofNullable(
						remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toReserveDomain()),
				ReserveLeaveUndigestedNumber.of(new ReserveLeaveRemainingDayNumber(undigestedNumber)));
	}
	
	public static ReserveLeaveDto from(RealReserveLeave domain) {
		return domain == null ? null : new ReserveLeaveDto(
						DayUsedNumberDto.from(domain.getUsedNumber()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
						CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrant().orElse(null)),
						0);
	}

	public RealReserveLeave toRealDomain() {
		return RealReserveLeave.of(
				usedNumber == null ? new ReserveLeaveUsedNumber() : usedNumber.toDomain(),
				remainingNumber == null ? new ReserveLeaveRemainingNumber() : remainingNumber.toReserveDomain(),
				remainingNumberBeforeGrant == null ? new ReserveLeaveRemainingNumber() : remainingNumberBeforeGrant.toReserveDomain(),
				Optional.ofNullable(remainingNumberAfterGrant == null ? null : remainingNumberAfterGrant.toReserveDomain()));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (NOT_DIGESTION.equals(path)) {
			return Optional.of(ItemValue.builder().value(undigestedNumber).valueType(ValueType.DAYS));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case USAGE:
			return new DayUsedNumberDto();
		case REMAIN:
		case (GRANT + BEFORE):
		case (GRANT + AFTER):
			return new CommonLeaveRemainingNumberDto();
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
		case (GRANT + BEFORE):
			return Optional.ofNullable(remainingNumberBeforeGrant);
		case (GRANT + AFTER):
			return Optional.ofNullable(remainingNumberAfterGrant);
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

	@Override
	public void set(String path, ItemValue value) {
		if (NOT_DIGESTION.equals(path)) {
			undigestedNumber = value.valueOrDefault(0d);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case USAGE:
			usedNumber = (DayUsedNumberDto) value; break;
		case REMAIN:
			remainingNumber = (CommonLeaveRemainingNumberDto) value; break;
		case (GRANT + BEFORE):
			remainingNumberBeforeGrant = (CommonLeaveRemainingNumberDto) value; break;
		case (GRANT + AFTER):
			remainingNumberAfterGrant = (CommonLeaveRemainingNumberDto) value; break;
		default:
			break;
		}
	}

	
}
