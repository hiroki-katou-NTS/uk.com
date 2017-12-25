package nts.uk.ctx.at.record.dom.daily.optionalitemtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 任意項目値 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnyItemValue {

	/** 任意項目NO: 任意項目NO */
	private AnyItemNo itemNo;
	
	/** 回数: 日次任意回数 */
	private Optional<AnyItemTimes> times;
	
	/** 金額: 日次任意金額 */
	private Optional<AnyItemAmount> amount;
	
	/** 時間: 日次任意時間 */
	private Optional<AnyItemTime> time;
	
}
