package nts.uk.screen.at.app.kmk004.o;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 社員別基本設定（変形労働）を表示する
 * @author tutt
 *
 */
@NoArgsConstructor
@Setter
public class DeforLaborMonthTimeShaDto {

	// 社員別変形労働法定労働時間
	public DeforLaborTimeShaDto deforLaborTimeShaDto;
	
	// 社員別変形労働集計設定
	public ShaDeforLaborMonthActCalSetDto shaDeforLaborMonthActCalSetDto;
}
