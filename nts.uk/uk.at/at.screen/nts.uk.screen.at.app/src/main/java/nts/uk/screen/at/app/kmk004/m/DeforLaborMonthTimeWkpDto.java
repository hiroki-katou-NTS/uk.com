package nts.uk.screen.at.app.kmk004.m;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 職場別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@Setter
public class DeforLaborMonthTimeWkpDto {
	
	// 職場別変形労働法定労働時間
	public DeforLaborTimeWkpDto deforLaborTimeWkpDto;
	
	// 職場別変形労働集計設定
	public WkpDeforLaborMonthActCalSetDto wkpDeforLaborMonthActCalSetDto;
}
