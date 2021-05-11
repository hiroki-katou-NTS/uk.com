package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.login.IdentifiedEmployeeInfo;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.sys.shared.dom.user.User;

/**
 * ログイン社員を識別する
 */
public class EmployeeIdentify {
	
	/**
	 * 社員コードにより識別する
	 * @param require
	 * @param companyId
	 * @param employeeCode
	 * @return
	 */
	public static IdentificationResult identifyByEmployeeCode(Require require, String companyId, String employeeCode) {
		val employee = require.getEmployeeDataMngInfoImportByEmployeeCode(companyId, employeeCode);
		// 社員コードから社員を特定できない
		if(!employee.isPresent()) {
			return identifyFailure(require, companyId, employeeCode);
		}
		val user = require.getUserByPersonId(employee.get().getPersonId());
		
		// 個人IDからユーザを特定できない or 有効期限が切れている
		if (!user.isPresent() || !user.get().isAvailableAt(GeneralDate.today())) {
			return identifyFailure(require, companyId, employeeCode);
		}
		
		// 社員、ユーザの特定に成功
		return IdentificationResult.success(employee.get(), user.get());
	}
	
	// 識別に失敗
	private static IdentificationResult identifyFailure(Require require, String companyId, String employeeCode) {
		val failureLog = AtomTask.of(() -> {
			require.addFailureLog(PasswordAuthIdentificationFailureLog.create(companyId, employeeCode));
		});
		return IdentificationResult.failure(failureLog);
	}
	
	/**
	 * 識別結果
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	@ToString
	public static class IdentificationResult {
		
		/** 識別成功 */
		private final boolean identificationSuccess;
		
		/** 識別された社員(識別成功時のみ) */
		private final Optional<IdentifiedEmployeeInfo> employeeInfo;
		
		/** 識別失敗記録の永続化処理 */
		private final Optional<AtomTask> failureLog;
		
		/**
		 * 識別成功
		 * @param EmployeeDataMngInfoImport
		 * @param User
		 * @return IdentificationResult
		 */
		public static IdentificationResult success(EmployeeDataMngInfoImport employee, User user) {
			return new IdentificationResult(
					true, 
					Optional.of(new IdentifiedEmployeeInfo(employee, user)), 
					Optional.empty());
		}
		
		/**
		 * 識別失敗
		 * @param AtomTask
		 * @return IdentificationResult
		 */
		public static IdentificationResult failure(AtomTask failureLog) {
			
			return new IdentificationResult(
					false, 
					Optional.empty(), 
					Optional.of(failureLog));
		}
		
		public boolean isSuccess() {
			return this.identificationSuccess;
		}

		public boolean isFailure() {
			return !this.isSuccess();
		}
		
		public IdentifiedEmployeeInfo getEmployeeInfo() {
			// 社員情報が取得できていない時（失敗時時）にはそもそもこのメソッドを呼ぶべきではないのでifPresentは行わない
			return employeeInfo.get();
		}
		
		public AtomTask getAtomTask() {
			AtomTask atomTasks = AtomTask.none();
			if(failureLog.isPresent()) {
				atomTasks = atomTasks.then(failureLog.get());
			}
			return atomTasks;
		}
	}
	
	public static interface Require{
		Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeCode(String companyId, String employeeCode);
		
		Optional<User> getUserByPersonId(String personId);
		
		void addFailureLog(PasswordAuthIdentificationFailureLog failurLog);
	}
}
