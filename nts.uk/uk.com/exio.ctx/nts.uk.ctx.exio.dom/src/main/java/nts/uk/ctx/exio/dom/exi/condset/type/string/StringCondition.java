package nts.uk.ctx.exio.dom.exi.condset.type.string;

import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.CompareStringCondition;
import nts.uk.ctx.exio.dom.exi.condset.Validation;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 文字条件
 */
public class StringCondition implements Validation{
	
	CompareStringCondition condition;
	Optional<ImportingConditionString> value;
	
	@Override
	public boolean validate(DataItem target) {
		switch(this.condition) {
			case NOT_COND:
				return true;
			case COND1_EQUAL_VAL:
				return value.get().toString() == target.getString();
			case COND1_NOT_EQUAL_VAL:
				return value.get().toString() != target.getString();
			default:
				throw new RuntimeException("実装が存在しない文字の比較条件です。");
		}
	}
}
