package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeInfoDto {

	// 社員ID
	public String id;

	// 社員コード
	public String code;

	// 社員名
	public String businessName;

}