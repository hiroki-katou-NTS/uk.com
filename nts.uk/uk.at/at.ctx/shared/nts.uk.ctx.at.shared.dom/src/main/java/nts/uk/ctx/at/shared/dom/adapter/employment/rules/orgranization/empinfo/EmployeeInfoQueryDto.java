
package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class EmployeeInformationQueryDto.
 */
// 社員情報
@Data
@AllArgsConstructor
public class EmployeeInfoQueryDto {

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

}
