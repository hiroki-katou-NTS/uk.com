package nts.uk.ctx.at.function.dom.adapter.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeInformationImport {
	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

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
	
	/**
	- 1: Go to support
	- 2: Come to support
	- 0: Do not go to support */
	int supportType; // 応援タイプ

	public EmployeeInformationImport(String employeeId, String employeeCode, String businessName,
			WorkplaceImport workplace, ClassificationImport classification, DepartmentImport department,
			PositionImport position, EmploymentImport employment, Integer employmentCls) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.businessName = businessName;
		this.workplace = workplace;
		this.classification = classification;
		this.department = department;
		this.position = position;
		this.employment = employment;
		this.employmentCls = employmentCls;
	}
}
