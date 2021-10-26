package nts.uk.file.at.app.export.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員情報dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoDto {
    // 社員ID
    private String employeeId;

    // 社員名
    private String employeeName;

    // 看護区分名称
    private String nursingClsName;
}
