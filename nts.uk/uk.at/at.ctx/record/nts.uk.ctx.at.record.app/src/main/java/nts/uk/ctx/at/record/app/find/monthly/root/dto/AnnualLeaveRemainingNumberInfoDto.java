package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonLeaveRemainingNumberDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumberInfo;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

@Data
/** 年休残数情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveRemainingNumberInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 合計 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private CommonLeaveRemainingNumberDto remainingNumber;

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = BEFORE, layout = LAYOUT_B)
	private CommonLeaveRemainingNumberDto before;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = AFTER, layout = LAYOUT_C)
	private CommonLeaveRemainingNumberDto after;

	public static AnnualLeaveRemainingNumberInfoDto from(AnnualLeaveRemainingNumberInfo domain) {
		return new AnnualLeaveRemainingNumberInfoDto(CommonLeaveRemainingNumberDto.from(domain.getRemainingNumber()),
														CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberBeforeGrant()),
														CommonLeaveRemainingNumberDto.from(domain.getRemainingNumberAfterGrantOpt().orElse(null)));
	}

	public AnnualLeaveRemainingNumberInfo toDomain() {
		return new AnnualLeaveRemainingNumberInfo(remainingNumber == null ? new AnnualLeaveRemainingNumber() : remainingNumber.toDomain(),
													before == null ? new AnnualLeaveRemainingNumber() : before.toDomain(),
													after == null ? Optional.empty() : Optional.of(after.toDomain()));
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TOTAL:
		case BEFORE:
		case AFTER:
			return new CommonLeaveRemainingNumberDto();
		default:
			return AttendanceItemDataGate.super.newInstanceOf(path);
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TOTAL:
			return Optional.ofNullable(remainingNumber);
		case BEFORE:
			return Optional.ofNullable(before);
		case AFTER:
			return Optional.ofNullable(after);
		default:
			return AttendanceItemDataGate.super.get(path);
		}
	
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TOTAL:
			remainingNumber = (CommonLeaveRemainingNumberDto) value; break;
		case BEFORE:
			before = (CommonLeaveRemainingNumberDto) value; break;
		case AFTER:
			after = (CommonLeaveRemainingNumberDto) value; break;
		default:
			break;
		}
	}
}
