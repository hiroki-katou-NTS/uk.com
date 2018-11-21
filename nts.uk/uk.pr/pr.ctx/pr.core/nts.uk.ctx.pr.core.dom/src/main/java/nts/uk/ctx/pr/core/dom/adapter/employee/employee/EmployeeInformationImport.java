package nts.uk.ctx.pr.core.dom.adapter.employee.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.adapter.employee.classification.ClassificationImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentImport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.PositionImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.WorkplaceImport;

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


}