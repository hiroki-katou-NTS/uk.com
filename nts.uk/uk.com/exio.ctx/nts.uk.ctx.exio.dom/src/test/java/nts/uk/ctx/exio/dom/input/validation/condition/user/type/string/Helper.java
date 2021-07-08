package nts.uk.ctx.exio.dom.input.validation.condition.user.type.string;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.validation.user.CompareStringCondition;
import nts.uk.ctx.exio.dom.input.validation.user.Validation;
import nts.uk.ctx.exio.dom.input.validation.user.type.string.ImportingStringCondition;
import nts.uk.ctx.exio.dom.input.validation.user.type.string.StringCondition;

public class Helper {
	static Validation notCompare = new StringCondition(CompareStringCondition.NOT_COND,Optional.empty());
	
	public static Validation DUMMY_VALIdATION(CompareStringCondition condition, String value) {
		return new StringCondition(condition,
				Optional.of(new ImportingStringCondition(value))
				);
	}
	
	public static DataItem DUMMY_ITEM(String value) {
		return new DataItem(DUMMY.itemNo,  value);
	}
	
	static class DUMMY{
		static String VALUE1 = "DUMMY1";
		static String VALUE2 = "DUMMY2";
		static int itemNo = 999;
	}
}
