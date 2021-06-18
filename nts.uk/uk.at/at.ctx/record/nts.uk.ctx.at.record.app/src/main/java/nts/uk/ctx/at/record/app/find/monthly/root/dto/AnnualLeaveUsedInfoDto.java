package nts.uk.ctx.at.record.app.find.monthly.root.dto;

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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;

@Data
/** 年休使用情報 */
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveUsedInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 付与前 */
	@AttendanceItemLayout(jpPropertyName = GRANT + BEFORE, layout = LAYOUT_A)
	private AnnualLeaveUsedNumberDto usedNumberBeforeGrant;

	/** 合計 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_B)
	private AnnualLeaveUsedNumberDto usedNumber;

	/** 時間年休使用回数 （1日2回使用した場合２回でカウント） */
	@AttendanceItemLayout(jpPropertyName = USAGE + COUNT, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.COUNT)
	private int annualLeaveUsedTimes;

	/** 時間年休使用日数 （1日2回使用した場合１回でカウント）  */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.COUNT)
	private int annualLeaveUsedDayTimes;

	/** 付与後 */
	@AttendanceItemLayout(jpPropertyName = GRANT + AFTER, layout = LAYOUT_E)
	private AnnualLeaveUsedNumberDto usedNumberAfterGrantOpt;

	public static AnnualLeaveUsedInfoDto from(AnnualLeaveUsedInfo domain) {
		return new AnnualLeaveUsedInfoDto(AnnualLeaveUsedNumberDto.from(domain.getUsedNumberBeforeGrant()),
											AnnualLeaveUsedNumberDto.from(domain.getUsedNumber()),
											domain.getAnnualLeaveUsedTimes().v(),
											domain.getAnnualLeaveUsedDayTimes().v(),
											AnnualLeaveUsedNumberDto.from(domain.getUsedNumberAfterGrantOpt().orElse(null)));
	}

	public AnnualLeaveUsedInfo toDomain() {
		return AnnualLeaveUsedInfo.of(usedNumber == null ? new AnnualLeaveUsedNumber() : usedNumber.toDomain(),
										usedNumberBeforeGrant == null ? new AnnualLeaveUsedNumber() : usedNumberBeforeGrant.toDomain(),
										new UsedTimes(annualLeaveUsedTimes), new UsedTimes(annualLeaveUsedDayTimes),
										usedNumberAfterGrantOpt == null ? Optional.empty() : Optional.of(usedNumberAfterGrantOpt.toDomain()));
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if ((GRANT + BEFORE).equals(path) || (GRANT + AFTER).equals(path) || (TOTAL).equals(path)) {
			return new AnnualLeaveUsedNumberDto();
		} 
		return null;
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {

		if ((GRANT + BEFORE).equals(path)) {
			return Optional.ofNullable(this.usedNumberBeforeGrant);
		} else if ((GRANT + AFTER).equals(path)) {
			return Optional.ofNullable(this.usedNumberAfterGrantOpt);
		} else if (TOTAL.equals(path)) {
			return Optional.ofNullable(this.usedNumber);
		}
		return Optional.empty();
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if ((GRANT + BEFORE).equals(path)) {
			this.usedNumberBeforeGrant = (AnnualLeaveUsedNumberDto) value;
		} else if ((GRANT + AFTER).equals(path)) {
			this.usedNumberAfterGrantOpt = (AnnualLeaveUsedNumberDto) value;
		} else if (TOTAL.equals(path)) {
			this.usedNumber = (AnnualLeaveUsedNumberDto) value;
		}
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case USAGE + COUNT:
			return Optional.of(ItemValue.builder().value(annualLeaveUsedTimes).valueType(ValueType.COUNT));
		case USAGE + DAYS:
			return Optional.of(ItemValue.builder().value(annualLeaveUsedDayTimes).valueType(ValueType.COUNT));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case USAGE + COUNT:
			annualLeaveUsedTimes = value.valueOrDefault(0);
			break;
		case USAGE + DAYS:
			annualLeaveUsedDayTimes = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}

	@Override
	public PropType typeOf(String path) {

		switch (path) {
		case USAGE + COUNT:
		case USAGE + DAYS:
			return PropType.VALUE;
		case GRANT + BEFORE:
		case GRANT + AFTER:
		case TOTAL:
			return PropType.OBJECT;
		default:
			return PropType.OBJECT;
		}
	}

}
