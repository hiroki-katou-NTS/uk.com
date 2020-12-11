package nts.uk.screen.at.app.kmk004.j;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeIdDto;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class AfterChangeFlexEmployeeSettingDto {

	// 社員別フレックス勤務集計方法

	private ShaFlexMonthActCalSetDto shaFlexMonthActCalSet;

	private List<EmployeeIdDto> employeeIds;
}
