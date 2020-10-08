package nts.uk.ctx.sys.auth.dom.anniversary;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import java.util.function.Function;

/**
 * 記念日のタイトル
 */
@StringMaxLength(60)
public class AnniversaryTitle extends StringPrimitiveValue<AnniversaryTitle> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnniversaryTitle(String rawValue) {
        super(rawValue);
    }
}
