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
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimes;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/** 日別実績の臨時時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 臨時時間: 日別実績の臨時枠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = FRAMES, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<TemporaryTimeFrameDto> temporaryFrameTime;
	
	/** 臨時回数 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = COUNT)
	@AttendanceItemValue(type = ValueType.COUNT)
	private Integer temporaryTimes;
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case COUNT:
			return Optional.of(ItemValue.builder().value(temporaryTimes).valueType(ValueType.COUNT));
		}
		return Optional.empty();
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case FRAMES:
			return new TemporaryTimeFrameDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		if (FRAMES.equals(path)) {
			return 10;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case FRAMES:
			return PropType.IDX_LIST;
		case COUNT:
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (FRAMES.equals(path)) {
			return (List<T>) temporaryFrameTime;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case COUNT:
			this.temporaryTimes = value.valueOrDefault(null);
			break;
		default:
			break;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (FRAMES.equals(path)) {
			temporaryFrameTime = (List<TemporaryTimeFrameDto>) value;
		}
	}
	
	@Override
	public TemporaryTimeDailyPerformDto clone(){
		return new TemporaryTimeDailyPerformDto(
				temporaryFrameTime == null ? null : temporaryFrameTime.stream().map(c -> c.clone()).collect(Collectors.toList()),
				temporaryTimes);
	}

	public static TemporaryTimeDailyPerformDto fromDomain(TemporaryTimeOfDaily domain) {
		return domain == null ? null : new TemporaryTimeDailyPerformDto(
				ConvertHelper.mapTo(domain.getTemporaryTime(),
						c -> new TemporaryTimeFrameDto(
								c.getWorkNo().v(),
								c.getTemporaryLateNightTime().v(),
								c.getTemporaryTime().v())),
				gettemporaryTimes(domain.getTemporaryTimes()));
	}

	private static Integer gettemporaryTimes(TemporaryTimes times) {
		return times == null ? 0 : times.v();
	}
	
	public TemporaryTimeOfDaily toDomain() {
		return new TemporaryTimeOfDaily(
				ConvertHelper.mapTo(temporaryFrameTime,
						(c) -> new TemporaryFrameTimeOfDaily(
								new WorkNo(c.getNo()),
								toAttendanceTime(c.getTemporaryTime()),
								toAttendanceTime(c.getTemporaryNightTime()))),
				toTemporaryTimes(temporaryTimes));
	}
	
	private AttendanceTime toAttendanceTime(Integer time) {
		return time == null ? AttendanceTime.ZERO : new AttendanceTime(time);
	}
	
	private TemporaryTimes toTemporaryTimes(Integer times) {
		return times == null ? new TemporaryTimes(0) : new TemporaryTimes(times);
	}
	
	public static TemporaryTimeOfDaily defaultDomain() {
		return new TemporaryTimeOfDaily(new ArrayList<>(), new TemporaryTimes(0));
	}
}
