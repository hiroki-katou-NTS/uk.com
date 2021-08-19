package nts.uk.ctx.at.record.app.find.monthly.root.dto.care;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildCareNurseUsedInfoDto implements ItemConst, AttendanceItemDataGate {

	/** 使用数 */
	private ChildCareNurseUsedNumberDto usedNumber;
	/** 時間休暇使用回数 */
	private int usedTimes;
	/** 時間休暇使用日数 */
	private int usedDays;
	
	public static ChildCareNurseUsedInfoDto from (ChildCareNurseUsedInfo domain) {
		if (domain == null) {
			return null;
		}
		return new ChildCareNurseUsedInfoDto(
				ChildCareNurseUsedNumberDto.from(domain.getUsedNumber()),
				domain.getUsedTimes().v(), 
				domain.getUsedDays().v());
	}
	
	public ChildCareNurseUsedInfo domain() {
		
		return ChildCareNurseUsedInfo.of(
				usedNumber == null ? new ChildCareNurseUsedNumber() : usedNumber.domain(),
				new UsedTimes(usedTimes), 
				new UsedTimes(usedDays));	
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.COUNT));
		case TIME:
			return Optional.of(ItemValue.builder().value(usedTimes).valueType(ValueType.COUNT));
		default:
			return AttendanceItemDataGate.super.valueOf(path);
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case DAYS:
		case TIME:
			return PropType.VALUE;
		default:
			return AttendanceItemDataGate.super.typeOf(path);
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case DAYS:
			this.usedDays = value.valueOrDefault(0); return;
		case TIME:
			this.usedTimes = value.valueOrDefault(0); return;
		default:
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (path.equals(USAGE)) {
			return new ChildCareNurseUsedNumberDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (path.equals(USAGE)) {
			return Optional.of(this.usedNumber);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (path.equals(USAGE)) {
			this.usedNumber = (ChildCareNurseUsedNumberDto) value;
		}
	}
}
