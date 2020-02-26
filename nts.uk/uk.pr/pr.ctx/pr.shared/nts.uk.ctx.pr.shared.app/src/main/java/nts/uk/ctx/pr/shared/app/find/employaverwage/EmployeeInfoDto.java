package nts.uk.ctx.pr.shared.app.find.employaverwage;

import lombok.Data;

@Data
public class EmployeeInfoDto {

    private String employeeId; // 社員ID
    private String employeeCode; // 社員コード
    private String businessName; // ビジネスネーム
    private String departmentName; // 部門表示名
    private String employmentName; //雇用名称
    private Long averageWage;

}
