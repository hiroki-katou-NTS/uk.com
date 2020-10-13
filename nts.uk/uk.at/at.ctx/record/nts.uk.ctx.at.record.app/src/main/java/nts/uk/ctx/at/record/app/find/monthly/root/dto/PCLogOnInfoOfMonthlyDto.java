package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.PCLogonClockOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.PCLogonDivergenceOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.PCLogonOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン情報 */
public class PCLogOnInfoOfMonthlyDto implements ItemConst {

	/** PCログオン時刻: 月別実績のPCログオン時刻 */
	@AttendanceItemLayout(jpPropertyName = CLOCK, layout = LAYOUT_A)
	private PCLogOnClockOfMonthly pcLogOnTime;

	@AttendanceItemLayout(jpPropertyName = DIVERGENCE, layout = LAYOUT_B)
	/** PCログオン乖離: 月別実績のPCログオン乖離 */
	private PCLogOnDivergenceOfMonthly pcLogOnDivergence;
	
	public static PCLogOnInfoOfMonthlyDto from (PCLogonOfMonthly domain){
		if(domain != null){
			return new PCLogOnInfoOfMonthlyDto(PCLogOnClockOfMonthly.from(domain.getLogonClock()), 
					PCLogOnDivergenceOfMonthly.from(domain.getLogonDivergence()));
		}
		return null;
	} 
	
	public PCLogonOfMonthly toDomain(){
		return PCLogonOfMonthly.of(pcLogOnTime == null ? new PCLogonClockOfMonthly() : pcLogOnTime.toDomain(), 
									pcLogOnDivergence == null ? new PCLogonDivergenceOfMonthly() : pcLogOnDivergence.toDomain());
	}
}
