package nts.uk.screen.at.app.kmk004.n;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@Data
public class SelectEmploymentDeforDto {
	
	// 雇用別基本設定（変形労働）を表示する
	DeforLaborMonthTimeEmpDto deforLaborMonthTimeEmpDto;
	
	// 雇用別年度リストを表示する
	List<YearDto> years;
}
