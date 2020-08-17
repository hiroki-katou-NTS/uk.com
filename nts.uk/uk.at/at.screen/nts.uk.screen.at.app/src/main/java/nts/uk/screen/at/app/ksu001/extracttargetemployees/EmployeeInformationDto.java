package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeInformationDto {
	
	String employeeId; // 社員ID
	String employeeCode; // 社員コード
	String businessName; // ビジネスネーム
}
