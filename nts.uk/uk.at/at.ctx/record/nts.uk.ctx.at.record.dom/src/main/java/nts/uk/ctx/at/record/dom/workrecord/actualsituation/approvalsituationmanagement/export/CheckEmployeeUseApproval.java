package nts.uk.ctx.at.record.dom.workrecord.actualsituation.approvalsituationmanagement.export;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;

/**
 * @author thanhnx
 * 社員が日の承認処理を利用できるかチェックする
 *
 */
@Stateless
public class CheckEmployeeUseApproval {
   public boolean checkEmployeeUseApproval(String employeeId, GeneralDate date){
	   return false;
   }
}
