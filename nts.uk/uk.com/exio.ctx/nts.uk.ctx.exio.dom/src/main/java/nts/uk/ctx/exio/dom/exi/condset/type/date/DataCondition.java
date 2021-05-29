package nts.uk.ctx.exio.dom.exi.condset.type.date;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 日付条件
 */
@Getter
public class DataCondition implements ComparableValidation<GeneralDate>{

	CompareValueCondition condition;
	Optional<GeneralDate> value1;
	Optional<GeneralDate> value2;

	@Override
	public GeneralDate getTargetValue(DataItem targetItem) {
		return targetItem.getDate();
	}

}
