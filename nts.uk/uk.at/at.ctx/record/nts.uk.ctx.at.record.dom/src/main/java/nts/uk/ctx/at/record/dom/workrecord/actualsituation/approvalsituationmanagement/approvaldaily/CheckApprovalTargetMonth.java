package nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.approvaldaily;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.export.CheckEmployeeUseApprovalMonth;

/**
 * @author thanhnx
 * 対象月の承認が済んでいるかチェックする
 *
 */
@Stateless
public class CheckApprovalTargetMonth {
	
	@Inject
	private CheckEmployeeUseApprovalMonth checkEmployeeUseApprovalMonth;
	
   public void checkApprovalTargetMonth(String employeeId, GeneralDate date){
	   boolean check = checkEmployeeUseApprovalMonth.checkEmployeeUseApprovalTargetMonth(employeeId, date);
	   if(!check){
		   //TODO 対象月の本人確認が済んでいるかチェックする
		   
	   }
   }
}
