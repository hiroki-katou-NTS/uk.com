package nts.uk.ctx.at.record.dom.daily.breaktimegoout;


import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 日別実績の休憩時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakTimeSheetOfDaily {
	private BreakCategory breakClassification;
	
	private List<BreakTimeSheet> breakTimeSheet;
	
	/**
	 * 休憩種類の取得
	 * @return
	 */
	public BreakCategory getcategory() {
		return this.breakClassification.is();
	}

	/**
	 * 指定した時間帯に含まれる休憩時間の合計値を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		
		return this.breakTimeSheet.stream()
				.collect(Collectors.summingInt(b -> b.calculateMinutesDuplicatedWith(baseTimeSheet)));
	}
}
