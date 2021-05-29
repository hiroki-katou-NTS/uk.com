package nts.uk.ctx.exio.dom.exi.condset.type.numeric.integer;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 整数条件
 */
@Getter
public class IntegerCondition implements ComparableValidation<Long>{
	CompareValueCondition condition;
	Optional<Long> value1;
	Optional<Long> value2;

	@Override
	public Long getTargetValue(DataItem targetItem) {
		return targetItem.getInt();
	}
}
