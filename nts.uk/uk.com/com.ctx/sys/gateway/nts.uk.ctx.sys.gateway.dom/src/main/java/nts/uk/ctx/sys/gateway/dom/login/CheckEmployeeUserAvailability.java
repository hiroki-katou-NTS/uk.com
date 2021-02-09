package nts.uk.ctx.sys.gateway.dom.login;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.AccountLockPolicy;

/**
 * 社員がログインできるかチェックする
 */
public class CheckEmployeeUserAvailability {

	public static void check(Require require, IdentifiedEmployeeInfo identified) {

		val employee = identified.getEmployee();
		
		// 社員が削除されていないかチェック
		if(employee.isDeleted()) {
			throw new BusinessException("Msg_176");
		}
		
		// ユーザの有効期限が切れていないかチェック
		if(identified.getUser().isAvailableAt(GeneralDate.today())) {
			throw new BusinessException("Msg_316");
		}
		
		// 社員の所属情報が存在しているかチェック
//		if(!employee.getSyaEmpHist().isPresent() 
//		|| !employee.getSyaJobHist().isPresent() 
//		|| !employee.getSyaWkpHist().isPresent()) {
//			throw new BusinessException("Msg_1420");
//		}
		
		// アカウントロック
		String tenantCode = identified.getTenantCode();
		String userId = identified.getUserId();
		require.getAccountLockPolicy(tenantCode)
				.ifPresent(policy -> {
					if (policy.isLocked(require, userId)) {
						throw new BusinessException(new RawErrorMessage(policy.getLockOutMessage().v()));		
					}
				});
	}
		
	public static interface Require extends 
			AccountLockPolicy.RequireIsLocked {

		Optional<AccountLockPolicy> getAccountLockPolicy(String tenantCode);
		
	}
}
