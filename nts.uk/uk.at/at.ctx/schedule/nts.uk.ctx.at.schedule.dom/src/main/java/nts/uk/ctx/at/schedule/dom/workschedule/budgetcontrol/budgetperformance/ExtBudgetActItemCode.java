package nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 外部予算実績項目コード
 * @author HieuLt
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class ExtBudgetActItemCode extends CodePrimitiveValue<ExtBudgetActItemCode> {


	private static final long serialVersionUID = 1L;
	
	public ExtBudgetActItemCode(String rawValue) {
		super(rawValue);
	}

}
