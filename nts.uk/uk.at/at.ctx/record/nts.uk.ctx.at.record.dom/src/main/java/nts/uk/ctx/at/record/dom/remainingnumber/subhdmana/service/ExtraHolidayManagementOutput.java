package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

@AllArgsConstructor
@Getter
public class ExtraHolidayManagementOutput {
	private List<LeaveManagementData> listLeaveData;
	private List<CompensatoryDayOffManaData> listCompensatoryData;
	private List<LeaveComDayOffManagement> listLeaveComDayOffManagement;
	private SEmpHistoryImport sEmpHistoryImport; 
	private ClosureEmployment closureEmploy; 
}
