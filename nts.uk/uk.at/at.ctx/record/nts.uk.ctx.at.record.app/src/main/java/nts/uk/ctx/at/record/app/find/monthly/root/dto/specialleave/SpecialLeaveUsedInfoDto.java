package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUsedInfo;

/**
 * @author thanh_nx
 *
 *         特別休暇使用情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveUsedInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 合計 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private SpecialLeaveUseNumberDto usedNumber;

	/** 付与前 */
	// @AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_B)
	private SpecialLeaveUseNumberDto usedNumberBeforeGrant;

	/** 特休使用回数 （1日2回使用した場合２回でカウント） */
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.COUNT)
	private int specialLeaveUsedTimes;

	/** 特休使用日数 （1日2回使用した場合１回でカウント） */
	private int specialLeaveUsedDayTimes;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_C)
	private SpecialLeaveUseNumberDto usedNumberAfterGrantOpt;

	public static SpecialLeaveUsedInfoDto from(SpecialLeaveUsedInfo usedNumberInfo) {
		return new SpecialLeaveUsedInfoDto(SpecialLeaveUseNumberDto.from(usedNumberInfo.getUsedNumber()),
				SpecialLeaveUseNumberDto.from(usedNumberInfo.getUsedNumberBeforeGrant()),
				usedNumberInfo.getSpecialLeaveUsedTimes() == null ? 0 : usedNumberInfo.getSpecialLeaveUsedTimes().v(),
				usedNumberInfo.getSpecialLeaveUsedDayTimes() == null ? 0
						: usedNumberInfo.getSpecialLeaveUsedDayTimes().v(),
				usedNumberInfo.getUsedNumberAfterGrantOpt().map(x -> SpecialLeaveUseNumberDto.from(x)).orElse(null));
	}

	public SpecialLeaveUsedInfo toDomain() {
		return new SpecialLeaveUsedInfo(
				usedNumber == null ? new SpecialLeaveUseNumber() : usedNumber.toDomain(), 
				usedNumberBeforeGrant == null ? new SpecialLeaveUseNumber() : usedNumberBeforeGrant.toDomain(),
				new UsedTimes(specialLeaveUsedTimes), new UsedTimes(specialLeaveUsedDayTimes),
				Optional.ofNullable(usedNumberAfterGrantOpt == null ? null : usedNumberAfterGrantOpt.toDomain()));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {

		switch (path) {
		case TOTAL:
		case GRANT + BEFORE:
		case GRANT + AFTER:
			return new SpecialLeaveUseNumberDto();

		default:
			return null;
		}

	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		switch (path) {
		case TOTAL:
			return Optional.ofNullable(this.usedNumber);

		case GRANT + BEFORE:
			return Optional.ofNullable(this.usedNumberBeforeGrant);

		case GRANT + AFTER:
			return Optional.ofNullable(this.usedNumberAfterGrantOpt);

		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {

		switch (path) {
		case TOTAL:
			this.usedNumber = (SpecialLeaveUseNumberDto) value;
			break;

		case GRANT + BEFORE:
			this.usedNumberBeforeGrant = (SpecialLeaveUseNumberDto) value;
			break;

		case GRANT + AFTER:
			this.usedNumberAfterGrantOpt = (SpecialLeaveUseNumberDto) value;
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
		case COUNT:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(specialLeaveUsedTimes).valueType(ValueType.COUNT));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			specialLeaveUsedTimes = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

}
