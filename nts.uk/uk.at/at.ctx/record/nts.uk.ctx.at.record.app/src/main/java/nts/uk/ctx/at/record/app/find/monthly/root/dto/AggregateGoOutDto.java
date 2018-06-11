package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOut;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
/** 集計外出 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateGoOutDto {

	/** 回数: 勤怠月間回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "回数", layout = "A", needCheckIDWithMethod = "goOutReason")
	private int times;

	/** 外出理由: 外出理由 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "外出理由", layout = "B", needCheckIDWithMethod = "goOutReason")
	private int goOutReason;

	/** 合計時間: 計算付き月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "合計時間", layout = "C", needCheckIDWithMethod = "goOutReason")
	private TimeMonthWithCalculationDto totalTime;

	/** 法定外時間: 計算付き月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定外時間", layout = "D", needCheckIDWithMethod = "goOutReason")
	private TimeMonthWithCalculationDto illegalTime;

	/** 法定内時間: 計算付き月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "法定内時間", layout = "E", needCheckIDWithMethod = "goOutReason")
	private TimeMonthWithCalculationDto legalTime;

	public String goOutReason() {
		switch (this.goOutReason) {
		case 0:
			return "私用";
		case 1:
			return "公用";
		case 2:
			return "有償";
		case 3:
		default:
			return "組合";
		}
	}
	
	public static AggregateGoOutDto from(AggregateGoOut domain) {
		AggregateGoOutDto dto = new AggregateGoOutDto();
		if(domain != null) {
			dto.setGoOutReason(domain.getGoOutReason() == null ? 0 : domain.getGoOutReason().value);
			dto.setIllegalTime(TimeMonthWithCalculationDto.from(domain.getIllegalTime()));
			dto.setLegalTime(TimeMonthWithCalculationDto.from(domain.getLegalTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
			dto.setTotalTime(TimeMonthWithCalculationDto.from(domain.getTotalTime()));
		}
		return dto;
	}

	public AggregateGoOut toDomain(){
		return AggregateGoOut.of(ConvertHelper.getEnum(goOutReason, GoingOutReason.class), 
					new AttendanceTimesMonth(times), 
					legalTime == null ? new TimeMonthWithCalculation() : legalTime.toDomain(), 
					illegalTime == null ? new TimeMonthWithCalculation() : illegalTime.toDomain(), 
					totalTime != null ? new TimeMonthWithCalculation() : totalTime.toDomain());
	}
}
