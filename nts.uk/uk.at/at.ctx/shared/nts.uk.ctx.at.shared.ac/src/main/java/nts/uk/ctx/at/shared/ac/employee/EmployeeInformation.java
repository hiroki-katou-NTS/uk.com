package nts.uk.ctx.at.shared.ac.employee;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

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
	
	/** The business name Kana. */
	String businessNameKana; // ビジネスネームカナ

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
	
	int gender;
}