package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonClock;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 集計PCログオン時刻 + 集計PCログオン乖離 */
public class TotalPcLogon {

	/** 合計時刻: 勤怠月間時間 + 合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "合計時間", layout = "A")
	private int totalTime;

	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "合計日数", layout = "B")
	private double totalDays;

	/** 平均時刻: 勤怠月間時間 + 平均時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "平均時刻", layout = "C")
	private int averageTime;
	
	public static TotalPcLogon from(AggrPCLogonClock domain){
		if(domain != null){
			return new TotalPcLogon(
						domain.getTotalClock() == null ? 0 : domain.getTotalClock().valueAsMinutes(), 
						domain.getTotalDays() == null ? 0 : domain.getTotalDays().v(), 
						domain.getAverageClock() == null ? 0 : domain.getAverageClock().valueAsMinutes());
		}
		return null;
	}
	
	public static TotalPcLogon from(AggrPCLogonDivergence domain){
		if(domain != null){
			return new TotalPcLogon(
						domain.getTotalTime() == null ? 0 : domain.getTotalTime().valueAsMinutes(), 
						domain.getDays() == null ? 0 : domain.getDays().v(), 
						domain.getAverageTime() == null ? 0 : domain.getAverageTime().valueAsMinutes());
		}
		return null;
	}
	
	public AggrPCLogonClock toDomain(){
		return AggrPCLogonClock.of(new AttendanceDaysMonth(totalDays), 
									new AttendanceTimeMonth(totalTime), 
									new AttendanceTimeMonth(averageTime));
	}
	
	public AggrPCLogonDivergence toDivergenceDomain(){
		return AggrPCLogonDivergence.of(new AttendanceDaysMonth(totalDays), 
									new AttendanceTimeMonth(totalTime), 
									new AttendanceTimeMonth(averageTime));
	}
}
