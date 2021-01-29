package nts.uk.screen.at.app.kmk004.l;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 会社別基本設定（変形労働）を表示する
 * 
 * @author tutt
 *
 */
@NoArgsConstructor
@Setter
public class DeforLaborMonthTimeComDto {

	// 会社別変形労働法定労働時間
	public DeforLaborTimeComDto deforLaborTimeComDto;

	// 会社別変形労働集計設定
	public ComDeforLaborMonthActCalSetDto comDeforLaborMonthActCalSetDto;
}
