package nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.approvaldaily;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.export.CheckEmployeeUseApproval;

/**
 * @author thanhnx
 * 対象期間の日の承認が済んでいるかチェックする
 *
 */
@Stateless
public class CheckApprovalDayComplete {
   
	@Inject
	private CheckEmployeeUseApproval checkEmployeeUseApproval;
	
	public void checkApprovalDayComplete(String employeeId, GeneralDate date){
		//TODO 社員が日の承認処理を利用できるかチェックする
		boolean checkUse = checkEmployeeUseApproval.checkEmployeeUseApproval(employeeId, date);
		if(!checkUse){
		//TODO 対象日の本人確認が済んでいるかチェックする	
		}
		
	}
}
