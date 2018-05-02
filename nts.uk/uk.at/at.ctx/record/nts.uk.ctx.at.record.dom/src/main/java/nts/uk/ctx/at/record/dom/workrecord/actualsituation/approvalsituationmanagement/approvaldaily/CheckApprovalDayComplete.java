package nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.approvaldaily;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.export.CheckEmployeeUseApproval;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.param.ApprovalDayComplete;

/**
 * @author thanhnx
 * 対象期間の日の承認が済んでいるかチェックする
 *
 */
@Stateless
public class CheckApprovalDayComplete {
   
	@Inject
	private CheckEmployeeUseApproval checkEmployeeUseApproval;
	
	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;
	
	public Optional<ApprovalDayComplete> checkApprovalDayComplete(String employeeId, GeneralDate date){
		//TODO 社員が日の承認処理を利用できるかチェックする
		boolean checkUse = checkEmployeeUseApproval.checkEmployeeUseApproval(employeeId, date);
		if(!checkUse){
		  return Optional.of(new ApprovalDayComplete(true, Collections.emptyList()));
		}
		//TODO 対応するImported「（就業．勤務実績）承認対象者の承認状況」をすべて取得する
		List<ApproveRootStatusForEmpImport> appRoots = approvalStatusAdapter.getApprovalByListEmplAndListApprovalRecordDate(Arrays.asList(date), Arrays.asList(employeeId), 1);
		
		List<GeneralDate> dates = appRoots.stream().filter(x -> x.getApprovalStatus() != ApprovalStatusForEmployee.APPROVED).map(x -> x.getAppDate()).collect(Collectors.toList());
		if(dates.isEmpty())  return Optional.of(new ApprovalDayComplete(true, Collections.emptyList()));
		return Optional.of(new ApprovalDayComplete(true, dates));
	}
}
