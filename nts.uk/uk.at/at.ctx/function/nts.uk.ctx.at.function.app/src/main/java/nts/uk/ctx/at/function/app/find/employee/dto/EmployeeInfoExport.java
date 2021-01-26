package nts.uk.ctx.at.function.app.find.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInfoExport {

    /** 個人ID */
    private String personId;

    /** 性別 */
    private int gender;

    /** 生年月日 */
    private GeneralDate birthDay;

    /** ビジネスネーム.Business name */
    private String businessName;
}
