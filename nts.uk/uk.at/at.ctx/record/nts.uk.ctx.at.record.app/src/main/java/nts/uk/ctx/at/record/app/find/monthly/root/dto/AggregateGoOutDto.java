package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout.AggregateGoOut;

@Data
/** 集計外出 */
@NoArgsConstructor
@AllArgsConstructor
public class AggregateGoOutDto implements ItemConst, AttendanceItemDataGate {

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

	/** コアタイム外時間: 計算付き月間時間 */
	@AttendanceItemLayout(jpPropertyName = CORE_OUT, layout = LAYOUT_F, needCheckIDWithMethod = DEFAULT_CHECK_ENUM_METHOD)
	private TimeMonthWithCalculationDto coreOutTime;

	@Override
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
			dto.setCoreOutTime(TimeMonthWithCalculationDto.from(domain.getTotalTime()));
		}
		return dto;
	}

	public AggregateGoOut toDomain(){
		return AggregateGoOut.of(reason(),  
					new AttendanceTimesMonth(times), 
					legalTime == null ? new TimeMonthWithCalculation() : legalTime.toDomain(), 
					illegalTime == null ? new TimeMonthWithCalculation() : illegalTime.toDomain(), 
					totalTime == null ? new TimeMonthWithCalculation() : totalTime.toDomain(), 
					coreOutTime == null ? new TimeMonthWithCalculation() : coreOutTime.toDomain());
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

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(times).valueType(ValueType.COUNT));
		case REASON:
			return Optional.of(ItemValue.builder().value(attr).valueType(ValueType.ATTR));
			default: return Optional.empty();
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case COUNT:
		case REASON:
			return PropType.VALUE;
			default: return PropType.OBJECT;
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			this.times = value.valueOrDefault(0); break;
		case REASON:
			this.attr = value.valueOrDefault(0); break;
		default:
		}
	}

	@Override
	public void setEnum(String enumText) {
		switch (enumText) {
		case E_SUPPORT:
			this.attr = 0; break;
		case E_UNION:
			this.attr = 1; break;
		case E_CHARGE:
			this.attr = 2; break;
		case E_OFFICAL:
			this.attr = 3; break;
		default:
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case TOTAL:
		case ILLEGAL:
		case LEGAL:
			return new TimeMonthWithCalculationDto();
		default:
			return null;
		}
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case TOTAL:
			return Optional.ofNullable(totalTime);
		case ILLEGAL:
			return Optional.ofNullable(illegalTime);
		case LEGAL:
			return Optional.ofNullable(legalTime);
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case TOTAL:
			totalTime = (TimeMonthWithCalculationDto) value; break;
		case ILLEGAL:
			illegalTime = (TimeMonthWithCalculationDto) value; break;
		case LEGAL:
			legalTime = (TimeMonthWithCalculationDto) value; break;
		default:
		}
	}
	
}
