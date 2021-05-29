package nts.uk.ctx.exio.dom.exi.condset.type.time;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 時間条件
 */
@Getterpublic class TimeCondition implements ComparableValidation<ImportingConditionTime>{

	CompareValueCondition condition;
	Optional<ImportingConditionTime> value1;
	Optional<ImportingConditionTime> value2;
	
	@Override
	public ImportingConditionTime getTargetValue(DataItem targetItem) {
		return new ImportingConditionTime(targetItem.getInt());
	}

}
