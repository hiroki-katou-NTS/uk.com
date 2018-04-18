package nts.uk.ctx.at.request.dom.application.common.adapter.closure;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
//現在の締め期間
@Value
public class PresentClosingPeriodImport {
	// 処理年月
	private YearMonth processingYm;

	// 締め開始日
	private GeneralDate closureStartDate;

	// 締め終了日
	private GeneralDate closureEndDate;

	}
