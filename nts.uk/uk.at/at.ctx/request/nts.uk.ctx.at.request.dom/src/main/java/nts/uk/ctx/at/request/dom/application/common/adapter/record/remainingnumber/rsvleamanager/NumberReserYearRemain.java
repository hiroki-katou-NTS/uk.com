package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author loivt
 * 積立年休残数(仮)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumberReserYearRemain {
	/**
	 * 残数 <- 基準日時点積立年休残数．積立年休情報．残数．積立年休(マイナスあり)．残数.合計残日数
	 */
	private double remainningDays;
}
