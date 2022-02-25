package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.employeebasic;

import java.util.Optional;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethodRequire;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

/**
 * 外部受入内で社員IDを特定してくれる人
 */
public class EmployeeIdIdentifier {

	public static Optional<String> getEmployeeId(CanonicalizationMethodRequire require, ExecutionContext context, String employeeCode) {
		if(isImportingWithEmployeeBasic(require, context)) {
			return EmployeeBasicCanonicalization.getSIDFromCanonicalizedData(require, context, employeeCode);
		}
		return require.getEmployeeDataMngInfoByEmployeeCode(employeeCode)
				.map(c -> c.getEmployeeId());
	}
	
	public interface GetEmployeeIdRequire extends ImportingWithEmployeeBasicRequire,
																					EmployeeBasicCanonicalization.GetCanonicalizedDataRequire{
		Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeCode(String employeeCode);
	}
	
	private static boolean isImportingWithEmployeeBasic(CanonicalizationMethodRequire require, ExecutionContext context) {
		return require.getExternalImportSetting(context).containEmployeeBasic()
			  && !context.getDomainId().equals(ImportingDomainId.EMPLOYEE_BASIC);
	}
	
	private interface ImportingWithEmployeeBasicRequire{
		ExternalImportSetting getExternalImportSetting(ExecutionContext context);
	}
}
