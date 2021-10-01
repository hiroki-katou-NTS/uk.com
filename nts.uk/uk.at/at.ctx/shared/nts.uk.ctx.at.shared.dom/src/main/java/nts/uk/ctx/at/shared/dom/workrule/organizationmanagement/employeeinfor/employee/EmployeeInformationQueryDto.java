package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * The Class EmployeeInformationQueryDto.
 */
// 社員情報
@Data
@Builder
public class EmployeeInformationQueryDto {

	/** The employee ids. */
	List<String> employeeIds; // 社員一覧

	/** The reference date. */
	GeneralDate referenceDate; // 年月日

	/** The to get workplace. */
	boolean toGetWorkplace; // 職場を取得する

	/** The to get department. */
	boolean toGetDepartment; // 部門を取得する

	/** The to get position. */
	boolean toGetPosition; // 職位を取得する

	/** The to get employment. */
	boolean toGetEmployment; // 雇用を取得する

	/** The to get classification. */
	boolean toGetClassification; // 分類を取得する

	/** The to get employment cls. */
	boolean toGetEmploymentCls; // 就業区分を取得する

	public EmployeeInformationQueryDto(List<String> employeeIds, GeneralDate referenceDate, boolean toGetWorkplace,
			boolean toGetDepartment, boolean toGetPosition, boolean toGetEmployment, boolean toGetClassification,
			boolean toGetEmploymentCls) {
		super();
		this.employeeIds = employeeIds;
		this.referenceDate = referenceDate;
		this.toGetWorkplace = toGetWorkplace;
		this.toGetDepartment = toGetDepartment;
		this.toGetPosition = toGetPosition;
		this.toGetEmployment = toGetEmployment;
		this.toGetClassification = toGetClassification;
		this.toGetEmploymentCls = toGetEmploymentCls;
	}

}