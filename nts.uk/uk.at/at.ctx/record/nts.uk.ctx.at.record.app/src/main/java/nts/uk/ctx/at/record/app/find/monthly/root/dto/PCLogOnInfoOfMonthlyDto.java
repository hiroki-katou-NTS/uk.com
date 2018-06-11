package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン情報 */
public class PCLogOnInfoOfMonthlyDto implements ItemConst {

	/** PCログオン時刻: 月別実績のPCログオン時刻 */
	@AttendanceItemLayout(jpPropertyName = CLOCK, layout = LAYOUT_A)
	private PCLogOnTimeOfMonthly pcLogOnTime;

	@AttendanceItemLayout(jpPropertyName = DIVERGENCE, layout = LAYOUT_B)
	/** PCログオン乖離: 月別実績のPCログオン乖離 */
	private PCLogOnTimeOfMonthly pcLogOnDivergence;
	
	public static PCLogOnInfoOfMonthlyDto from (PCLogonOfMonthly domain){
		if(domain != null){
			return new PCLogOnInfoOfMonthlyDto(PCLogOnTimeOfMonthly.from(domain.getLogonClock()), 
												PCLogOnTimeOfMonthly.from(domain.getLogonDivergence()));
		}
		return null;
	} 
	
	public PCLogonOfMonthly toDomain(){
		return PCLogonOfMonthly.of(pcLogOnTime == null ? null : pcLogOnTime.toDomain(), 
									pcLogOnDivergence == null ? null : pcLogOnDivergence.toDivergenceDomain());
	}
}
