package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeInformationDto {
    private String employeeId; // 社員ID

    /** The employee code. */
    private String employeeCode; // 社員コード

    /** The business name. */
    private String businessName; // ビジネスネーム
}
