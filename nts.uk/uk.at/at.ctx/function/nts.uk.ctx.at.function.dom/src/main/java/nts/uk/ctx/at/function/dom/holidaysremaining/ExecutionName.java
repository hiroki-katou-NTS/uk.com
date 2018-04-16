package nts.uk.ctx.at.function.dom.holidaysremaining;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 更新処理自動実行項目名称
 */
@StringMaxLength(30)
public class ExecutionName extends StringPrimitiveValue<ExecutionName> {
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    public ExecutionName(String rawValue) {
        super(rawValue);
    }
}
