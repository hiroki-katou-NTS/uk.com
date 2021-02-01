package nts.uk.screen.at.app.kmk004.n;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 雇用別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@Setter
public class DeforLaborMonthTimeEmpDto {

	// 雇用別変形労働法定労働時間
	public DeforLaborTimeEmpDto deforLaborTimeEmpDto;
		
	// 雇用別変形労働集計設定
	public EmpDeforLaborMonthActCalSetDto empDeforLaborMonthActCalSetDto;
}
