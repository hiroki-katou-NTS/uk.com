package nts.uk.ctx.at.record.app.find.monthly.root.dto.care;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildCareNurseUsedNumberDto implements ItemConst, AttendanceItemDataGate {

	/** 日数 */
	private double usedDays;
	/** 時間 */
	private Integer usedTimes;
	
	public static ChildCareNurseUsedNumberDto from (ChildCareNurseUsedNumber domain) {
		if (domain == null) {
			return null;
		}
		return new ChildCareNurseUsedNumberDto(domain.getUsedDay().v(), 
												domain.getUsedTimes().map(c -> c.valueAsMinutes()).orElse(null));
	}
	
	public ChildCareNurseUsedNumber domain() {
		
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(usedDays), 
											Optional.ofNullable(usedTimes == null ? null : new TimeOfUse(usedTimes)));	
	}
	
	public static ChildCareNurseUsedNumberDto from (ChildCareNurseRemainingNumber domain) {
		if (domain == null) {
			return null;
		}
		return new ChildCareNurseUsedNumberDto(domain.getRemainDay().v(), 
												domain.getRemainTimes().map(c -> c.valueAsMinutes()).orElse(null));
	}
	
	public ChildCareNurseRemainingNumber domainRemain() {
		
		return ChildCareNurseRemainingNumber.of(new DayNumberOfRemain(usedDays), 
												Optional.ofNullable(usedTimes == null ? null : new TimeOfRemain(usedTimes)));	
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case DAYS:
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.DAYS));
		case TIME:
			return Optional.of(ItemValue.builder().value(usedTimes).valueType(ValueType.TIME));
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
			this.usedDays = value.valueOrDefault(0d); return;
		case TIME:
			this.usedTimes = value.valueOrDefault(null); return;
		default:
		}
	}
}
