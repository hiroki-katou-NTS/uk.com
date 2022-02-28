package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.employeebasic;

import java.util.Optional;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;

/**
 * 外部受入内で個人IDを特定してくれる人
 */
public class PersonIdIdentifier {

	public static String getPersonId(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context, String employeeId) {
		if(isImportingWithEmployeeBasic(require, context)) {
			return EmployeeBasicCanonicalization.getPIDFromCanonicalizedData(require, context, employeeId).get();
		}
		return require.getEmployeeDataMngInfoByEmployeeId(employeeId)
				.get()
				.getPersonId();
	}
	
	public interface GetPersonIdRequire extends ImportingWithEmployeeBasicRequire,
																				EmployeeBasicCanonicalization.GetCanonicalizedDataRequire{
		Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeId(String employeeId);
	}
	
	
	private static boolean isImportingWithEmployeeBasic(DomainCanonicalization.RequireCanonicalize require, ExecutionContext context) {
		return require.getExternalImportSetting(context).containEmployeeBasic()
			  && !context.getDomainId().equals(ImportingDomainId.EMPLOYEE_BASIC);
	}
	
	public interface ImportingWithEmployeeBasicRequire{
		ExternalImportSetting getExternalImportSetting(ExecutionContext context);
	}
}
