package nts.uk.screen.at.app.kmk004.i;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
public class SelectEmploymentFlexDto {
	// 雇用を選択する
	EmpFlexMonthActCalSetDto flexMonthActCalSet;
	// 職場別年度リストを表示する
	List<YearDto> years;
}
