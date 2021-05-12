package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.ksu001.aggreratepersonaltotal.AggreratePersonalDto;
import nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal.AggrerateWorkplaceDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleActualOfShiftOutput {

	// List<勤務予定（シフト）dto>
	public List<ScheduleOfShiftDto> listWorkScheduleShift;    
	
	// // Map<シフトマスタ, Optional<出勤休日区分>>
	public Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle; 
	
	// 個人計集計結果　←集計内容によって情報が異なる
	public AggreratePersonalDto aggreratePersonal;
	
	// ・職場計集計結果　←集計内容によって情報が異なる
	public AggrerateWorkplaceDto aggrerateWorkplace;
	
}
