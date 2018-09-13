package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOut;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;

@Data
/** 集計外出 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateGoOutDto implements ItemConst {

	/** 回数: 勤怠月間回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_A, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int times;

	/** 外出理由: 外出理由 */
	@AttendanceItemValue(type = ValueType.ATTR)
	@AttendanceItemLayout(jpPropertyName = REASON, layout = LAYOUT_B, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private int attr;

	/** 合計時間: 計算付き月間時間 */
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_C, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TimeMonthWithCalculationDto totalTime;

	/** 法定外時間: 計算付き月間時間 */
	@AttendanceItemLayout(jpPropertyName = ILLEGAL, layout = LAYOUT_D, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TimeMonthWithCalculationDto illegalTime;

	/** 法定内時間: 計算付き月間時間 */
	@AttendanceItemLayout(jpPropertyName = LEGAL, layout = LAYOUT_E, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TimeMonthWithCalculationDto legalTime;

	public String enumText() {
		switch (this.attr) {
		case 0:
			return E_SUPPORT;
		case 1:
			return E_UNION;
		case 2:
			return E_CHARGE;
		case 3:
		default:
			return E_OFFICAL;
		}
	}
	
	public static AggregateGoOutDto from(AggregateGoOut domain) {
		AggregateGoOutDto dto = new AggregateGoOutDto();
		if(domain != null) {
			dto.setAttr(domain.getGoOutReason() == null ? 0 : domain.getGoOutReason().value);
			dto.setIllegalTime(TimeMonthWithCalculationDto.from(domain.getIllegalTime()));
			dto.setLegalTime(TimeMonthWithCalculationDto.from(domain.getLegalTime()));
			dto.setTimes(domain.getTimes() == null ? 0 : domain.getTimes().v());
			dto.setTotalTime(TimeMonthWithCalculationDto.from(domain.getTotalTime()));
		}
		return dto;
	}

	public AggregateGoOut toDomain(){
		return AggregateGoOut.of(reason(),  
					new AttendanceTimesMonth(times), 
					legalTime == null ? new TimeMonthWithCalculation() : legalTime.toDomain(), 
					illegalTime == null ? new TimeMonthWithCalculation() : illegalTime.toDomain(), 
					totalTime == null ? new TimeMonthWithCalculation() : totalTime.toDomain());
	}
	
	public GoingOutReason reason() {
		switch (this.attr) {
		case 0:
			return GoingOutReason.PRIVATE;
		case 1:
			return GoingOutReason.PUBLIC;
		case 2:
			return GoingOutReason.COMPENSATION;
		case 3:
		default:
			return GoingOutReason.UNION;
		}
	}
}
