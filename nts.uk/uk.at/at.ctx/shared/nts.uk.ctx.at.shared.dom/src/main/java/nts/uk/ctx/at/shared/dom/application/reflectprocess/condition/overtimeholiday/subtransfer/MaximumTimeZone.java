package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.overtimeholiday.subtransfer;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;

/**
 * @author thanh_nx
 *
 *         最大時間帯
 */
@AllArgsConstructor
@Getter
@Setter
public class MaximumTimeZone {

	// Map<int,計算時間帯>
	Map<Integer, TimeSpanForDailyCalc> timeSpan;

	public MaximumTimeZone() {
		this.timeSpan = new HashMap<>();
	}

}
