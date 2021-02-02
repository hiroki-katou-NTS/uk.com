package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/** 日別実績の休憩時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeSheetDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 計上用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = CALC)
	private TotalDeductionTimeDto toRecordTotalTime;

	/** 控除用合計時間: 控除合計時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION)
	private TotalDeductionTimeDto deductionTotalTime;

	/** 勤務間時間: 勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = WORKING_TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer duringWork;

	/** 補正後時間帯: 休憩時間帯 */
	 @AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = AFTER_CORRECTED, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<BreakTimeSheetDto> correctedTimeSheet;

	/** 休憩回数: 休憩外出回数 */
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = COUNT)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer breakTimes;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case WORKING_TIME:
			return Optional.of(ItemValue.builder().value(duringWork).valueType(ValueType.TIME));
		case (COUNT):
			return Optional.of(ItemValue.builder().value(breakTimes).valueType(ValueType.COUNT));
		default:
			return Optional.empty();
		}
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case CALC:
		case (DEDUCTION):
			return new TotalDeductionTimeDto();
		case (AFTER_CORRECTED):
			return new BreakTimeSheetDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (CALC):
			return Optional.ofNullable(toRecordTotalTime);
		case (DEDUCTION):
			return Optional.ofNullable(deductionTotalTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}

	@Override
	public int size(String path) {
		if (AFTER_CORRECTED.equals(path)) {
			return 10;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case WORKING_TIME:
		case (COUNT):
			return PropType.VALUE;
		case AFTER_CORRECTED:
			return PropType.IDX_LIST;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (AFTER_CORRECTED.equals(path)) {
			return (List<T>) correctedTimeSheet;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case WORKING_TIME:
			duringWork = value.valueOrDefault(null);
			break;
		case (COUNT):
			breakTimes = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (CALC):
			toRecordTotalTime = (TotalDeductionTimeDto ) value;
			break;
		case (DEDUCTION):
			deductionTotalTime = (TotalDeductionTimeDto ) value;
		default:
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (AFTER_CORRECTED.equals(path)) {
			correctedTimeSheet = (List<BreakTimeSheetDto>) value;
		}
	}

	public static BreakTimeSheetDailyPerformDto fromBreakTimeOfDaily(BreakTimeOfDaily domain) {
		return domain == null ? null : new BreakTimeSheetDailyPerformDto(
						TotalDeductionTimeDto.getDeductionTime(domain.getToRecordTotalTime()),
						TotalDeductionTimeDto.getDeductionTime(domain.getDeductionTotalTime()),
						getAttendanceTime(domain.getWorkTime()),
						ConvertHelper.mapTo(domain.getBreakTimeSheet(),
								(c) -> new BreakTimeSheetDto(
										getTime(c.getStartTime()),
										getTime(c.getEndTime()), 
										getAttendanceTime(c.getBreakTime()),
										c.getBreakFrameNo().v().intValue())),
						domain.getGooutTimes() == null ? 0 : domain.getGooutTimes().v());
	}

	@Override
	public BreakTimeSheetDailyPerformDto clone() {
		return new BreakTimeSheetDailyPerformDto(
						toRecordTotalTime == null ? null : toRecordTotalTime.clone(),
						toRecordTotalTime == null ? null : toRecordTotalTime.clone(),
						duringWork,
						correctedTimeSheet == null ? null : correctedTimeSheet.stream().map(t -> t.clone()).collect(Collectors.toList()),
						breakTimes);
	}
	
	private static Integer getTime(TimeWithDayAttr domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}
	
	private static Integer getAttendanceTime(AttendanceTime domain) {
		return domain == null ? null : domain.valueAsMinutes();
	}
	
	public BreakTimeOfDaily toDmain(){
		return new BreakTimeOfDaily(createDeductionTime(toRecordTotalTime), createDeductionTime(deductionTotalTime), 
						breakTimes == null ? new BreakTimeGoOutTimes(0) : new BreakTimeGoOutTimes(breakTimes), 
						duringWork == null ? AttendanceTime.ZERO : new AttendanceTime(duringWork), 
						ConvertHelper.mapTo(correctedTimeSheet, c -> new BreakTimeSheet(
												new BreakFrameNo(c.getNo()), 
												c.getStart() == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(c.getStart()),
												c.getEnd() == null ? TimeWithDayAttr.THE_PRESENT_DAY_0000 : new TimeWithDayAttr(c.getEnd()), 
												c.getBreakTime() == null ? AttendanceTime.ZERO : new AttendanceTime(c.getBreakTime()))));
	}
	
	public static BreakTimeOfDaily defaultValue(){
		return new BreakTimeOfDaily(DeductionTotalTime.defaultValue(), DeductionTotalTime.defaultValue(), 
						new BreakTimeGoOutTimes(0), AttendanceTime.ZERO, new ArrayList<>());
	}
	
	private DeductionTotalTime createDeductionTime(TotalDeductionTimeDto dto) {
		return dto == null ? DeductionTotalTime.defaultValue() : dto.createDeductionTime();
	}
}
