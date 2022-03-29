package nts.uk.ctx.at.record.dom.adapter.workplace;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 * 
 *         全ての職場の所属社員を取得するAdapter
 *
 */
public interface GetAllEmployeeWithWorkplaceAdapter {
	/**
	 * @name 取得する
	 * @param employeeId 	社員ID		社員ID
	 * @param baseDate 	基準日	年月日
	 * @return 	参照可能職場
	 */
	ReferenceableWorkplaceImport get(String employeeId, GeneralDate baseDate);
}
