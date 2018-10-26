package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class EmployeeInformationQueryDtoImport {
    /**
     * The employee ids.
     */
    List<String> employeeIds; // 社員一覧

    /**
     * The reference date.
     */
    GeneralDate referenceDate; // 年月日

    /**
     * The to get workplace.
     */
    boolean toGetWorkplace; // 職場を取得する

    /**
     * The to get department.
     */
    boolean toGetDepartment; // 部門を取得する

    /**
     * The to get position.
     */
    boolean toGetPosition; // 職位を取得する

    /**
     * The to get employment.
     */
    boolean toGetEmployment; // 雇用を取得する

    /**
     * The to get classification.
     */
    boolean toGetClassification; // 分類を取得する

    /**
     * The to get employment cls.
     */
    boolean toGetEmploymentCls; // 就業区分を取得する

    public EmployeeInformationQueryDtoImport(List<String> employeeIds, GeneralDate referenceDate,
                                             boolean toGetWorkplace, boolean toGetDepartment, boolean toGetPosition, boolean toGetEmployment,
                                             boolean toGetClassification, boolean toGetEmploymentCls) {
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
