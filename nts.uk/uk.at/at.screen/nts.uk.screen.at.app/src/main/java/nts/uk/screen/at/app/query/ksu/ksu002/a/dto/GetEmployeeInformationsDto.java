package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.ArrayList;
import java.util.List;

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
public class GetEmployeeInformationsDto {

	// ・社員情報リスト：List＜社員ID、社員コード, 社員名>
	public List<EmployeeInfoDto> employeeInfos = new ArrayList<>();

}

