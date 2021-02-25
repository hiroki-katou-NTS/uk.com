package nts.uk.ctx.at.function.app.find.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 個人社員基本情報
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBasicInfoExportDto {

    /**
     * 個人ID
     */
    private String personId;
    /**
     * 入社年月日
     */
    private GeneralDate entryDate;
    /**
     * 退職年月日
     */
    private GeneralDate retiredDate;
    /**
     * 生年月日
     */
    private GeneralDate birthDay;
    /**
     * 性別
     */
    private int gender;
    /**
     * ビジネスネーム
     */
    private String businessName;

    /**
     * 社員ID
     */
    private String employeeId;

    /** 社員コード */
    private String employeeCode;
}
