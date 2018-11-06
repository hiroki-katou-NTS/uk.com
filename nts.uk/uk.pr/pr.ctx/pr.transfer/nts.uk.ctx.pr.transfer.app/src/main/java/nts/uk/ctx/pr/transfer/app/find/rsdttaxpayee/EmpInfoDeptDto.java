package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EmpInfoDeptDto {
    /**
     * 社員ID
     */
    private String sid;
    /**
     * 部門表示名
     */
    private String departmentName;
}
