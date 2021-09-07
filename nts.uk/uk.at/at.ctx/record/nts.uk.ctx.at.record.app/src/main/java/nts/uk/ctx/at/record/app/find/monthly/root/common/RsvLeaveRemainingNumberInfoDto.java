package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingInfo;

@Data
/** 積立年休残情報 */
@NoArgsConstructor
@AllArgsConstructor
public class RsvLeaveRemainingNumberInfoDto implements ItemConst, AttendanceItemDataGate {

//	/** 合計残日数 */
//	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
//	private RsvLeaveRemainingNumberDto totalRemainingDays;

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private RsvLeaveRemainingNumberDto before;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private RsvLeaveRemainingNumberDto after;

	public static RsvLeaveRemainingNumberInfoDto from(ReserveLeaveRemainingInfo domain) {

		return domain == null ? null
				: new RsvLeaveRemainingNumberInfoDto(
						RsvLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
						domain.getRemainingNumberAfterGrantOpt().map(c -> RsvLeaveRemainingNumberDto.from(c))
								.orElse(null));
	}

	public ReserveLeaveRemainingInfo toReserveDomain() {

		return ReserveLeaveRemainingInfo.of(before.toReserveDomain(),
				Optional.ofNullable(after == null ? null : after.toReserveDomain()));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case DAYS:
		case BEFORE:
		case AFTER:
			return new RsvLeaveRemainingNumberDto();
		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case DAYS:
			//return Optional.ofNullable(totalRemainingDays);
			return Optional.empty();
		case BEFORE:
			return Optional.ofNullable(before);
		case AFTER:
			return Optional.ofNullable(after);
		default:
			break;
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case BEFORE:
		case AFTER:
			return PropType.OBJECT;
		default:
			return AttendanceItemDataGate.super.typeOf(path);
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case DAYS:
			//totalRemainingDays = (RsvLeaveRemainingNumberDto) value;
			break;
		case BEFORE:
			before = (RsvLeaveRemainingNumberDto) value;
			break;
		case AFTER:
			after = (RsvLeaveRemainingNumberDto) value;
			break;
		default:
			break;
		}
	}
}
