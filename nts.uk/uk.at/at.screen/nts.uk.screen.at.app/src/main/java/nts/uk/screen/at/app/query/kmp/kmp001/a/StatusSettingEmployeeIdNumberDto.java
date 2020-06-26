package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ・カードNOが設定されている社員一覧：List<社員ID>
 * @author chungnt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusSettingEmployeeIdNumberDto {
	
	// List<社員ID>
	private List<String> employeeIds;
}
