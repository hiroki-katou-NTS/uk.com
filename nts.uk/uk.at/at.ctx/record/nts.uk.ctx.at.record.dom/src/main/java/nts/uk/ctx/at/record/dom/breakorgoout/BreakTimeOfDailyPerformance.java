package nts.uk.ctx.at.record.dom.breakorgoout;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 
 * @author nampt
 * 日別実績の休憩時間帯 - root
 *
 */
@Getter
@AllArgsConstructor
public class BreakTimeOfDailyPerformance extends AggregateRoot {
	
	private String employeeId;
	
	private BreakType breakType;
	
	private List<BreakTimeSheet> breakTimeSheets;
	
	private GeneralDate ymd;
	
	/**
	 * 休憩種類の取得
	 * @return
	 */
	public BreakType getcategory() {
		return this.breakType;
	}

	/**
	 * 指定した時間帯に含まれる休憩時間の合計値を返す
	 * @param baseTimeSheet
	 * @return
	 */
	public int sumBreakTimeIn(TimeSpanForCalc baseTimeSheet) {
		
		return this.breakTimeSheets.stream()
				.collect(Collectors.summingInt(b -> b.calculateMinutesDuplicatedWith(baseTimeSheet)));
	}

}
