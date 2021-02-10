package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;

/**
 * 社員がログインできるかチェックする
 */
public class CheckEmployeeAvailability {

	public static void check(Require require, IdentifiedEmployeeInfo identified) {

		val employee = identified.getEmployee();
		
		// ログインできるユーザかチェックする
		CheckUserAvailability.check(require, identified);
		
		// 社員の入退職状況をチェックする
		
		// 社員の所属情報が正しいかチェックする
//		if(!employee.getSyaEmpHist().isPresent() 
//		|| !employee.getSyaJobHist().isPresent() 
//		|| !employee.getSyaWkpHist().isPresent()) {
//			throw new BusinessException("Msg_1420");
//		}
		
	}
		
	public static interface Require extends 
			AccountLockPolicy.Require, 
			CheckUserAvailability.Require {
		
	}
}
