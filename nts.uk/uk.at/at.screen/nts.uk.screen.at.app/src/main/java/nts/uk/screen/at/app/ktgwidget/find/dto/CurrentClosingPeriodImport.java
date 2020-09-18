package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@Builder

//	現在の締め期間
public class CurrentClosingPeriodImport {
	// 処理年月
	private YearMonth processingYm;

	// 締め開始日
	private GeneralDate closureStartDate;

	// 締め終了日
	private GeneralDate closureEndDate;

}