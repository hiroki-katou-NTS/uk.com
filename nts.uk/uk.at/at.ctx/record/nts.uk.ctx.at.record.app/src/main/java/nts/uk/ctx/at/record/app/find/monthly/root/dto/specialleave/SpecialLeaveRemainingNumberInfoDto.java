package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
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

	/** 合計 */

	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private SpecialLeaveRemainingNumberDto remainingNumber;

	/** 付与前 */
	// @AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private SpecialLeaveRemainingNumberDto remainingNumberBeforeGrant;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private Optional<SpecialLeaveRemainingNumberDto> remainingNumberAfterGrantOpt;

	public static SpecialLeaveRemainingNumberInfoDto from(SpecialLeaveRemainingNumberInfo domain) {

		return new SpecialLeaveRemainingNumberInfoDto(SpecialLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
				SpecialLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
				domain.getRemainingNumberAfterGrantOpt().map(x -> SpecialLeaveRemainingNumberDto.from(x)));
	}

	public SpecialLeaveRemainingNumberInfo toDomain() {
		return new SpecialLeaveRemainingNumberInfo(remainingNumber.toDomain(), remainingNumberBeforeGrant.toDomain(),
				remainingNumberAfterGrantOpt.map(x -> x.toDomain()));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {

		switch (path) {
		case DAYS:
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
		case TOTAL:
			return Optional.of(this.remainingNumber);

		case GRANT + BEFORE:
			return Optional.of(this.remainingNumberBeforeGrant);

		case GRANT + AFTER:
			return this.remainingNumberAfterGrantOpt.map(x -> (SpecialLeaveRemainingNumberDto) x);

		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {

		switch (path) {
		case TOTAL:
			this.remainingNumber = (SpecialLeaveRemainingNumberDto) value;
			break;
		case GRANT + BEFORE:
			this.remainingNumberBeforeGrant = (SpecialLeaveRemainingNumberDto) value;
			break;
		case GRANT + AFTER:
			this.remainingNumberAfterGrantOpt = Optional
					.ofNullable(value == null ? null : (SpecialLeaveRemainingNumberDto) value);
			break;
		}

	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case TOTAL:
		case GRANT + BEFORE:
		case GRANT + AFTER:
			return PropType.OBJECT;
		default:
			return PropType.OBJECT;
		}
	}
}
