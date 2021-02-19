package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業締め日.アルゴリズム.Query.処理年月と締め期間を取得する.クラス.現在の締め期間
 */
@Builder
@Data
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
	

}