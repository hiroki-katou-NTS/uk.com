/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.query.model.classification.ClassificationModel;
import nts.uk.query.model.department.DepartmentModel;
import nts.uk.query.model.employement.EmploymentModel;
import nts.uk.query.model.position.PositionModel;
import nts.uk.query.model.workplace.WorkplaceModel;

/**
 * The Class EmployeeInformation.
 */
// 社員情報
@Data
@Builder
public class EmployeeInformation {

	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

	/** The workplace. */
	Optional<WorkplaceModel> workplace; // 所属職場

	/** The classification. */
	Optional<ClassificationModel> classification; // 所属分類

	/** The department. */
	Optional<DepartmentModel> department; // 所属部門

	/** The position. */
	Optional<PositionModel> position; // 所属職位

	/** The employment. */
	Optional<EmploymentModel> employment; // 所属雇用

	/** The employment cls. */
	Optional<Integer> employmentCls; // 就業区分
}
