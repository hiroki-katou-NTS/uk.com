/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

/**
 * The Class EmployeeInformationExport.
 */
// 社員情報
@Builder
@Data
public class EmployeeInformationExport {

	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

	/** The workplace. */
	Optional<String> workplace; // 所属職場

	/** The classification. */
	Optional<String> classification; // 所属分類

	/** The department. */
	Optional<String> department; // 所属部門

	/** The position. */
	Optional<String> position; // 所属職位

	/** The employment. */
	Optional<String> employment; // 所属雇用

	/** The employment cls. */
	Optional<Integer> employmentCls; // 就業区分
}
