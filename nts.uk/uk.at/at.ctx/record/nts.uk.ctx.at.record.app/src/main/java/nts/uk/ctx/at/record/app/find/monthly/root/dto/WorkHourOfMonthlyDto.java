package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.EndClockOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.WorkClockOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.PCLogonOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時刻 */
public class WorkHourOfMonthlyDto implements ItemConst {

	/** PCログオン情報: 月別実績のPCログオン情報 */
	@AttendanceItemLayout(jpPropertyName = PC, layout = LAYOUT_A)
	private PCLogOnInfoOfMonthlyDto pcLogOnInfo;

	/** 終業時刻: 月別実績の終業時刻 */
	@AttendanceItemLayout(jpPropertyName = END_WORK, layout = LAYOUT_B)
	private EndWorkHourOfMonthlyDto endWorkHours;
	
	public static WorkHourOfMonthlyDto from(WorkClockOfMonthly domain){
		if(domain != null){
			return new WorkHourOfMonthlyDto(PCLogOnInfoOfMonthlyDto.from(domain.getLogonInfo()), 
											EndWorkHourOfMonthlyDto.from(domain.getEndClock()));
		}
		return null;
	}
	
	public WorkClockOfMonthly toDomain(){
		return WorkClockOfMonthly.of(endWorkHours == null ? new EndClockOfMonthly() : endWorkHours.toDomain(), 
									pcLogOnInfo == null ? new PCLogonOfMonthly() : pcLogOnInfo.toDomain());
	}
}
