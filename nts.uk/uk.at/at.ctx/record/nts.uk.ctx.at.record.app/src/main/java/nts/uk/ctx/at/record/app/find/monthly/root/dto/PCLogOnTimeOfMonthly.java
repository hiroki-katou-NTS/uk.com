package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonClock;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonClockOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workclock.pclogon.PCLogonDivergenceOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン時刻 + 月別実績のPCログオン乖離 */
public class PCLogOnTimeOfMonthly {

	/** PCログオフ時刻: 集計PCログオン時刻 + PCログオフ乖離: 集計PCログオン乖離 */
	@AttendanceItemLayout(jpPropertyName = "PCログオフ", layout = "A")
	private TotalPcLogon logOff;

	/** PCログオン時刻: 集計PCログオン時刻 + PCログオン乖離: 集計PCログオン乖離 */
	@AttendanceItemLayout(jpPropertyName = "PCログオン", layout = "B")
	private TotalPcLogon logOn;
	
	public static PCLogOnTimeOfMonthly from(PCLogonClockOfMonthly domain){
		if(domain != null){
			return new PCLogOnTimeOfMonthly(TotalPcLogon.from(domain.getLogoffClock()), 
											TotalPcLogon.from(domain.getLogonClock()));
		}
		return null;
	}
	
	public static PCLogOnTimeOfMonthly from(PCLogonDivergenceOfMonthly domain){
		if(domain != null){
			return new PCLogOnTimeOfMonthly(TotalPcLogon.from(domain.getLogoffDivergence()), 
											TotalPcLogon.from(domain.getLogonDivergence()));
		}
		return null;
	}
	
	public PCLogonClockOfMonthly toDomain(){
		return PCLogonClockOfMonthly.of(logOn == null ? new AggrPCLogonClock() : logOn.toDomain(), 
										logOff == null ? new AggrPCLogonClock() : logOff.toDomain());
	}
	
	public PCLogonDivergenceOfMonthly toDivergenceDomain(){
		return PCLogonDivergenceOfMonthly.of(logOn == null ? new AggrPCLogonDivergence() : logOn.toDivergenceDomain(), 
										logOff == null ? new AggrPCLogonDivergence() : logOff.toDivergenceDomain());
	}
}
