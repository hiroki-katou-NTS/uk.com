package nts.uk.ctx.exio.dom.exi.condset.type.time;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 時刻条件
 */
@Getter
public class TimeMomentCondition implements ComparableValidation<ImportingConditionTimeMoment>{

	CompareValueCondition condition;
	Optional<ImportingConditionTimeMoment> value1;
	Optional<ImportingConditionTimeMoment> value2;

	@Override
	public ImportingConditionTimeMoment getTargetValue(DataItem targetItem) {
		return new ImportingConditionTimeMoment(targetItem.getInt());
	}
}
