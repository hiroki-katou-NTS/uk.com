package nts.uk.ctx.sys.gateway.dom.login.password.identification;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.val;
import nts.arc.task.tran.AtomTask;
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
	public static IdentificationResult identifyByEmployeeCode(RequireByEmployeeCode require, String companyId, String employeeCode) {

		val employee = require.getEmployeeDataMngInfoImportByEmployeeCode(companyId, employeeCode);

		return identify(require, employee, () -> AtomTask.of(() -> {
			require.addFailureLog(PasswordAuthIdentificationFailureLog.create(companyId, employeeCode));
		}));
	}

	/**
	 * 社員IDにより識別する
	 * @param require
	 * @param employeeId
	 * @return
	 */
	public static IdentificationResult identifyByEmployeeId(RequireByEmployeeId require, String employeeId) {

		val employee = require.getEmployeeDataMngInfoImportByEmployeeId(employeeId);

		return identify(require, employee, AtomTask::none);
	}

	private static IdentificationResult identify(
			RequireCommon require,
			Optional<EmployeeDataMngInfoImport> employee,
			Supplier<AtomTask> atomTaskFailure) {

		// 社員を特定できない
		if(!employee.isPresent()) {
			return IdentificationResult.failure(atomTaskFailure.get());
		}

		val user = require.getUserByPersonId(employee.get().getPersonId())
				.orElseThrow(() -> new RuntimeException("not found user, person ID: " + employee.get().getPersonId()));

		// 社員、ユーザの特定に成功
		return IdentificationResult.success(employee.get(), user);
	}


	// 識別に失敗
	private static IdentificationResult identifyFailure(RequireByEmployeeCode requireByEmployeeCode, String companyId, String employeeCode) {
		val failureLog = AtomTask.of(() -> {
			requireByEmployeeCode.addFailureLog(PasswordAuthIdentificationFailureLog.create(companyId, employeeCode));
		});
		return IdentificationResult.failure(failureLog);
	}
	
	public interface RequireByEmployeeCode extends RequireCommon {

		Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeCode(String companyId, String employeeCode);

		void addFailureLog(PasswordAuthIdentificationFailureLog failurLog);
	}

	public interface RequireByEmployeeId extends RequireCommon {

		Optional<EmployeeDataMngInfoImport> getEmployeeDataMngInfoImportByEmployeeId(String employeeId);
	}

	public interface RequireCommon {

		Optional<User> getUserByPersonId(String personId);
	}
}
