package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 日別実績の加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDailyPerformDto implements ItemConst {

	/** 特定日加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = SPECIFIC, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RaisingSalaryTimeDto> specificDayOfRaisingSalaryTime;

	/** 加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = RAISING_SALARY, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<RaisingSalaryTimeDto> raisingSalaryTime;
	
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
