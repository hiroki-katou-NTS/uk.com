package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class EmpInfoDeptDto {
    /**
     * 社員ID
     */
    private String sid;
    /**
     * 部門コード
     */
    private String departmentCode;

    /**
     * 部門表示名
     */
    private String departmentName;
}
