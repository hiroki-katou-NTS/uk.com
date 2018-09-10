package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonClock;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 集計PCログオン時刻 + 集計PCログオン乖離 */
public class TotalPcLogonClock implements ItemConst {

	/** 合計時刻: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private int totalTime;

	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B)
	private double totalDays;

	/** 平均時刻: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = AVERAGE, layout = LAYOUT_C)
	private int averageTime;
	
	public static TotalPcLogonClock from(AggrPCLogonClock domain){
		if(domain != null){
			return new TotalPcLogonClock(
						domain.getTotalClock() == null ? 0 : domain.getTotalClock().valueAsMinutes(), 
						domain.getTotalDays() == null ? 0 : domain.getTotalDays().v(), 
						domain.getAverageClock() == null ? 0 : domain.getAverageClock().valueAsMinutes());
		}
		return null;
	}
	
	public AggrPCLogonClock toDomain(){
		return AggrPCLogonClock.of(new AttendanceDaysMonth(totalDays), 
									new AttendanceTimeMonth(totalTime), 
									new AttendanceTimeMonth(averageTime));
	}
}
