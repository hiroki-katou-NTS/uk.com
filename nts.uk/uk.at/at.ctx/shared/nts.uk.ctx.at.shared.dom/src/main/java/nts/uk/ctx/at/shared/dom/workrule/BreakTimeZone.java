package nts.uk.ctx.at.shared.dom.workrule;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 休憩時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.休憩時間
 *
 * @author kumiko_otake
 *
 */
@Value
public class BreakTimeZone {

	/** 休憩時間帯を固定にする **/
	private final boolean fixed;

	/** 休憩時間帯 **/
	private final List<TimeSpanForCalc> breakTimes;


	/**
	 * 流動休憩で作る
	 * @param breakTimes 休憩時間帯リスト
	 * @return 休憩時間帯(流動)
	 */
	public static BreakTimeZone createAsNotFixed(List<TimeSpanForCalc> breakTimes) {

		return new BreakTimeZone(false, breakTimes);

	}

	/**
	 * 固定休憩で作る
	 * @param breakTimes 休憩時間帯リスト
	 * @return 休憩時間帯(固定)
	 */
	public static BreakTimeZone createAsFixed(List<TimeSpanForCalc> breakTimes) {

		return new BreakTimeZone(true, breakTimes);

	}

}
