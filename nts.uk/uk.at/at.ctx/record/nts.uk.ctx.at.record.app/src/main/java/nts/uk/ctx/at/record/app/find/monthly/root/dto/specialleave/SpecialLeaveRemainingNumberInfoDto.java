package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumberInfo;

/**
 * @author thanh_nx
 *
 *         特休残数情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveRemainingNumberInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private SpecialLeaveRemainingNumberDto remainingNumberBeforeGrant;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private SpecialLeaveRemainingNumberDto remainingNumberAfterGrantOpt;

	public static SpecialLeaveRemainingNumberInfoDto from(SpecialLeaveRemainingNumberInfo domain) {

		return new SpecialLeaveRemainingNumberInfoDto(SpecialLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
														SpecialLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrantOpt().orElse(null)));
	}

	public SpecialLeaveRemainingNumberInfo toDomain() {
		return new SpecialLeaveRemainingNumberInfo(
				remainingNumberBeforeGrant == null ? new SpecialLeaveRemainingNumber() : remainingNumberBeforeGrant.toDomain(),
				Optional.ofNullable(remainingNumberAfterGrantOpt == null ? null : remainingNumberAfterGrantOpt.toDomain()));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {

		switch (path) {
		case GRANT + BEFORE:
		case GRANT + AFTER:
			return new SpecialLeaveRemainingNumberDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		switch (path) {

		case GRANT + BEFORE:
			return Optional.ofNullable(this.remainingNumberBeforeGrant);

		case GRANT + AFTER:
			return Optional.ofNullable(this.remainingNumberAfterGrantOpt);

		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {

		switch (path) {
		case GRANT + BEFORE:
			this.remainingNumberBeforeGrant = (SpecialLeaveRemainingNumberDto) value;
			break;
		case GRANT + AFTER:
			this.remainingNumberAfterGrantOpt = (SpecialLeaveRemainingNumberDto) value;
			break;
		}

	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case GRANT + BEFORE:
		case GRANT + AFTER:
			return PropType.OBJECT;
		default:
			return PropType.OBJECT;
		}
	}
}
