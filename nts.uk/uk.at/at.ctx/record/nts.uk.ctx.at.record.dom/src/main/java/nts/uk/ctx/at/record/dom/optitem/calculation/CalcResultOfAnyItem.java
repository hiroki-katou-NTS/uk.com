package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;

/**
 * 任意項目の計算結果
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class CalcResultOfAnyItem {
	private OptionalItemNo optionalItemNo;
	Optional<Integer> count;
	Optional<Integer> time;
	Optional<Integer> money;
	
	
	/**
	 * 指定された属性の項目のみ渡された値で更新する
	 * @param value
	 * @param optionalItemAtr
	 * @return
	 */
	public CalcResultOfAnyItem reCreateCalcResultOfAnyItem(Integer value, OptionalItemAtr optionalItemAtr) {
		switch (optionalItemAtr) {
		case NUMBER:
			return new CalcResultOfAnyItem(this.optionalItemNo,Optional.of(value),this.time,this.money);
		case AMOUNT:
			return new CalcResultOfAnyItem(this.optionalItemNo,this.count,this.time,Optional.of(value));
		case TIME:
			return new CalcResultOfAnyItem(this.optionalItemNo,this.count,Optional.of(value),this.money);
		default:
			throw new RuntimeException("unknown value of enum OptionalItemAtr");
		}
	}
	
}
