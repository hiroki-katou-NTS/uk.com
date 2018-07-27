/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.workrule.closure;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * The Class PresentClosingPeriodExport.
 */
@Builder
@Data
// 現在の締め期間
public class PresentClosingPeriodExport {

	/** The processing ym. */
	// 処理年月
	private YearMonth processingYm;

	/** The closure start date. */
	// 締め開始日
	private GeneralDate closureStartDate;

	/** The closure end date. */
	// 締め終了日
	private GeneralDate closureEndDate;
	
	/** 日付 */
	private ClosureDateExport closureDate;

}
