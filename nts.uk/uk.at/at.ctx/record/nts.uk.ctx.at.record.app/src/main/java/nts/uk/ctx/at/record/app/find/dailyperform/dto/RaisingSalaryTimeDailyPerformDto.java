package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.record.dom.raisesalarytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 日別実績の加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDailyPerformDto {

	/** 特定日加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定加給時間", listMaxLength = 10, indexField = "raisingSalaryNo")
	private List<RaisingSalaryTimeDto> specificDayOfRaisingSalaryTime;

	/** 加給時間 : 加給時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "加給時間", listMaxLength = 10, indexField = "raisingSalaryNo")
	private List<RaisingSalaryTimeDto> raisingSalaryTime;
	
	public static RaisingSalaryTimeDailyPerformDto toDto(RaiseSalaryTimeOfDailyPerfor domain){
		return domain == null ? null : new RaisingSalaryTimeDailyPerformDto(toArray(domain.getRaisingSalaryTimes()), toArray(domain.getRaisingSalaryTimes()));
	}
	
	private static List<RaisingSalaryTimeDto> toArray(List<BonusPayTime> domain){
		return ConvertHelper.mapTo(domain, c -> RaisingSalaryTimeDto.toDto(c));
	}
	
	public RaiseSalaryTimeOfDailyPerfor toDomain(){
		return new RaiseSalaryTimeOfDailyPerfor(ConvertHelper.mapTo(raisingSalaryTime, c -> c.toDomain()), 
												ConvertHelper.mapTo(specificDayOfRaisingSalaryTime, c -> c.toDomain()));
	}
}
