package nts.uk.ctx.hr.shared.dom.adapter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeInformationImport {
	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

	/** The business name Kana. */
	String businessNameKana; // ビジネスネームカナ

	/** The workplace. */
	WorkplaceImport workplace; // 所属職場

	/** The classification. */
	ClassificationImport classification; // 所属分類

	/** The department. */
	DepartmentImport department; // 所属部門

	/** The position. */
	PositionImport position; // 所属職位

	/** The employment. */
	EmploymentImport employment; // 所属雇用

	/** The employment cls. */
	Integer employmentCls; // 就業区分

	public EmployeeInformationImport(String employeeId, String employeeCode, String businessName,
			String businessNameKana, WorkplaceImport workplace, ClassificationImport classification,
			DepartmentImport department, PositionImport position, EmploymentImport employment, Integer employmentCls) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.businessName = businessName;
		this.businessNameKana = businessNameKana;
		this.workplace = workplace;
		this.classification = classification;
		this.department = department;
		this.position = position;
		this.employment = employment;
		this.employmentCls = employmentCls;
	}
	
}
