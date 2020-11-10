package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * @author thanhpv
 * @name 現在の締め期間
 */
@AllArgsConstructor
@Getter
public class CurrentClosingPeriod {

	// 処理年月
	private Integer processingYm;
	
	//締め開始日
	private GeneralDate startDate;
	
	//締め終了日
	private GeneralDate endDate;
	
	
	
}
