package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 法定外残業深夜時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcessOverTimeWorkMidNightTimeDto implements ItemConst {

	/** 時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TIME)
	private CalcAttachTimeDto time;

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
