package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonClock;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 集計PCログオン時刻 + 集計PCログオン乖離 */
public class TotalPcLogon implements ItemConst {

	/** 合計時刻: 勤怠月間時間 + 合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TOTAL + CLOCK, layout = LAYOUT_A)
	private Integer totalTime;

	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = TOTAL + DAYS, layout = LAYOUT_B)
	private Double totalDays;

	/** 平均時刻: 勤怠月間時間 + 平均時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = AVERAGE + CLOCK, layout = LAYOUT_C)
	private Integer averageTime;
	
	public static TotalPcLogon from(AggrPCLogonClock domain){
		if(domain != null){
			return new TotalPcLogon(
						domain.getTotalClock() == null ? null : domain.getTotalClock().valueAsMinutes(), 
						domain.getTotalDays() == null ? null : domain.getTotalDays().v(), 
						domain.getAverageClock() == null ? null : domain.getAverageClock().valueAsMinutes());
		}
		return null;
	}
	
	public static TotalPcLogon from(AggrPCLogonDivergence domain){
		if(domain != null){
			return new TotalPcLogon(
						domain.getTotalTime() == null ? null : domain.getTotalTime().valueAsMinutes(), 
						domain.getDays() == null ? null : domain.getDays().v(), 
						domain.getAverageTime() == null ? null : domain.getAverageTime().valueAsMinutes());
		}
		return null;
	}
	
	public AggrPCLogonClock toDomain(){
		return AggrPCLogonClock.of(totalDays == null ? null : new AttendanceDaysMonth(totalDays), 
									totalTime == null ? null : new AttendanceTimeMonth(totalTime), 
									averageTime == null ? null : new AttendanceTimeMonth(averageTime));
	}
	
	public AggrPCLogonDivergence toDivergenceDomain(){
		return AggrPCLogonDivergence.of(totalDays == null ? null : new AttendanceDaysMonth(totalDays), 
									totalTime == null ? null : new AttendanceTimeMonth(totalTime), 
									averageTime == null ? null : new AttendanceTimeMonth(averageTime));
	}
}
