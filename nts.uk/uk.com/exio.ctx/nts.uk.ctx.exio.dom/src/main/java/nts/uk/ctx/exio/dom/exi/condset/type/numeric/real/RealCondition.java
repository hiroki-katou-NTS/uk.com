package nts.uk.ctx.exio.dom.exi.condset.type.numeric.real;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.exio.dom.exi.condset.ComparableValidation;
import nts.uk.ctx.exio.dom.exi.condset.CompareValueCondition;
import nts.uk.ctx.exio.dom.exi.condset.type.time.ImportingConditionTime;
import nts.uk.ctx.exio.dom.input.DataItem;

@Getter
public class RealCondition implements ComparableValidation<ImportingConditionReal>{

	CompareValueCondition condition;
	Optional<ImportingConditionReal> value1;
	Optional<ImportingConditionReal> value2;
	
	@Override
	public ImportingConditionReal getTargetValue(DataItem targetItem) {
		return new ImportingConditionReal(targetItem.getReal());
	}

}
