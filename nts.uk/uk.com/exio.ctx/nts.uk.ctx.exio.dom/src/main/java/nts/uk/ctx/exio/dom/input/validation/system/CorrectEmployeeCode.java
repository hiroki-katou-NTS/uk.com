package nts.uk.ctx.exio.dom.input.validation.system;

import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.employee.dom.setting.code.EmployeeCESetting;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;

import java.util.Optional;

/**
 * 社員コードを社員コードの編集設定に基づき補正する
 */
public class CorrectEmployeeCode {
	
	private final static String employeeCodeFQN = EmployeeCode.class.getName();

	public static DataItem correct(EmployeeCodeValidateRequire require, ExecutionContext context, DataItem item) {

		if (!isEmployeeCodePrimitiveValue(require, context, item)) {
			return item;
		}

		return new DataItem(item.getItemNo(), getCorrectedEmployeeCode(require, context, item.getString()));
	}
	
	/**
	 *  社員コードの編集設定に基づいて社員コードを補正する 
	 */
	private static String getCorrectedEmployeeCode(EmployeeCodeValidateRequire require, ExecutionContext context, String employeeCode) {
		return require.getEmployeeCESetting(context.getCompanyId())
				.get()
				.editEmployeeCode(employeeCode);
	}
	
	private static boolean isEmployeeCodePrimitiveValue(EmployeeCodeValidateRequire require, ExecutionContext context, DataItem item) {
		return require.getImportableItem(context.getDomainId(), item.getItemNo())
				.getDomainConstraint()
				.map(t -> t.getFqn().equals(employeeCodeFQN))
				.orElse(false);
	}
	
	public interface EmployeeCodeValidateRequire{
		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);

		Optional<EmployeeCESetting> getEmployeeCESetting(String companyId);
	}
}
