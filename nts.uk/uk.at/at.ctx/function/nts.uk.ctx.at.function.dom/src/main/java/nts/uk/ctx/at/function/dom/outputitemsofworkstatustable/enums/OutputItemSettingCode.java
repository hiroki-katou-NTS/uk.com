package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.*;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

//出力項目設定コード
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class OutputItemSettingCode extends CodePrimitiveValue<PrimitiveValue<String>> {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new output setting code.
     *
     * @param rawValue the raw value
     */
    public OutputItemSettingCode(String rawValue) {
        super(rawValue);
    }
}
