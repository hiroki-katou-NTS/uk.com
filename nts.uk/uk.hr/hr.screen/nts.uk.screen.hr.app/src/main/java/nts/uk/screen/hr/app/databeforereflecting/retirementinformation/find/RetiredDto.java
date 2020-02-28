package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RetiredDto {
	// 社員ID
	private String employeeId;
	// 年齢
	private int age;
}
