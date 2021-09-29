package nts.uk.ctx.bs.employee.dom.workplace.master;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 職場階層単体コード
 */
@SuppressWarnings("serial")
@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
public class WorkplaceHierarchyUnitCode extends StringPrimitiveValue<WorkplaceHierarchyUnitCode> {

	public WorkplaceHierarchyUnitCode(String rawValue) {
		super(rawValue);
	}
}