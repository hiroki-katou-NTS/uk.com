package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;

@AllArgsConstructor
@Getter
/** 月次処理用の暫定残数管理データ */
public class FixedRemainDataForMonthlyAgg {

	/** 日別暫定残数管理データ */
	private Map<GeneralDate, DailyInterimRemainMngData> daily;
	
	/** 月別確定管理データ */
	private FixedManagementDataMonth monthly;
}
