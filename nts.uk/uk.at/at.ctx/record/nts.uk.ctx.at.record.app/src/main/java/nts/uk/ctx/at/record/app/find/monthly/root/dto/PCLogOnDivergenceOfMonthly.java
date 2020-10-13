package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.AggrPCLogonDivergence;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workclock.pclogon.PCLogonDivergenceOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績のPCログオン時刻 + 月別実績のPCログオン乖離 */
public class PCLogOnDivergenceOfMonthly implements ItemConst{

	/** PCログオフ時刻: 集計PCログオン時刻 + PCログオフ乖離: 集計PCログオン乖離 */
	@AttendanceItemLayout(jpPropertyName = LOGOFF, layout = LAYOUT_A)
	private TotalPcLogonTime logOff;

	/** PCログオン時刻: 集計PCログオン時刻 + PCログオン乖離: 集計PCログオン乖離 */
	@AttendanceItemLayout(jpPropertyName = LOGON, layout = LAYOUT_B)
	private TotalPcLogonTime logOn;
	
	public static PCLogOnDivergenceOfMonthly from(PCLogonDivergenceOfMonthly domain){
		if(domain != null){
			return new PCLogOnDivergenceOfMonthly(TotalPcLogonTime.from(domain.getLogoffDivergence()), 
					TotalPcLogonTime.from(domain.getLogonDivergence()));
		}
		return null;
	}
	
	public PCLogonDivergenceOfMonthly toDomain(){
		return PCLogonDivergenceOfMonthly.of(logOn == null ? new AggrPCLogonDivergence() : logOn.toDomain(), 
										logOff == null ? new AggrPCLogonDivergence() : logOff.toDomain());
	}
}
