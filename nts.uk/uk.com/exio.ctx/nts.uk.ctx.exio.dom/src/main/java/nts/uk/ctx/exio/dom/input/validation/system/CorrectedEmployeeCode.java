package nts.uk.ctx.exio.dom.input.validation.system;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.employee.dom.setting.code.EmployeeCESetting;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * 社員コードを社員コードの編集設定に基づき補正する
 */
public class CorrectedEmployeeCode {
	
	private final static String employeeCodeFQN = EmployeeCode.class.getName();
	
	public static RevisedDataRecord correct(EmployeeCodeValidateRequire require, ExecutionContext context, RevisedDataRecord record) {
		return record.getItems().stream()
			.filter(item -> isEmployeeCodePrimitiveValue(require, context, item))
			.findFirst()
			.map(target -> {
				val correctedData = new DataItem(target.getItemNo(), getCorrectedEmployeeCode(require, context, target.getString()));
				return record.replace(correctedData); 
			})
			.orElse(record);
	}
	
	/**
	 *  社員コードの編集設定に基づいて社員コードを補正する 
	 */
	private static String getCorrectedEmployeeCode(EmployeeCodeValidateRequire require, ExecutionContext context, String employeeCode) {
		return require.getByComId(context.getCompanyId())
				.get()
				.editEmployeeCode(employeeCode);
	}
	
	private static boolean isEmployeeCodePrimitiveValue(EmployeeCodeValidateRequire require, ExecutionContext context, DataItem item) {
		return require.getImportableItem(context.getDomainId(), item.getItemNo())
				.getDomainConstraint()
				.map(t -> t.getFqn().equals(employeeCodeFQN))
				.orElse(false);
	}
	
	public static interface EmployeeCodeValidateRequire{
		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);

		Optional<EmployeeCESetting> getByComId(String companyId);
	}
}
