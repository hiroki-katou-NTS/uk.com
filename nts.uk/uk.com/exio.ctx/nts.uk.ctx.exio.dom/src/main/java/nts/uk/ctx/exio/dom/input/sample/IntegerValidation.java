package nts.uk.ctx.exio.dom.input.sample;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.DataItem;

@Getter
public class IntegerValidation implements ComparableValidation<Integer>{

	Integer value1;
	Integer value2;
	CompareValueCondition condition;

	@Override
	public Integer getTargetValue(DataItem targetItem) {
		return targetItem.getInt();
	}
}
