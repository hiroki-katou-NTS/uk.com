package nts.uk.file.at.app.export.yearholidaymanagement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;
import nts.uk.query.pub.classification.ClassificationExport;
import nts.uk.query.pub.department.DepartmentExport;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.employement.EmploymentExport;
import nts.uk.query.pub.position.PositionExport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHolidayInformationExport {

	/** The employee id. */
	String employeeId; // 社員ID

	/** The employee code. */
	String employeeCode; // 社員コード

	/** The business name. */
	String businessName; // ビジネスネーム

	/** The workplace. */
	WorkplaceHolidayExport workplace; // 所属職場

	/** The classification. */
	ClassificationExport classification; // 所属分類

	/** The department. */
	DepartmentExport department; // 所属部門

	/** The position. */
	PositionExport position; // 所属職位

	/** The employment. */
	EmploymentExport employment; // 所属雇用

	/** The employment cls. */
	Integer employmentCls; // 就業区分

	Optional<AnnualHolidayGrantInfor> holidayInfo;

	List<AnnualHolidayGrantDetail> HolidayDetails;

	public static EmployeeHolidayInformationExport fromEmpId(String employeeId) {
		EmployeeHolidayInformationExport result = new EmployeeHolidayInformationExport();
		result.setEmployeeId(employeeId);
		return result;
	}

	public static EmployeeHolidayInformationExport fromEmpInfo(EmployeeInformationExport empInfo) {
		return new EmployeeHolidayInformationExport(empInfo.getEmployeeId(), empInfo.getEmployeeCode(),
				empInfo.getBusinessName(),
				empInfo.getWorkplace() != null ? new WorkplaceHolidayExport(empInfo.getWorkplace()) : null,
				empInfo.getClassification(), empInfo.getDepartment(), empInfo.getPosition(), empInfo.getEmployment(),
				empInfo.getEmploymentCls(), Optional.empty(), Collections.emptyList());
	}

}
