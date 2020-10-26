package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DayUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.TimeUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.OuenTimeOfMonthlyDto.OuenWorkAggregateDetailDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;

/** 特別休暇使用数 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUseNumberDto implements ItemConst, AttendanceItemDataGate {
	
	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_A)
	private DayUsedNumberDto useDays;
	
	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME, layout = LAYOUT_B)
	private TimeUsedNumberDto useTimes;
	
	public SpecialLeaveUseNumber toDomain(){
		return new SpecialLeaveUseNumber(useDays == null ? null : useDays.toSpecial(),
										Optional.ofNullable(useTimes == null ? null : useTimes.toSpecial()));
	}
	
	public static SpecialLeaveUseNumberDto from(SpecialLeaveUseNumber domain){
		return domain == null ? null : new SpecialLeaveUseNumberDto(DayUsedNumberDto.from(domain.getUseDays()), 
																	TimeUsedNumberDto.from(domain.getUseTimes().orElse(null)));
	}
	
	@Override
	public PropType typeOf(String path) {
		return PropType.OBJECT;
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case USAGE + DAYS:
			return new DayUsedNumberDto();
		case USAGE + TIME:
			return new TimeUsedNumberDto();
		default:
			break;
		}
		return null;
	}
	
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case USAGE + DAYS:
			return Optional.ofNullable(this.useDays);
		case USAGE + TIME:
			return Optional.ofNullable(this.useTimes);
		default:
			return Optional.empty();
		}
	}
}