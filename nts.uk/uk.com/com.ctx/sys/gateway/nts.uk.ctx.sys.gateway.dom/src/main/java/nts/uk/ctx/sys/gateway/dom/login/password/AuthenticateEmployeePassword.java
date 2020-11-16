package nts.uk.ctx.sys.gateway.dom.login.password;

import lombok.Value;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.sys.shared.dom.user.FindUser;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * 社員コードとパスワードで認証する
 */
public class AuthenticateEmployeePassword {

	public static Result authenticate(Require require, String companyId, String employeeCode, String password) {
		
		User user;
		{
			val userOpt = FindUser.byEmployeeCode(require, companyId, employeeCode);
			
			if (!userOpt.isPresent()) {
				// 識別失敗
				return Result.failed();
			}
			
			user = userOpt.get();
		}
		
		if (!user.comparePassword(password)) {
			// 認証失敗
			val atomTask = FailedAuthenticateEmployeePassword.failed(require, user.getUserID());
			return Result.failed(atomTask);
		}
		
		return Result.succeeded();
	}
	
	public static interface Require extends
			FindUser.RequireByEmployeeCode,
			FailedAuthenticateEmployeePassword.Require {
		
	}
	
	@Value
	public static class Result {
		boolean isSuccess;
		AtomTask atomTask;
		
		public static Result succeeded() {
			return new Result(true, AtomTask.of(() -> {}));
		}
		
		public static Result failed() {
			return failed(AtomTask.of(() -> {}));
		}
		
		public static Result failed(AtomTask atomTask) {
			return new Result(false, atomTask);
		}
	}
}
