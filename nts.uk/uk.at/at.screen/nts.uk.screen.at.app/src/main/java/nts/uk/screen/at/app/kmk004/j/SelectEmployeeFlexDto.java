package nts.uk.screen.at.app.kmk004.j;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author sonnlb
 *
 */
@Data
@NoArgsConstructor
public class SelectEmployeeFlexDto {
	// 社員別フレックス勤務集計方法
	private ShaFlexMonthActCalSetDto shaFlexMonthActCalSet;

	// 社員別年度リストを表示する
	private List<YearDto> years;

}
