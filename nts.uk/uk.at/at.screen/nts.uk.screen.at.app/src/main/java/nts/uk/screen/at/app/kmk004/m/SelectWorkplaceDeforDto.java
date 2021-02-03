package nts.uk.screen.at.app.kmk004.m;

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
public class SelectWorkplaceDeforDto {
	
	// 職場別基本設定（変形労働）を表示する
	DeforLaborMonthTimeWkpDto deforLaborMonthTimeWkpDto;
	
	// 職場別年度リストを表示する
	List<YearDto> years;
}
