package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;

/** 日別実績の加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDailyPerformDto implements ItemConst, AttendanceItemDataGate {

	/** 特定日加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = SPECIFIC, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RaisingSalaryTimeDto> specificDayOfRaisingSalaryTime;

	/** 加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = RAISING_SALARY, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RaisingSalaryTimeDto> raisingSalaryTime;
	

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case SPECIFIC:
		case RAISING_SALARY:
			return new RaisingSalaryTimeDto();

		default:
			break;
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		switch (path) {
		case SPECIFIC:
		case RAISING_SALARY:
			return 10;
		default:
			return AttendanceItemDataGate.super.size(path);
		}
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case SPECIFIC:
		case RAISING_SALARY:
			return PropType.IDX_LIST;
		default:
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		switch (path) {
		case SPECIFIC:
			return (List<T>) specificDayOfRaisingSalaryTime;
		case RAISING_SALARY:
			return (List<T>) raisingSalaryTime;
		default:
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		switch (path) {
		case SPECIFIC:
			specificDayOfRaisingSalaryTime = (List<RaisingSalaryTimeDto>) value;
			break;
		case RAISING_SALARY:
			raisingSalaryTime = (List<RaisingSalaryTimeDto>)  value;
			break;
		default:
		}
	}
	
	public static RaisingSalaryTimeDailyPerformDto toDto(RaiseSalaryTimeOfDailyPerfor domain){
		return domain == null ? null : new RaisingSalaryTimeDailyPerformDto(toArray(domain.getAutoCalRaisingSalarySettings()), toArray(domain.getRaisingSalaryTimes()));
	}
	
	@Override
	public RaisingSalaryTimeDailyPerformDto clone(){
		return new RaisingSalaryTimeDailyPerformDto(cloneA(specificDayOfRaisingSalaryTime), cloneA(raisingSalaryTime));
	}
	
	private static List<RaisingSalaryTimeDto> toArray(List<BonusPayTime> domain){
		return ConvertHelper.mapTo(domain, c -> RaisingSalaryTimeDto.toDto(c));
	}
	
	private List<RaisingSalaryTimeDto> cloneA(List<RaisingSalaryTimeDto> dto){
		return dto == null ? null : dto.stream().map(t -> t.clone()).collect(Collectors.toList());
	}
	
	public RaiseSalaryTimeOfDailyPerfor toDomain(){
		return new RaiseSalaryTimeOfDailyPerfor(ConvertHelper.mapTo(raisingSalaryTime, c -> c.toDomain()), 
												ConvertHelper.mapTo(specificDayOfRaisingSalaryTime, c -> c.toDomain()));
	}
}
