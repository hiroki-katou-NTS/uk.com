package nts.uk.ctx.exio.dom.input.importableitem.group;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 受入グループID
 */
@IntegerRange(min = 1, max = 999)
@SuppressWarnings("serial")
public class ImportingGroupId extends IntegerPrimitiveValue<ImportingGroupId>{

	public ImportingGroupId(Integer rawValue) {
		super(rawValue);
	}
	
	public static ImportingGroupId of(int groupId) {
		return new ImportingGroupId(groupId);
	}
	
	/** 雇用履歴 */
	public static final ImportingGroupId EMPLOYMENT_HISTORY = of(100);

	/** 作業 */
	public static final ImportingGroupId TASK = of(200);
}
