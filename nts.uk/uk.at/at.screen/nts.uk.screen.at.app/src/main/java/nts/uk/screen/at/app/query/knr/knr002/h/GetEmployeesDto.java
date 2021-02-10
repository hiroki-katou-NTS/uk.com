package nts.uk.screen.at.app.query.knr.knr002.h;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author xuannt
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetEmployeesDto {
	//	社員ID
	private String employeeId; 
	//	社員コード
	private String employeeCode; 
	//	ビジネスネーム
	private String businessName; 
	//	ビジネスネームカナ
	private String businessNameKana; 
	//	所属職場．職場表示名
	private String workplaceName; 
}
