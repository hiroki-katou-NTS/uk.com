package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.ExcessOverTimeWorkMidNightTime;

/** 法定外残業深夜時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOverTimeWorkMidNightTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto time;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (TIME.equals(path)) {
			return new CalcAttachTimeDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (TIME.equals(path)) {
			return Optional.ofNullable(time);
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (TIME.equals(path)) {
			time = (CalcAttachTimeDto) value;
		}
	}

	public static ExcessOverTimeWorkMidNightTimeDto fromOverTimeWorkDailyPerform(
			ExcessOverTimeWorkMidNightTime domain) {
		return domain == null || domain.getTime() == null ? null : 
				new ExcessOverTimeWorkMidNightTimeDto(CalcAttachTimeDto.toTimeWithCal(domain.getTime()));
	}
	
	@Override
	public ExcessOverTimeWorkMidNightTimeDto clone() {
		return new ExcessOverTimeWorkMidNightTimeDto(time == null ? null : time.clone());
	}

	public ExcessOverTimeWorkMidNightTime toDomain() {
		return time == null ? new ExcessOverTimeWorkMidNightTime(TimeDivergenceWithCalculation.defaultValue()) 
				: new ExcessOverTimeWorkMidNightTime(time.createTimeDivWithCalc());
	}
}
