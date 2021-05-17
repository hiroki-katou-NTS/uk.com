package nts.uk.screen.at.app.ksu001.getsendingperiod;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.screen.at.app.ksu001.aggreratepersonaltotal.AggreratePersonalDto;
import nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal.AggrerateWorkplaceDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePeriodInShiftResult_New {
	
	public List<EmployeeInformationImport> listEmpInfo;
	
	public DataSpecDateAndHolidayDto dataSpecDateAndHolidayDto;
	
	// 個人計集計結果　←集計内容によって情報が異なる
	public AggreratePersonalDto aggreratePersonal;
	
	// ・職場計集計結果　←集計内容によって情報が異なる
	public AggrerateWorkplaceDto aggrerateWorkplace;
	
	// Map<シフトマスタ, Optional<出勤休日区分>>
	public Map<ShiftMasterDto, Integer> mapShiftMasterWithWorkStyle; 
	
	// List<勤務予定（シフト）dto>
	public List<ScheduleOfShiftDto> listWorkScheduleShift; 
}
