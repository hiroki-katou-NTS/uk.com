package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeInformationDto {
	
	public String employeeId; // 社員ID
	public String employeeCode; // 社員コード
	public String businessName; // ビジネスネーム
}
