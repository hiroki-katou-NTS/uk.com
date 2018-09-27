package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 集計PCログオン乖離 */
public class TotalPcLogonTime implements ItemConst {

	/** 合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL, layout = LAYOUT_A)
	private int totalTime;

	/** 合計日数: 勤怠月間日数 + 日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B)
	private double totalDays;

	/** 平均時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = AVERAGE , layout = LAYOUT_C)
	private int averageTime;
	
	public static TotalPcLogonTime from(AggrPCLogonDivergence domain){
		if(domain != null){
			return new TotalPcLogonTime(
						domain.getTotalTime() == null ? 0 : domain.getTotalTime().valueAsMinutes(), 
						domain.getDays() == null ? 0 : domain.getDays().v(), 
						domain.getAverageTime() == null ? 0 : domain.getAverageTime().valueAsMinutes());
		}
		return null;
	}
	
	public AggrPCLogonDivergence toDomain(){
		return AggrPCLogonDivergence.of(new AttendanceDaysMonth(totalDays), 
									new AttendanceTimeMonth(totalTime), 
									new AttendanceTimeMonth(averageTime));
	}
}
