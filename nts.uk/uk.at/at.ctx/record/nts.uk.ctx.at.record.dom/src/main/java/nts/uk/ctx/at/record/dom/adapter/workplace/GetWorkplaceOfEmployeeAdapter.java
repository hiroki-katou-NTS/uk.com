package nts.uk.ctx.at.record.dom.adapter.workplace;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnlb
 * 
 *         参照可能社員の所属職場を取得するAdapter
 */
public interface GetWorkplaceOfEmployeeAdapter {
	/**
	 * @name 取得する
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 */
	ReferenceableWorkplaceImport get(String userID, String employeeID, GeneralDate date);
}
