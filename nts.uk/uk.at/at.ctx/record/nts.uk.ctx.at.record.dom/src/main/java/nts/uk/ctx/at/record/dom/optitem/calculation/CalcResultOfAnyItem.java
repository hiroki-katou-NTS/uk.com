package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemNo;

/**
 * 任意項目の計算結果
 * @author keisuke_hoshina
 *
 */
@Getter
public class CalcResultOfAnyItem {
	private AnyItemNo anyItemNo;
	Optional<Integer> count;
	Optional<Integer> time;
	Optional<Integer> money;
}
