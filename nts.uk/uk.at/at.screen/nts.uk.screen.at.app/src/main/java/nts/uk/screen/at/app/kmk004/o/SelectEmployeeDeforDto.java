package nts.uk.screen.at.app.kmk004.o;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;

/**
 * 
 * @author tutt
 *
 */
@Data
@NoArgsConstructor
public class SelectEmployeeDeforDto {
	
	// 社員別基本設定（変形労働）を表示する
	private DeforLaborMonthTimeShaDto deforLaborMonthTimeShaDto;

	// 社員別年度リストを表示する
	private List<YearDto> years;
}
